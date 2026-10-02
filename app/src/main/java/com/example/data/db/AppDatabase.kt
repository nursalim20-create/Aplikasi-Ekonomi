package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.ActivityLog
import com.example.data.model.AppSetting
import com.example.data.model.GameScore
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizResult
import com.example.data.model.Student
import com.example.data.model.StudentProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Student::class,
        MaterialTopic::class,
        QuizQuestion::class,
        QuizResult::class,
        GameScore::class,
        ActivityLog::class,
        AppSetting::class,
        StudentProgress::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun economicsDao(): EconomicsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ekonomi_belitang_db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Prepopulate default data
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getDatabase(context).economicsDao()
                            // Settings
                            DefaultEconomicsData.initialSettings.forEach { dao.insertSetting(it) }
                            // Topics
                            DefaultEconomicsData.initialTopics.forEach { dao.insertTopic(it) }
                            // Questions
                            dao.insertQuestions(DefaultEconomicsData.initialQuestions)
                            // Sample students
                            DefaultEconomicsData.sampleStudents.forEach { dao.insertStudent(it) }
                            // Sample Quiz results
                            DefaultEconomicsData.sampleQuizResults.forEach { dao.insertQuizResult(it) }
                            // Sample Game scores
                            DefaultEconomicsData.sampleGameScores.forEach { dao.insertGameScore(it) }
                            // Sample logs
                            DefaultEconomicsData.sampleActivityLogs.forEach { dao.insertLog(it) }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
