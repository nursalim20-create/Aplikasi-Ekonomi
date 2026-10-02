package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ActivityLog
import com.example.data.model.AppSetting
import com.example.data.model.GameScore
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizResult
import com.example.data.model.Student
import com.example.data.model.StudentProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface EconomicsDao {

    // Students
    @Query("SELECT * FROM students ORDER BY className ASC, studentNumber ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE className = :className ORDER BY studentNumber ASC")
    fun getStudentsByClass(className: String): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    fun getStudentById(id: Int): Flow<Student?>

    @Query("SELECT * FROM students WHERE LOWER(name) = LOWER(:name) AND className = :className AND studentNumber = :studentNumber LIMIT 1")
    suspend fun findStudent(name: String, className: String, studentNumber: Int): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Delete
    suspend fun deleteStudent(student: Student)

    // Materials
    @Query("SELECT * FROM material_topics ORDER BY chapterNumber ASC, topicNumber ASC")
    fun getAllTopics(): Flow<List<MaterialTopic>>

    @Query("SELECT * FROM material_topics WHERE chapterNumber = :chapter ORDER BY topicNumber ASC")
    fun getTopicsByChapter(chapter: Int): Flow<List<MaterialTopic>>

    @Query("SELECT * FROM material_topics WHERE id = :id LIMIT 1")
    fun getTopicById(id: Int): Flow<MaterialTopic?>

    @Query("SELECT COUNT(*) FROM material_topics")
    suspend fun getTopicCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: MaterialTopic): Long

    @Update
    suspend fun updateTopic(topic: MaterialTopic)

    @Delete
    suspend fun deleteTopic(topic: MaterialTopic)

    // Quiz Questions
    @Query("SELECT * FROM quiz_questions WHERE topicId = :topicId ORDER BY id ASC")
    fun getQuestionsForTopic(topicId: Int): Flow<List<QuizQuestion>>

    @Query("SELECT * FROM quiz_questions WHERE topicId = :topicId ORDER BY id ASC")
    suspend fun getQuestionsForTopicSync(topicId: Int): List<QuizQuestion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizQuestion>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuizQuestion): Long

    @Update
    suspend fun updateQuestion(question: QuizQuestion)

    @Delete
    suspend fun deleteQuestion(question: QuizQuestion)

    @Query("DELETE FROM quiz_questions WHERE topicId = :topicId")
    suspend fun deleteQuestionsForTopic(topicId: Int)

    // Quiz Results
    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC")
    fun getAllQuizResults(): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results WHERE studentId = :studentId ORDER BY completedAt DESC")
    fun getQuizResultsForStudent(studentId: Int): Flow<List<QuizResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResult): Long

    // Game Scores
    @Query("SELECT * FROM game_scores ORDER BY score DESC, completedAt ASC")
    fun getAllGameScores(): Flow<List<GameScore>>

    @Query("SELECT * FROM game_scores WHERE studentId = :studentId ORDER BY score DESC")
    fun getGameScoresForStudent(studentId: Int): Flow<List<GameScore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameScore(score: GameScore): Long

    // Activity Logs
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 150")
    fun getAllLogs(): Flow<List<ActivityLog>>

    @Query("SELECT COUNT(*) FROM activity_logs WHERE isRead = 0")
    fun getUnreadLogsCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ActivityLog): Long

    @Query("UPDATE activity_logs SET isRead = 1 WHERE isRead = 0")
    suspend fun markAllLogsAsRead()

    @Query("DELETE FROM activity_logs")
    suspend fun clearLogs()

    // Settings
    @Query("SELECT * FROM app_settings WHERE `key` = :key LIMIT 1")
    fun getSetting(key: String): Flow<AppSetting?>

    @Query("SELECT * FROM app_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingSync(key: String): AppSetting?

    @Query("SELECT * FROM app_settings")
    fun getAllSettings(): Flow<List<AppSetting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: AppSetting)

    // Progress
    @Query("SELECT * FROM student_progress WHERE studentId = :studentId")
    fun getProgressForStudent(studentId: Int): Flow<List<StudentProgress>>

    @Query("SELECT * FROM student_progress WHERE studentId = :studentId AND topicId = :topicId LIMIT 1")
    suspend fun getProgressForStudentTopic(studentId: Int, topicId: Int): StudentProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: StudentProgress)
}
