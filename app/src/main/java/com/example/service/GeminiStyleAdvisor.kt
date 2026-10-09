package com.example.service

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiStyleAdvisor {
    private const val TAG = "GeminiStyleAdvisor"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun generateBarberAdaptationAdvice(
        clientName: String,
        referenceStyle: String,
        faceShape: String,
        hairTexture: String,
        desiredFade: String
    ): BarberAdvice = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackAdvice(referenceStyle, faceShape, hairTexture, desiredFade)
        }

        try {
            val prompt = """
                You are Master Barber at Kimera (Jayanagar, Bengaluru), an elite metro salon.
                Client: $clientName
                Reference Style: $referenceStyle
                Face Shape: $faceShape
                Hair Texture: $hairTexture
                Desired Fade: $desiredFade
                
                Provide chair-side technical haircut adaptation notes before the first cut.
                Return ONLY a JSON object with this exact structure:
                {
                   "summary": "Short 1-sentence adaptation summary",
                   "fadeGuard": "Recommended guard (e.g. #1.5 to #2 open)",
                   "topLength": "Recommended length in inches on top",
                   "textureTechnique": "Barber technique (e.g. point cutting, thinning shears on crown)",
                   "productRec": "Matte clay / Sea salt spray / Pomade advice",
                   "faceShapeRationale": "Why this adaptation complements the $faceShape face shape",
                   "confidenceScore": 94
                }
            """.trimIndent()

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseMimeType", "application/json")
                    put("temperature", 0.4)
                })
            }

            val request = Request.Builder()
                .url("$BASE_URL?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string() ?: ""

            if (!response.isSuccessful || responseString.isBlank()) {
                Log.w(TAG, "Gemini API error code: ${response.code}")
                return@withContext getFallbackAdvice(referenceStyle, faceShape, hairTexture, desiredFade)
            }

            val rootJson = JSONObject(responseString)
            val candidates = rootJson.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val rawText = parts?.optJSONObject(0)?.optString("text") ?: ""

            val parsedAdvice = JSONObject(rawText)
            BarberAdvice(
                summary = parsedAdvice.optString("summary", "Adapted $referenceStyle for $faceShape face and $hairTexture hair."),
                fadeGuard = parsedAdvice.optString("fadeGuard", "#1.5 to #2 taper"),
                topLength = parsedAdvice.optString("topLength", "1.5 - 2.0 inches"),
                textureTechnique = parsedAdvice.optString("textureTechnique", "Deep point cutting on crown to enhance texture flow"),
                productRec = parsedAdvice.optString("productRec", "Matte Hair Clay & Texture Powder"),
                faceShapeRationale = parsedAdvice.optString("faceShapeRationale", "Balances horizontal width while emphasizing jawline structure."),
                confidenceScore = parsedAdvice.optInt("confidenceScore", 92)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed calling Gemini API", e)
            getFallbackAdvice(referenceStyle, faceShape, hairTexture, desiredFade)
        }
    }

    private fun getFallbackAdvice(
        referenceStyle: String,
        faceShape: String,
        hairTexture: String,
        desiredFade: String
    ): BarberAdvice {
        val guard = when {
            desiredFade.contains("Low", ignoreCase = true) -> "#2 into #3 low taper"
            desiredFade.contains("Mid", ignoreCase = true) -> "#1.5 drop taper"
            desiredFade.contains("High", ignoreCase = true) -> "#0.5 into #1.5 skin fade"
            else -> "Scissor over comb taper"
        }

        val rationale = when (faceShape) {
            "Oval" -> "Oval faces carry balanced symmetry; micro-fringe adds directional contrast without hiding the forehead."
            "Square" -> "Softened temple corners round off angular jawlines while keeping modern edge."
            "Round" -> "Vertical volume with tight temple taper visually elongates facial proportions."
            "Diamond" -> "Wider textured fringe softens high cheekbones and creates proportional balance."
            else -> "Custom taper tailored to client hairline cowlick and natural growth patterns."
        }

        return BarberAdvice(
            summary = "Kimera adaptation: Adapted $referenceStyle to $hairTexture hair and $faceShape face geometry.",
            fadeGuard = guard,
            topLength = "1.5 to 2.2 inches (textured)",
            textureTechnique = "Point cutting for layered movement with crown bulk reduction",
            productRec = "Matte styling clay + Sea salt spray for natural pliable hold",
            faceShapeRationale = rationale,
            confidenceScore = 95
        )
    }
}

data class BarberAdvice(
    val summary: String,
    val fadeGuard: String,
    val topLength: String,
    val textureTechnique: String,
    val productRec: String,
    val faceShapeRationale: String,
    val confidenceScore: Int
)
