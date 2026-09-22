package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "Learner",
    val confidence: String = "Beginner",
    val learningGoal: String = "Speak English confidently",
    val dailyStudyTime: String = "20 minutes",
    val preferredPractice: String = "Speaking, Vocabulary",
    val cefrLevel: String = "B1",
    val overallScore: Int = 55,
    val grammarScore: Int = 60,
    val vocabScore: Int = 55,
    val sentenceScore: Int = 50,
    val readingScore: Int = 65,
    val everydayScore: Int = 50,
    val strongestSkill: String = "Reading",
    val weakestSkill: String = "Speaking",
    val xp: Int = 45,
    val streak: Int = 3,
    val lastActiveDate: String = "",
    val totalSpeakingMinutes: Int = 18,
    val tutorSessionsCount: Int = 2,
    val isOnboarded: Boolean = true,
    val hasTakenAssessment: Boolean = true
)

@Entity(tableName = "daily_tasks")
data class DailyTask(
    @PrimaryKey val id: String,
    val title: String,
    val category: String, // Grammar, Vocabulary, Sentence Builder, Reading, Speaking, Listening, Pronunciation, Revision
    val cefrLevel: String,
    val durationMinutes: Int,
    val xpReward: Int,
    val isCompleted: Boolean = false,
    val dateKey: String = ""
)

@Entity(tableName = "saved_words")
data class SavedWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val phonetic: String,
    val definition: String,
    val exampleSentence: String,
    val isMastered: Boolean = false,
    val dateAdded: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_corrections")
data class SavedCorrection(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val youSaid: String,
    val better: String,
    val why: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class PlacementQuestion(
    val id: Int,
    val section: String, // Grammar, Vocabulary, Sentence Formation, Reading, Everyday English
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val levelWeight: String // A1 to C2
)

data class AssessmentResult(
    val overallScore: Int,
    val estimatedCefrLevel: String,
    val grammarScore: Int,
    val vocabularyScore: Int,
    val sentenceScore: Int,
    val readingScore: Int,
    val everydayScore: Int,
    val strongestSkill: String,
    val weakestSkill: String
)

data class GrammarQuiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class GrammarLesson(
    val id: String,
    val title: String,
    val level: String,
    val explanation: String,
    val rule: String,
    val examples: List<String>,
    val quizzes: List<GrammarQuiz>
)

data class VocabWord(
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val meaning: String,
    val example: String,
    val level: String = "B1"
)

data class ReadingPassage(
    val id: String,
    val title: String,
    val level: String,
    val category: String,
    val passage: String,
    val keyWords: List<String>,
    val questions: List<GrammarQuiz>
)

data class SentenceExercise(
    val id: String,
    val level: String,
    val targetSentence: String,
    val scrambledWords: List<String>,
    val hint: String,
    val betterSuggestion: String = ""
)

data class ListeningExercise(
    val id: String,
    val title: String,
    val level: String,
    val textToSpeak: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class SpeakingExercise(
    val id: String,
    val title: String,
    val level: String,
    val prompt: String,
    val targetPhrase: String,
    val contextTip: String
)

data class PronunciationExercise(
    val id: String,
    val soundLabel: String,
    val word1: String,
    val word2: String,
    val tip: String,
    val exampleSentence: String
)

data class ChatMessage(
    val id: String,
    val sender: String, // "tutor" | "user"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val correction: TutorCorrection? = null
)

data class TutorCorrection(
    val youSaid: String,
    val better: String,
    val why: String
)

data class TutorSessionSummary(
    val durationMinutes: Int,
    val turns: Int,
    val speakingSeconds: Int,
    val newVocab: List<String>,
    val corrections: List<TutorCorrection>,
    val grammarAreas: List<String>,
    val confidenceFeedback: String,
    val suggestedNext: String,
    val xpAwarded: Int
)

data class AchievementItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean,
    val progress: String = ""
)
