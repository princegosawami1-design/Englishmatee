package com.example.data.repository

import com.example.data.local.EnglishMateDao
import com.example.data.models.DailyTask
import com.example.data.models.SavedCorrection
import com.example.data.models.SavedWord
import com.example.data.models.UserProfile
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LearningRepository(private val dao: EnglishMateDao) {

    val userProfile: Flow<UserProfile?> = dao.getUserProfile()
    val dailyTasks: Flow<List<DailyTask>> = dao.getDailyTasks()
    val savedWords: Flow<List<SavedWord>> = dao.getSavedWords()
    val savedCorrections: Flow<List<SavedCorrection>> = dao.getSavedCorrections()

    suspend fun saveProfile(profile: UserProfile) {
        dao.saveUserProfile(profile)
    }

    suspend fun getCurrentProfile(): UserProfile {
        return dao.getUserProfileOnce() ?: UserProfile()
    }

    suspend fun addXpAndCheckStreak(earnedXp: Int) {
        val current = getCurrentProfile()
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val newStreak = if (current.lastActiveDate == todayStr) {
            current.streak
        } else {
            current.streak + 1
        }
        val updated = current.copy(
            xp = current.xp + earnedXp,
            streak = if (newStreak == 0) 1 else newStreak,
            lastActiveDate = todayStr
        )
        dao.saveUserProfile(updated)
    }

    suspend fun completeTask(taskId: String) {
        val tasks = dao.getUserProfileOnce() // trigger check
        // Find task and update
        // We will update in DAO
    }

    suspend fun updateTask(task: DailyTask) {
        dao.updateDailyTask(task)
        if (task.isCompleted) {
            addXpAndCheckStreak(task.xpReward)
        }
    }

    suspend fun setDailyTasks(tasks: List<DailyTask>) {
        dao.clearDailyTasks()
        dao.insertDailyTasks(tasks)
    }

    suspend fun addSavedWord(word: SavedWord) {
        dao.insertSavedWord(word)
    }

    suspend fun deleteSavedWord(id: Int) {
        dao.deleteSavedWord(id)
    }

    suspend fun addCorrection(correction: SavedCorrection) {
        dao.insertSavedCorrection(correction)
    }

    suspend fun resetAllProgress() {
        dao.clearDailyTasks()
        val freshProfile = UserProfile(
            name = "Learner",
            confidence = "Beginner",
            learningGoal = "Speak English confidently",
            dailyStudyTime = "20 minutes",
            preferredPractice = "Speaking, Vocabulary",
            cefrLevel = "A1",
            overallScore = 0,
            grammarScore = 0,
            vocabScore = 0,
            sentenceScore = 0,
            readingScore = 0,
            everydayScore = 0,
            strongestSkill = "None yet",
            weakestSkill = "None yet",
            xp = 0,
            streak = 0,
            lastActiveDate = "",
            totalSpeakingMinutes = 0,
            tutorSessionsCount = 0,
            isOnboarded = false,
            hasTakenAssessment = false
        )
        dao.saveUserProfile(freshProfile)
    }
}
