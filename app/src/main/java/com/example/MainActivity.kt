package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.data.models.UserProfile
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.components.EnglishMateBottomNav
import com.example.ui.components.EnglishMateTopBar
import com.example.ui.home.HomeScreen
import com.example.ui.learn.LearnScreen
import com.example.ui.onboarding.OnboardingScreen
import com.example.ui.placement.PlacementResultScreen
import com.example.ui.placement.PlacementTestScreen
import com.example.ui.practice.PracticeScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.progress.ProgressScreen
import com.example.ui.theme.EnglishMateTheme
import com.example.ui.tutor.LiveTutorScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnglishMateTheme {
                EnglishMateApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun EnglishMateApp(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val dailyTasks by viewModel.dailyTasks.collectAsState()
    val savedWords by viewModel.savedWords.collectAsState()
    val savedCorrections by viewModel.savedCorrections.collectAsState()

    val profile = userProfile ?: UserProfile()

    when (currentScreen) {
        AppScreen.ONBOARDING -> {
            OnboardingScreen(
                onComplete = { name, confidence, goal, dailyTime, preferred ->
                    viewModel.completeOnboarding(name, confidence, goal, dailyTime, preferred)
                }
            )
        }

        AppScreen.PLACEMENT_TEST -> {
            val currentIndex by viewModel.placementQuestionIndex.collectAsState()
            val answers by viewModel.placementAnswers.collectAsState()

            PlacementTestScreen(
                currentIndex = currentIndex,
                answers = answers,
                onSelectAnswer = { qId, optIdx ->
                    viewModel.recordPlacementAnswer(qId, optIdx)
                },
                onNext = { viewModel.nextPlacementQuestion() },
                onPrevious = { viewModel.previousPlacementQuestion() },
                onSubmit = { viewModel.finishPlacementTest() }
            )
        }

        AppScreen.PLACEMENT_RESULT -> {
            val lastResult by viewModel.lastAssessmentResult.collectAsState()
            lastResult?.let { res ->
                PlacementResultScreen(
                    result = res,
                    onStartPersonalizedPlan = { viewModel.startPersonalizedPlan() },
                    onRetakeAssessment = { viewModel.retakePlacementTest() }
                )
            }
        }

        else -> {
            // Main App Container with TopBar and Bottom Navigation
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                topBar = {
                    EnglishMateTopBar(
                        streak = profile.streak,
                        xp = profile.xp,
                        onRetakePlacement = { viewModel.navigateTo(AppScreen.PLACEMENT_TEST) }
                    )
                },
                bottomBar = {
                    EnglishMateBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = { screen -> viewModel.navigateTo(screen) }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentScreen) {
                        AppScreen.HOME -> {
                            HomeScreen(
                                profile = profile,
                                tasks = dailyTasks,
                                onToggleTask = { task -> viewModel.toggleTask(task) },
                                onNavigate = { screen -> viewModel.navigateTo(screen) },
                                onStartTutorMode = { mode ->
                                    viewModel.setTutorMode(mode)
                                    viewModel.startTutorSession(mode)
                                },
                                onSpeak = { text, isSlow ->
                                    viewModel.voiceManager.speak(text, isSlow)
                                }
                            )
                        }

                        AppScreen.LEARN -> {
                            LearnScreen(
                                savedWords = savedWords,
                                onSaveWord = { w, p, m, e ->
                                    viewModel.saveVocabWord(w, p, m, e)
                                },
                                onSpeak = { text, isSlow ->
                                    viewModel.voiceManager.speak(text, isSlow)
                                }
                            )
                        }

                        AppScreen.PRACTICE -> {
                            PracticeScreen(
                                onSpeak = { text, isSlow ->
                                    viewModel.voiceManager.speak(text, isSlow)
                                }
                            )
                        }

                        AppScreen.LIVE_TUTOR -> {
                            val isActive by viewModel.isSessionActive.collectAsState()
                            val isMicMuted by viewModel.isMicMuted.collectAsState()
                            val isCameraOn by viewModel.isCameraOn.collectAsState()
                            val isSpeakerOn by viewModel.isSpeakerOn.collectAsState()
                            val selectedMode by viewModel.selectedTutorMode.collectAsState()
                            val chatMessages by viewModel.chatMessages.collectAsState()
                            val sessionDuration by viewModel.sessionDurationSeconds.collectAsState()
                            val isResponding by viewModel.isTutorResponding.collectAsState()
                            val summary by viewModel.tutorSummary.collectAsState()
                            val showSummary by viewModel.showSummaryDialog.collectAsState()

                            LiveTutorScreen(
                                isActive = isActive,
                                isMicMuted = isMicMuted,
                                isCameraOn = isCameraOn,
                                isSpeakerOn = isSpeakerOn,
                                selectedMode = selectedMode,
                                chatMessages = chatMessages,
                                sessionDurationSeconds = sessionDuration,
                                isTutorResponding = isResponding,
                                tutorSummary = summary,
                                showSummaryDialog = showSummary,
                                onStartSession = { mode -> viewModel.startTutorSession(mode) },
                                onEndSession = { viewModel.endTutorSession() },
                                onToggleMic = { viewModel.toggleMic() },
                                onToggleCamera = { viewModel.toggleCamera() },
                                onToggleSpeaker = { viewModel.toggleSpeaker() },
                                onSelectMode = { mode -> viewModel.setTutorMode(mode) },
                                onSendMessage = { msg -> viewModel.sendUserMessage(msg) },
                                onSpeak = { text, isSlow -> viewModel.voiceManager.speak(text, isSlow) },
                                onDismissSummary = { viewModel.dismissSummaryDialog() }
                            )
                        }

                        AppScreen.PROGRESS -> {
                            ProgressScreen(
                                profile = profile,
                                corrections = savedCorrections
                            )
                        }

                        AppScreen.PROFILE -> {
                            ProfileScreen(
                                profile = profile,
                                savedWords = savedWords,
                                onRetakePlacement = { viewModel.navigateTo(AppScreen.PLACEMENT_TEST) },
                                onDeleteSavedWord = { id -> viewModel.deleteSavedWord(id) },
                                onResetProgress = { viewModel.resetAllProgress() },
                                onSpeak = { text, isSlow -> viewModel.voiceManager.speak(text, isSlow) }
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}
