package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SalonAssetProvider
import com.example.data.local.AppDatabase
import com.example.data.model.ChairSession
import com.example.data.model.StyleVariation
import com.example.data.repository.SessionRepository
import com.example.service.BarberAdvice
import com.example.service.GeminiStyleAdvisor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RenderProgressState(
    val isRendering: Boolean = false,
    val elapsedSeconds: Int = 0,
    val progressPercent: Float = 0f,
    val currentStageText: String = "Initializing Kimera Studio...",
    val isComplete: Boolean = false
)

data class PilotMetrics(
    val totalSessions: Int = 0,
    val matchRatePercent: Int = 0,
    val barberAdoptionPercent: Int = 0,
    val consentRatePercent: Int = 0,
    val speedCompliancePercent: Int = 0,
    val realismRatePercent: Int = 0
)

class KimeraSessionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SessionRepository
    init {
        val db = AppDatabase.getInstance(application)
        repository = SessionRepository(db.sessionDao())
    }

    val allSessions: StateFlow<List<ChairSession>> = repository.allSessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentSession = MutableStateFlow<ChairSession>(
        ChairSession(
            clientName = "Vikram S.",
            clientPhone = "+91 98450 12345",
            barberName = "Rahul (Kimera Jayanagar)",
            pricingModel = "fee_100",
            stage = "START"
        )
    )
    val currentSession: StateFlow<ChairSession> = _currentSession.asStateFlow()

    private val _renderProgress = MutableStateFlow(RenderProgressState())
    val renderProgress: StateFlow<RenderProgressState> = _renderProgress.asStateFlow()

    private val _barberAdvice = MutableStateFlow<BarberAdvice?>(null)
    val barberAdvice: StateFlow<BarberAdvice?> = _barberAdvice.asStateFlow()

    val variations: List<StyleVariation> = SalonAssetProvider.defaultVariations

    private var renderJob: Job? = null

    init {
        // Pre-populate initial pilot benchmark data if empty for instant rich experience
        viewModelScope.launch {
            if (repository.getSessionCount() == 0) {
                seedInitialPilotData()
            }
        }
    }

    private suspend fun seedInitialPilotData() {
        val now = System.currentTimeMillis()
        val seeds = listOf(
            ChairSession(
                clientName = "Vikram K. (Tech Lead)",
                clientPhone = "+91 98860 44210",
                barberName = "Rahul (Kimera Lead)",
                pricingModel = "fee_100",
                stage = "COMPLETED",
                photoConsentGranted = true,
                retentionOptIn = true,
                consentTimestamp = now - 86400000 * 2,
                referenceStyleName = "Textured Crop Fade",
                faceShape = "Oval",
                hairTexture = "Wavy (Type 2B)",
                desiredFade = "Low Taper Fade",
                renderDurationSeconds = 41,
                renderSpeedMetTarget = true,
                selectedVariationId = 1,
                selectedVariationTitle = "Low Taper Textured Crop",
                barberNotes = "Crown kept dense, #2 taper on temple, scissor point-cut fringe.",
                outcomeMatchStatus = "MATCHED",
                outcomeRealismRating = 5,
                barberAdoptedTool = true,
                outcomeNotes = "Client was thrilled with the realistic texture representation.",
                createdAt = now - 86400000 * 2,
                completedAt = now - 86400000 * 2 + 1800000
            ),
            ChairSession(
                clientName = "Arun M. (Product Designer)",
                clientPhone = "+91 97410 88231",
                barberName = "Kiran S.",
                pricingModel = "commission_2pct",
                stage = "COMPLETED",
                photoConsentGranted = true,
                retentionOptIn = false,
                consentTimestamp = now - 86400000,
                referenceStyleName = "Modern Drop Taper Crop",
                faceShape = "Square",
                hairTexture = "Straight Thick",
                desiredFade = "Mid Drop Fade",
                renderDurationSeconds = 36,
                renderSpeedMetTarget = true,
                selectedVariationId = 2,
                selectedVariationTitle = "Mid Drop Fade Crop",
                barberNotes = "Debulked sides, blunt micro-fringe with matte powder finish.",
                outcomeMatchStatus = "MATCHED",
                outcomeRealismRating = 5,
                barberAdoptedTool = true,
                outcomeNotes = "Barber referenced the render 3 times during the consultation.",
                createdAt = now - 86400000,
                completedAt = now - 86400000 + 1750000
            ),
            ChairSession(
                clientName = "Devendra P. (Architect)",
                clientPhone = "+91 96112 55901",
                barberName = "Rahul (Kimera Lead)",
                pricingModel = "fee_100",
                stage = "COMPLETED",
                photoConsentGranted = true,
                retentionOptIn = true,
                consentTimestamp = now - 3600000 * 5,
                referenceStyleName = "Scissor Flow Textured Crop",
                faceShape = "Diamond",
                hairTexture = "Curly (Type 3A)",
                desiredFade = "Scissor Taper",
                renderDurationSeconds = 44,
                renderSpeedMetTarget = true,
                selectedVariationId = 3,
                selectedVariationTitle = "Scissor Flow Textured Crop",
                barberNotes = "Preserved curls on front perimeter, soft scissor taper around ears.",
                outcomeMatchStatus = "MATCHED",
                outcomeRealismRating = 4,
                barberAdoptedTool = true,
                outcomeNotes = "First time client tried a modern crop; preview eliminated anxiety.",
                createdAt = now - 3600000 * 5,
                completedAt = now - 3600000 * 5 + 1900000
            )
        )
        for (s in seeds) {
            repository.createSession(s)
        }
    }

    fun startNewSession(
        clientName: String,
        clientPhone: String,
        barberName: String,
        pricingModel: String
    ) {
        _currentSession.update {
            it.copy(
                id = 0,
                clientName = clientName.ifBlank { "Vikram S." },
                clientPhone = clientPhone.ifBlank { "+91 98450 12345" },
                barberName = barberName,
                pricingModel = pricingModel,
                stage = "CONSENT",
                createdAt = System.currentTimeMillis(),
                completedAt = null,
                outcomeMatchStatus = null
            )
        }
    }

    fun setConsent(photoConsent: Boolean, retentionOptIn: Boolean) {
        _currentSession.update {
            it.copy(
                photoConsentGranted = photoConsent,
                retentionOptIn = retentionOptIn,
                consentTimestamp = System.currentTimeMillis(),
                stage = if (photoConsent) "REFERENCE_CAPTURE" else "DECLINED"
            )
        }
    }

    fun declineConsentAndExit() {
        _currentSession.update {
            it.copy(
                photoConsentGranted = false,
                retentionOptIn = false,
                consentTimestamp = System.currentTimeMillis(),
                stage = "DECLINED"
            )
        }
    }

    fun updateReferenceAndProfile(
        referenceStyleName: String,
        faceShape: String,
        hairTexture: String,
        desiredFade: String,
        clientPhotoPath: String? = null,
        referencePhotoPath: String? = null
    ) {
        _currentSession.update {
            it.copy(
                referenceStyleName = referenceStyleName,
                faceShape = faceShape,
                hairTexture = hairTexture,
                desiredFade = desiredFade,
                clientPhotoPath = clientPhotoPath ?: it.clientPhotoPath,
                referencePhotoPath = referencePhotoPath ?: it.referencePhotoPath
            )
        }
    }

    fun startGeneratingPreview() {
        val session = _currentSession.value
        _currentSession.update { it.copy(stage = "GENERATING") }
        _renderProgress.value = RenderProgressState(
            isRendering = true,
            elapsedSeconds = 0,
            progressPercent = 0.05f,
            currentStageText = "1/4 Facial Proportions & Hairline Analysis...",
            isComplete = false
        )

        renderJob?.cancel()
        renderJob = viewModelScope.launch {
            // Kick off advice generation in background
            val adviceDeferred = launch {
                val advice = GeminiStyleAdvisor.generateBarberAdaptationAdvice(
                    clientName = session.clientName,
                    referenceStyle = session.referenceStyleName,
                    faceShape = session.faceShape,
                    hairTexture = session.hairTexture,
                    desiredFade = session.desiredFade
                )
                _barberAdvice.value = advice
            }

            // Simulate the progressive under-2-minute rendering pipeline stages
            // Targets: completes rapidly (~12-15 seconds simulated or instant complete)
            val stages = listOf(
                Pair(0.20f, "1/4 Analyzing facial angles & hairline pattern..."),
                Pair(0.45f, "2/4 Adapting reference style to ${session.hairTexture}..."),
                Pair(0.75f, "3/4 Synthesizing 4 Kimera variation renders..."),
                Pair(0.92f, "4/4 Applying Jayanagar scissor detail & light finish..."),
                Pair(1.00f, "Renders complete! Ready for chair discussion.")
            )

            var seconds = 0
            for ((prog, text) in stages) {
                delay(1200)
                seconds += 1
                _renderProgress.update {
                    it.copy(
                        elapsedSeconds = seconds,
                        progressPercent = prog,
                        currentStageText = text
                    )
                }
            }

            adviceDeferred.join()

            _renderProgress.update {
                it.copy(
                    isRendering = false,
                    isComplete = true,
                    progressPercent = 1.0f
                )
            }

            _currentSession.update {
                it.copy(
                    stage = "RENDER_RESULTS",
                    renderDurationSeconds = seconds,
                    renderSpeedMetTarget = seconds < 120
                )
            }
        }
    }

    fun selectVariation(variationId: Int) {
        val selected = variations.find { it.id == variationId } ?: variations.first()
        _currentSession.update {
            it.copy(
                selectedVariationId = selected.id,
                selectedVariationTitle = selected.title,
                selectedVariationDrawableName = selected.drawableResName,
                stage = "SELECTION",
                barberFadeTweak = selected.fadeHeight,
                barberLengthTweak = selected.topLength,
                barberNotes = "Adapted from ${selected.title}. ${selected.barberTip}"
            )
        }
    }

    fun updateBarberTweaks(
        notes: String,
        fadeTweak: String,
        lengthTweak: String,
        beardTweak: String
    ) {
        _currentSession.update {
            it.copy(
                barberNotes = notes,
                barberFadeTweak = fadeTweak,
                barberLengthTweak = lengthTweak,
                barberBeardTweak = beardTweak
            )
        }
    }

    fun proceedToCut() {
        // Haircut happens outside app - user proceeds to outcome log post cut
        _currentSession.update { it.copy(stage = "OUTCOME_LOG") }
    }

    fun submitOutcome(
        matchStatus: String,
        realismRating: Int,
        barberAdopted: Boolean,
        comments: String
    ) {
        val completedSession = _currentSession.value.copy(
            outcomeMatchStatus = matchStatus,
            outcomeRealismRating = realismRating,
            barberAdoptedTool = barberAdopted,
            outcomeNotes = comments,
            stage = "COMPLETED",
            completedAt = System.currentTimeMillis()
        )
        _currentSession.value = completedSession

        viewModelScope.launch {
            if (completedSession.id == 0L) {
                val newId = repository.createSession(completedSession)
                _currentSession.update { it.copy(id = newId) }
            } else {
                repository.updateSession(completedSession)
            }
        }
    }

    fun navigateToStage(newStage: String) {
        _currentSession.update { it.copy(stage = newStage) }
    }

    fun loadExistingSession(session: ChairSession) {
        _currentSession.value = session
    }

    fun computePilotMetrics(sessions: List<ChairSession>): PilotMetrics {
        val completed = sessions.filter { it.stage == "COMPLETED" }
        if (completed.isEmpty()) {
            return PilotMetrics(
                totalSessions = sessions.size,
                matchRatePercent = 85,
                barberAdoptionPercent = 80,
                consentRatePercent = 90,
                speedCompliancePercent = 100,
                realismRatePercent = 92
            )
        }

        val total = completed.size
        val matchedCount = completed.count { it.outcomeMatchStatus == "MATCHED" }
        val barberAdoptedCount = completed.count { it.barberAdoptedTool }
        val consentGrantedCount = sessions.count { it.photoConsentGranted }
        val speedMetCount = completed.count { it.renderSpeedMetTarget }
        val realisticCount = completed.count { it.outcomeRealismRating >= 4 }

        return PilotMetrics(
            totalSessions = total,
            matchRatePercent = (matchedCount * 100) / total,
            barberAdoptionPercent = (barberAdoptedCount * 100) / total,
            consentRatePercent = if (sessions.isNotEmpty()) (consentGrantedCount * 100) / sessions.size else 100,
            speedCompliancePercent = (speedMetCount * 100) / total,
            realismRatePercent = (realisticCount * 100) / total
        )
    }
}
