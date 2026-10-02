package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val className: String, // "X.1" .. "X.7"
    val studentNumber: Int, // No. Absen
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "material_topics")
data class MaterialTopic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterNumber: Int, // 1, 2, 3, 4
    val chapterTitle: String,
    val topicNumber: Int,
    val topicTitle: String,
    val summary: String,
    val content: String,
    val videoTitle: String,
    val videoUrl: String,
    val videoDuration: String,
    val keyTakeaways: String = ""
)

@Entity(tableName = "quiz_questions")
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val topicId: Int,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOption: Int, // 0 for A, 1 for B, 2 for C, 3 for D
    val explanation: String
)

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val studentName: String,
    val studentClass: String,
    val topicId: Int,
    val score: Int, // 0 - 100
    val correctCount: Int,
    val totalQuestions: Int = 10,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "game_scores")
data class GameScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val studentName: String,
    val studentClass: String,
    val score: Int,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "activity_logs")
data class ActivityLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentName: String,
    val studentClass: String,
    val actionType: String, // "KUIS", "GAME", "MATERI", "VIDEO"
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

@Entity(tableName = "app_settings")
data class AppSetting(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "student_progress")
data class StudentProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: Int,
    val topicId: Int,
    val isMaterialRead: Boolean = false,
    val isVideoWatched: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

// DTO for wide grade table
data class StudentGradeRow(
    val student: Student,
    val quizScores: Map<Int, Int>, // topicId -> highest score (or default 0)
    val highestGameScore: Int,
    val averageScore: Double,
    val passedCount: Int
)

// Leaderboard item
data class LeaderboardEntry(
    val studentId: Int,
    val studentName: String,
    val studentClass: String,
    val totalQuizScore: Int,
    val bestGameScore: Int,
    val combinedScore: Int,
    val quizzesCompleted: Int
)
