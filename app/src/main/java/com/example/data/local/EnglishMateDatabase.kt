package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.models.DailyTask
import com.example.data.models.SavedCorrection
import com.example.data.models.SavedWord
import com.example.data.models.UserProfile

@Database(
    entities = [
        UserProfile::class,
        DailyTask::class,
        SavedWord::class,
        SavedCorrection::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EnglishMateDatabase : RoomDatabase() {
    abstract fun dao(): EnglishMateDao

    companion object {
        @Volatile
        private var INSTANCE: EnglishMateDatabase? = null

        fun getDatabase(context: Context): EnglishMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnglishMateDatabase::class.java,
                    "englishmate_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
