package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiTutorService
import com.example.data.content.LearningCurriculum
import com.example.data.content.PlacementTestQuestions
import com.example.data.local.EnglishMateDatabase
import com.example.data.models.AssessmentResult
import com.example.data.models.ChatMessage
import com.example.data.models.DailyTask
import com.example.data.models.SavedCorrection
import com.example.data.models.SavedWord
import com.example.data.models.TutorCorrection
import com.example.data.models.TutorSessionSummary
import com.example.data.models.UserProfile
import com.example.data.repository.LearningRepository
import com.example.voice.VoiceManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppScreen {
    HOME,
    LEARN,
    PRACTICE,
    LIVE_TUTOR,
    PROGRESS,
    PROFILE,
    ONBOARDING,
    PLACEMENT_TEST,
    PLACEMENT_RESULT
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = EnglishMateDatabase.getDatabase(application)
    private val repository = LearningRepository(database.dao())
    val voiceManager = VoiceManager(application)
    private val tutorService = GeminiTutorService()

    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val dailyTasks: StateFlow<List<DailyTask>> = repository.dailyTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedWords: StateFlow<List<SavedWord>> = repository.savedWords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedCorrections: StateFlow<List<SavedCorrection>> = repository.savedCorrections
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Placement Test State
    private val _placementQuestionIndex = MutableStateFlow(0)
    val placementQuestionIndex: StateFlow<Int> = _placementQuestionIndex.asStateFlow()

    private val _placementAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val placementAnswers: StateFlow<Map<Int, Int>> = _placementAnswers.asStateFlow()

    private val _lastAssessmentResult = MutableStateFlow<AssessmentResult?>(null)
    val lastAssessmentResult: StateFlow<AssessmentResult?> = _lastAssessmentResult.asStateFlow()

    // Live Tutor State
    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()

    private val _isMicMuted = MutableStateFlow(false)
    val isMicMuted: StateFlow<Boolean> = _isMicMuted.asStateFlow()

    private val _isCameraOn = MutableStateFlow(false)
    val isCameraOn: StateFlow<Boolean> = _isCameraOn.asStateFlow()

    private val _isSpeakerOn = MutableStateFlow(true)
    val isSpeakerOn: StateFlow<Boolean> = _isSpeakerOn.asStateFlow()

    private val _selectedTutorMode = MutableStateFlow("Casual Conversation")
    val selectedTutorMode: StateFlow<String> = _selectedTutorMode.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _sessionDurationSeconds = MutableStateFlow(0)
    val sessionDurationSeconds: StateFlow<Int> = _sessionDurationSeconds.asStateFlow()

    private val _isTutorResponding = MutableStateFlow(false)
    val isTutorResponding: StateFlow<Boolean> = _isTutorResponding.asStateFlow()

    private val _tutorSummary = MutableStateFlow<TutorSessionSummary?>(null)
    val tutorSummary: StateFlow<TutorSessionSummary?> = _tutorSummary.asStateFlow()

    private val _showSummaryDialog = MutableStateFlow(false)
    val showSummaryDialog: StateFlow<Boolean> = _showSummaryDialog.asStateFlow()

    private var sessionTimerJob: Job? = null
    private var sessionStartTimeMillis: Long = 0
    private var gatheredCorrections = mutableListOf<TutorCorrection>()
    private var gatheredVocab = mutableListOf<String>()

    init {
        viewModelScope.launch {
            // Seed initial profile and daily tasks if not present
            val profile = repository.getCurrentProfile()
            if (!profile.isOnboarded) {
                // If brand new, show onboarding
                _currentScreen.value = AppScreen.ONBOARDING
            } else {
                _currentScreen.value = AppScreen.HOME
            }

            // Ensure daily tasks exist
            val tasks = database.dao().getUserProfileOnce()
            refreshDailyTasksIfNeeded(profile)
        }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun completeOnboarding(
        name: String,
        confidence: String,
        goal: String,
        dailyTime: String,
        preferred: String
    ) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile()
            val updated = current.copy(
                name = name.ifBlank { "English Learner" },
                confidence = confidence,
                learningGoal = goal,
                dailyStudyTime = dailyTime,
                preferredPractice = preferred,
                isOnboarded = true
            )
            repository.saveProfile(updated)
            // Move directly to placement test to evaluate CEFR level
            _placementQuestionIndex.value = 0
            _placementAnswers.value = emptyMap()
            _currentScreen.value = AppScreen.PLACEMENT_TEST
        }
    }

    fun recordPlacementAnswer(questionId: Int, selectedOption: Int) {
        val current = _placementAnswers.value.toMutableMap()
        current[questionId] = selectedOption
        _placementAnswers.value = current
    }

    fun nextPlacementQuestion() {
        if (_placementQuestionIndex.value < PlacementTestQuestions.questions.size - 1) {
            _placementQuestionIndex.value += 1
        } else {
            finishPlacementTest()
        }
    }

    fun previousPlacementQuestion() {
        if (_placementQuestionIndex.value > 0) {
            _placementQuestionIndex.value -= 1
        }
    }

    fun finishPlacementTest() {
        val answers = _placementAnswers.value
        val result = PlacementTestQuestions.calculateAssessment(answers)
        _lastAssessmentResult.value = result

        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile()
            val updated = current.copy(
                cefrLevel = result.estimatedCefrLevel,
                overallScore = result.overallScore,
                grammarScore = result.grammarScore,
                vocabScore = result.vocabularyScore,
                sentenceScore = result.sentenceScore,
                readingScore = result.readingScore,
                everydayScore = result.everydayScore,
                strongestSkill = result.strongestSkill,
                weakestSkill = result.weakestSkill,
                hasTakenAssessment = true,
                xp = current.xp + 50 // award 50 XP for placement test completion
            )
            repository.saveProfile(updated)

            // Generate personalized plan based on new CEFR and weakest skill
            val newTasks = LearningCurriculum.generateDailyPlan(
                cefrLevel = result.estimatedCefrLevel,
                weakestSkill = result.weakestSkill,
                dailyTime = updated.dailyStudyTime
            )
            repository.setDailyTasks(newTasks)

            _currentScreen.value = AppScreen.PLACEMENT_RESULT
        }
    }

    fun startPersonalizedPlan() {
        _currentScreen.value = AppScreen.HOME
    }

    fun retakePlacementTest() {
        _placementQuestionIndex.value = 0
        _placementAnswers.value = emptyMap()
        _currentScreen.value = AppScreen.PLACEMENT_TEST
    }

    private suspend fun refreshDailyTasksIfNeeded(profile: UserProfile) {
        val currentTasks = database.dao().getDailyTasks()
        // If empty, generate
        viewModelScope.launch {
            dailyTasks.collect { list ->
                if (list.isEmpty()) {
                    val generated = LearningCurriculum.generateDailyPlan(
                        cefrLevel = profile.cefrLevel,
                        weakestSkill = profile.weakestSkill,
                        dailyTime = profile.dailyStudyTime
                    )
                    repository.setDailyTasks(generated)
                }
            }
        }
    }

    fun toggleTask(task: DailyTask) {
        viewModelScope.launch {
            val updated = task.copy(isCompleted = !task.isCompleted)
            repository.updateTask(updated)
        }
    }

    // ================= Live Tutor Functions =================
    fun setTutorMode(mode: String) {
        _selectedTutorMode.value = mode
    }

    fun toggleMic() {
        _isMicMuted.value = !_isMicMuted.value
    }

    fun toggleCamera() {
        _isCameraOn.value = !_isCameraOn.value
    }

    fun toggleSpeaker() {
        val newState = !_isSpeakerOn.value
        _isSpeakerOn.value = newState
        if (!newState) {
            voiceManager.stop()
        }
    }

    fun startTutorSession(mode: String = _selectedTutorMode.value) {
        _selectedTutorMode.value = mode
        _isSessionActive.value = true
        _sessionDurationSeconds.value = 0
        sessionStartTimeMillis = System.currentTimeMillis()
        gatheredCorrections.clear()
        gatheredVocab.clear()

        val welcomeText = when (mode) {
            "Job Interview Practice" -> "Hello! I am your EnglishMate AI interviewer. Thank you for joining today. Could you start by introducing yourself and your professional interests?"
            "Travel English" -> "Welcome to our travel session! Imagine you just landed at an international airport. How can I assist you with finding your gate or booking a hotel?"
            "Restaurant Conversation" -> "Welcome! Table for one? Have a seat please. Can I get you started with something refreshing to drink?"
            "Speaking Confidence" -> "Hi there! This is a judgment-free space to build speaking confidence. What is something you really enjoy doing in your free time?"
            "Grammar Correction" -> "Welcome to our live grammar clinic! Speak freely about your day or any topic, and I will share helpful real-time tips whenever there's a better phrasing."
            else -> "Hello! I am EnglishMate AI Tutor. It is wonderful to practice with you today. What exciting plans do you have for this week?"
        }

        val initialMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = "tutor",
            text = welcomeText
        )
        _chatMessages.value = listOf(initialMessage)

        if (_isSpeakerOn.value) {
            voiceManager.speak(welcomeText)
        }

        // Start duration timer
        sessionTimerJob?.cancel()
        sessionTimerJob = viewModelScope.launch {
            while (_isSessionActive.value) {
                delay(1000)
                _sessionDurationSeconds.value += 1
            }
        }
    }

    fun sendUserMessage(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = "user",
            text = text.trim()
        )
        _chatMessages.value = _chatMessages.value + userMsg

        _isTutorResponding.value = true

        viewModelScope.launch {
            val profile = userProfile.value ?: UserProfile()
            val result = tutorService.getTutorResponse(
                userMessage = text,
                history = _chatMessages.value,
                mode = _selectedTutorMode.value,
                userCefrLevel = profile.cefrLevel,
                userGoal = profile.learningGoal
            )

            _isTutorResponding.value = false

            if (result.correction != null) {
                gatheredCorrections.add(result.correction)
                // Persist correction locally
                repository.addCorrection(
                    SavedCorrection(
                        youSaid = result.correction.youSaid,
                        better = result.correction.better,
                        why = result.correction.why
                    )
                )
            }
            gatheredVocab.addAll(result.suggestedVocab)

            val tutorMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = "tutor",
                text = result.replyText,
                correction = result.correction
            )
            _chatMessages.value = _chatMessages.value + tutorMsg

            if (_isSpeakerOn.value) {
                voiceManager.speak(result.replyText)
            }
        }
    }

    fun endTutorSession() {
        sessionTimerJob?.cancel()
        voiceManager.stop()
        _isSessionActive.value = false

        val durationSec = _sessionDurationSeconds.value
        val durationMin = maxOf(1, durationSec / 60)
        val turns = _chatMessages.value.filter { it.sender == "user" }.size

        // Calculate XP: 5 min = 10 XP, 10 min = 20 XP, 15+ min = 30 XP, or based on meaningful turns
        val xpAwarded = when {
            durationMin >= 15 -> 30
            durationMin >= 10 -> 20
            durationMin >= 5 -> 10
            turns >= 3 -> 10 // ensure user is rewarded even in brief testing session
            else -> 5
        }

        val summary = TutorSessionSummary(
            durationMinutes = durationMin,
            turns = turns,
            speakingSeconds = durationSec / 2,
            newVocab = gatheredVocab.distinct(),
            corrections = gatheredCorrections.toList(),
            grammarAreas = listOf("Verb Tenses", "Natural Transitions", "Articles (a/an/the)"),
            confidenceFeedback = if (turns > 4) "Outstanding fluency and willingness to express complex thoughts!" else "Great effort taking steps to practice speaking aloud!",
            suggestedNext = "Review the newly saved vocabulary cards in your Learn tab and try Sentence Builder practice.",
            xpAwarded = xpAwarded
        )

        _tutorSummary.value = summary
        _showSummaryDialog.value = true

        viewModelScope.launch {
            repository.addXpAndCheckStreak(xpAwarded)
            val current = userProfile.value ?: UserProfile()
            repository.saveProfile(
                current.copy(
                    totalSpeakingMinutes = current.totalSpeakingMinutes + durationMin,
                    tutorSessionsCount = current.tutorSessionsCount + 1
                )
            )
        }
    }

    fun dismissSummaryDialog() {
        _showSummaryDialog.value = false
    }

    fun saveVocabWord(word: String, phonetic: String, definition: String, example: String) {
        viewModelScope.launch {
            repository.addSavedWord(
                SavedWord(
                    word = word,
                    phonetic = phonetic,
                    definition = definition,
                    exampleSentence = example
                )
            )
            repository.addXpAndCheckStreak(5)
        }
    }

    fun deleteSavedWord(id: Int) {
        viewModelScope.launch {
            repository.deleteSavedWord(id)
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            _placementQuestionIndex.value = 0
            _placementAnswers.value = emptyMap()
            _currentScreen.value = AppScreen.ONBOARDING
        }
    }

    override fun onCleared() {
        super.onCleared()
        sessionTimerJob?.cancel()
        voiceManager.shutdown()
    }
}
