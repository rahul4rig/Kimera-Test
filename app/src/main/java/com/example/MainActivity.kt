package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ConsentScreen
import com.example.ui.screens.DeclinedConsentScreen
import com.example.ui.screens.GeneratingPreviewScreen
import com.example.ui.screens.OutcomeLogScreen
import com.example.ui.screens.PilotDashboardSheet
import com.example.ui.screens.ReferenceCaptureScreen
import com.example.ui.screens.RenderResultsScreen
import com.example.ui.screens.SelectionDiscussionScreen
import com.example.ui.screens.SessionCompleteScreen
import com.example.ui.screens.SessionStartScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.WarmCream
import com.example.ui.viewmodel.KimeraSessionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WarmCream
                ) {
                    KimeraApp()
                }
            }
        }
    }
}

@Composable
fun KimeraApp(
    viewModel: KimeraSessionViewModel = viewModel()
) {
    val currentSession by viewModel.currentSession.collectAsStateWithLifecycle()
    val allSessions by viewModel.allSessions.collectAsStateWithLifecycle()
    val renderProgress by viewModel.renderProgress.collectAsStateWithLifecycle()
    val barberAdvice by viewModel.barberAdvice.collectAsStateWithLifecycle()

    var showPilotDashboard by remember { mutableStateOf(false) }

    val pilotMetrics = remember(allSessions) {
        viewModel.computePilotMetrics(allSessions)
    }

    val onTabSelected: (String) -> Unit = { tab ->
        when (tab) {
            "Session" -> viewModel.navigateToStage("START")
            "Preview" -> {
                if (currentSession.stage == "SELECTION" || currentSession.stage == "OUTCOME_LOG" || currentSession.stage == "COMPLETED") {
                    viewModel.navigateToStage("RENDER_RESULTS")
                } else if (currentSession.stage != "START" && currentSession.stage != "CONSENT") {
                    viewModel.navigateToStage("REFERENCE_CAPTURE")
                }
            }
            "Styling" -> {
                if (currentSession.stage == "RENDER_RESULTS" || currentSession.stage == "OUTCOME_LOG" || currentSession.stage == "COMPLETED") {
                    viewModel.navigateToStage("SELECTION")
                }
            }
            "Journal" -> {
                if (currentSession.completedAt != null || currentSession.stage == "COMPLETED") {
                    viewModel.navigateToStage("COMPLETED")
                } else {
                    showPilotDashboard = true
                }
            }
        }
    }

    when (currentSession.stage) {
        "START" -> {
            SessionStartScreen(
                onStartSession = { name, phone, barber, pricing ->
                    viewModel.startNewSession(name, phone, barber, pricing)
                },
                onOpenPilotDashboard = { showPilotDashboard = true },
                onTabSelected = onTabSelected
            )
        }

        "CONSENT" -> {
            BackHandler {
                viewModel.navigateToStage("START")
            }
            ConsentScreen(
                clientName = currentSession.clientName,
                onConfirmConsent = { photoConsent, retentionOptIn ->
                    viewModel.setConsent(photoConsent, retentionOptIn)
                },
                onDeclineConsent = {
                    viewModel.declineConsentAndExit()
                },
                onBack = {
                    viewModel.navigateToStage("START")
                }
            )
        }

        "DECLINED" -> {
            BackHandler {
                viewModel.navigateToStage("START")
            }
            DeclinedConsentScreen(
                clientName = currentSession.clientName,
                onRestartAiSession = {
                    viewModel.navigateToStage("CONSENT")
                },
                onCompleteConsultation = {
                    viewModel.navigateToStage("START")
                }
            )
        }

        "REFERENCE_CAPTURE" -> {
            BackHandler {
                viewModel.navigateToStage("CONSENT")
            }
            ReferenceCaptureScreen(
                clientName = currentSession.clientName,
                initialReferenceStyle = currentSession.referenceStyleName,
                initialFaceShape = currentSession.faceShape,
                initialHairTexture = currentSession.hairTexture,
                initialDesiredFade = currentSession.desiredFade,
                onGenerate = { refName, faceShape, hairTexture, desiredFade, clientPhoto, refPhoto ->
                    viewModel.updateReferenceAndProfile(
                        referenceStyleName = refName,
                        faceShape = faceShape,
                        hairTexture = hairTexture,
                        desiredFade = desiredFade,
                        clientPhotoPath = clientPhoto,
                        referencePhotoPath = refPhoto
                    )
                    viewModel.startGeneratingPreview()
                },
                onBack = {
                    viewModel.navigateToStage("CONSENT")
                }
            )
        }

        "GENERATING" -> {
            BackHandler {
                viewModel.navigateToStage("REFERENCE_CAPTURE")
            }
            GeneratingPreviewScreen(
                clientName = currentSession.clientName,
                referenceStyleName = currentSession.referenceStyleName,
                progressState = renderProgress
            )
        }

        "RENDER_RESULTS" -> {
            BackHandler {
                viewModel.navigateToStage("REFERENCE_CAPTURE")
            }
            RenderResultsScreen(
                clientName = currentSession.clientName,
                referenceStyleName = currentSession.referenceStyleName,
                variations = viewModel.variations,
                selectedVariationId = currentSession.selectedVariationId,
                barberAdvice = barberAdvice,
                onSelectVariation = { variationId ->
                    viewModel.selectVariation(variationId)
                },
                onBack = {
                    viewModel.navigateToStage("REFERENCE_CAPTURE")
                },
                onTabSelected = onTabSelected
            )
        }

        "SELECTION" -> {
            BackHandler {
                viewModel.navigateToStage("RENDER_RESULTS")
            }
            val selectedVar = viewModel.variations.find { it.id == currentSession.selectedVariationId }
                ?: viewModel.variations.first()

            SelectionDiscussionScreen(
                clientName = currentSession.clientName,
                selectedVariation = selectedVar,
                barberName = currentSession.barberName,
                barberAdvice = barberAdvice,
                initialNotes = currentSession.barberNotes,
                onProceedToCut = { notes, fadeTweak, lengthTweak, beardTweak ->
                    viewModel.updateBarberTweaks(notes, fadeTweak, lengthTweak, beardTweak)
                    viewModel.proceedToCut()
                },
                onBack = {
                    viewModel.navigateToStage("RENDER_RESULTS")
                },
                onTabSelected = onTabSelected
            )
        }

        "OUTCOME_LOG" -> {
            BackHandler {
                viewModel.navigateToStage("SELECTION")
            }
            OutcomeLogScreen(
                clientName = currentSession.clientName,
                chosenStyleTitle = currentSession.selectedVariationTitle,
                onSubmitOutcome = { match, realism, adopted, notes ->
                    viewModel.submitOutcome(match, realism, adopted, notes)
                },
                onBack = {
                    viewModel.navigateToStage("SELECTION")
                },
                onTabSelected = onTabSelected
            )
        }

        "COMPLETED" -> {
            BackHandler {
                viewModel.navigateToStage("START")
            }
            SessionCompleteScreen(
                session = currentSession,
                pilotMetrics = pilotMetrics,
                onStartNewSession = {
                    viewModel.startNewSession("Rohan V.", "+91 98450 12345", "Vikram", "fee_100")
                },
                onOpenPilotDashboard = {
                    showPilotDashboard = true
                },
                onTabSelected = onTabSelected
            )
        }

        else -> {
            SessionStartScreen(
                onStartSession = { name, phone, barber, pricing ->
                    viewModel.startNewSession(name, phone, barber, pricing)
                },
                onOpenPilotDashboard = { showPilotDashboard = true },
                onTabSelected = onTabSelected
            )
        }
    }

    if (showPilotDashboard) {
        PilotDashboardSheet(
            sessions = allSessions,
            metrics = pilotMetrics,
            onDismiss = { showPilotDashboard = false },
            onSelectSession = { session ->
                viewModel.loadExistingSession(session)
            }
        )
    }
}
