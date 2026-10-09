package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chair_sessions")
data class ChairSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientName: String = "",
    val clientPhone: String = "",
    val barberName: String = "Vikram S.",
    val pricingModel: String = "fee_100", // "fee_100" (₹100 consultation fee) or "commission_2pct" (2% service bill)
    val stage: String = "START", // START, CONSENT, REFERENCE_CAPTURE, GENERATING, RENDER_RESULTS, SELECTION, OUTCOME_LOG, COMPLETED, DECLINED
    val photoConsentGranted: Boolean = false,
    val retentionOptIn: Boolean = false,
    val consentTimestamp: Long = 0,
    val clientPhotoPath: String? = null,
    val referencePhotoPath: String? = null,
    val referenceStyleName: String = "Textured Crop Fade",
    val faceShape: String = "Oval",
    val hairTexture: String = "Wavy (Type 2B)",
    val desiredFade: String = "Low Taper Fade",
    val renderDurationSeconds: Int = 38,
    val renderSpeedMetTarget: Boolean = true,
    val selectedVariationId: Int = 1,
    val selectedVariationTitle: String = "Low Taper Textured Crop",
    val selectedVariationDrawableName: String = "style_var_one_1790418892068",
    val barberNotes: String = "Keep 1.5 inches length on crown, #2 guard on temple, natural taper at neckline.",
    val barberFadeTweak: String = "Low Taper (#1.5)",
    val barberLengthTweak: String = "Medium (1.5 in)",
    val barberBeardTweak: String = "Soft Taper Blend",
    val outcomeMatchStatus: String? = null, // "MATCHED", "MINOR_DIFFERENCES", "NOT_MATCHED"
    val outcomeRealismRating: Int = 5, // 1 to 5
    val barberAdoptedTool: Boolean = true, // barber used tool mid-consultation
    val outcomeNotes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)
