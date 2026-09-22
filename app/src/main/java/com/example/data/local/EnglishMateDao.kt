package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.models.DailyTask
import com.example.data.models.SavedCorrection
import com.example.data.models.SavedWord
import com.example.data.models.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface EnglishMateDao {

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileOnce(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfile)

    // Daily Tasks
    @Query("SELECT * FROM daily_tasks")
    fun getDailyTasks(): Flow<List<DailyTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyTasks(tasks: List<DailyTask>)

    @Update
    suspend fun updateDailyTask(task: DailyTask)

    @Query("DELETE FROM daily_tasks")
    suspend fun clearDailyTasks()

    // Saved Vocabulary
    @Query("SELECT * FROM saved_words ORDER BY id DESC")
    fun getSavedWords(): Flow<List<SavedWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedWord(word: SavedWord)

    @Query("DELETE FROM saved_words WHERE id = :id")
    suspend fun deleteSavedWord(id: Int)

    // Saved Corrections
    @Query("SELECT * FROM saved_corrections ORDER BY id DESC LIMIT 20")
    fun getSavedCorrections(): Flow<List<SavedCorrection>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedCorrection(correction: SavedCorrection)
}
