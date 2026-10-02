package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.ActivityLog
import com.example.data.model.AppSetting
import com.example.data.model.LeaderboardEntry
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.data.model.Student
import com.example.data.model.StudentGradeRow
import com.example.data.model.StudentProgress
import com.example.data.repository.EconomicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class UserRole {
    NONE, STUDENT, TEACHER
}

sealed class Screen {
    data object Auth : Screen()
    
    // Student screens
    data object StudentHome : Screen()
    data class StudentMaterialDetail(val topicId: Int) : Screen()
    data class StudentQuiz(val topicId: Int) : Screen()
    data object StudentGame : Screen()
    data object StudentLeaderboard : Screen()

    // Teacher screens
    data object TeacherDashboard : Screen()
    data object TeacherMaterials : Screen()
    data object TeacherGrades : Screen()
}

class EconomicsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EconomicsRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = EconomicsRepository(db.economicsDao(), application)
    }

    // Role & Navigation
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Auth)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _userRole = MutableStateFlow(UserRole.NONE)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    private val _currentStudent = MutableStateFlow<Student?>(null)
    val currentStudent: StateFlow<Student?> = _currentStudent.asStateFlow()

    // Teacher identity & school
    val teacherName = "Nur Salim, S. Pd"
    val schoolName = "SMA Negeri 1 Belitang II"

    // Data from repo
    val allTopics: StateFlow<List<MaterialTopic>> = repository.allTopics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudents: StateFlow<List<Student>> = repository.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activityLogs: StateFlow<List<ActivityLog>> = repository.allLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadLogsCount: StateFlow<Int> = repository.unreadLogsCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val leaderboard: StateFlow<List<LeaderboardEntry>> = repository.getLeaderboard()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSettings: StateFlow<List<AppSetting>> = repository.allSettings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Settings flags
    val isQuizUnlocked: StateFlow<Boolean> = allSettings.combine(_currentScreen) { settings, _ ->
        settings.firstOrNull { it.key == "quiz_unlocked" }?.value?.toBooleanStrictOrNull() ?: true
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val isGameUnlocked: StateFlow<Boolean> = allSettings.combine(_currentScreen) { settings, _ ->
        settings.firstOrNull { it.key == "game_unlocked" }?.value?.toBooleanStrictOrNull() ?: true
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // Grade table search and filters
    private val _gradeSearchQuery = MutableStateFlow("")
    val gradeSearchQuery: StateFlow<String> = _gradeSearchQuery.asStateFlow()

    private val _gradeSelectedClass = MutableStateFlow("Semua")
    val gradeSelectedClass: StateFlow<String> = _gradeSelectedClass.asStateFlow()

    val filteredGradeRows: StateFlow<List<StudentGradeRow>> = combine(
        repository.getGradeRows(),
        _gradeSearchQuery,
        _gradeSelectedClass
    ) { rows, query, selectedClass ->
        rows.filter { row ->
            val matchClass = if (selectedClass == "Semua") true else row.student.className.equals(selectedClass, ignoreCase = true)
            val matchSearch = query.isBlank() || 
                row.student.name.contains(query, ignoreCase = true) ||
                row.student.studentNumber.toString() == query.trim() ||
                row.student.className.contains(query, ignoreCase = true)
            matchClass && matchSearch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Student Progress
    private val _studentProgress = MutableStateFlow<List<StudentProgress>>(emptyList())
    val studentProgress: StateFlow<List<StudentProgress>> = _studentProgress.asStateFlow()

    // Notification / Toast Message
    private val _infoMessage = MutableStateFlow<String?>(null)
    val infoMessage: StateFlow<String?> = _infoMessage.asStateFlow()

    fun dismissInfoMessage() {
        _infoMessage.value = null
    }

    fun showInfo(msg: String) {
        _infoMessage.value = msg
    }

    // Navigation methods
    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun logout() {
        _userRole.value = UserRole.NONE
        _currentStudent.value = null
        _currentScreen.value = Screen.Auth
    }

    // Auth actions
    fun loginAsTeacher(password: String): Boolean {
        // Teacher pass check (default 123456)
        if (password.trim() == "123456" || password.trim() == "123" || password.trim() == "admin") {
            _userRole.value = UserRole.TEACHER
            _currentScreen.value = Screen.TeacherDashboard
            return true
        }
        return false
    }

    fun loginAsStudent(name: String, className: String, studentNumber: Int) {
        viewModelScope.launch {
            val student = repository.findOrCreateStudent(name, className, studentNumber)
            _currentStudent.value = student
            _userRole.value = UserRole.STUDENT
            _currentScreen.value = Screen.StudentHome
            // Collect progress
            repository.getStudentProgress(student.id).collect {
                _studentProgress.value = it
            }
        }
    }

    // Permission switches (Guru mengizinkan / mengunci Kuis dan Game)
    fun toggleQuizPermission(enabled: Boolean) {
        viewModelScope.launch {
            repository.setQuizUnlocked(enabled)
            val status = if (enabled) "DIBUKA untuk semua siswa" else "DIKUNCI oleh guru"
            showInfo("Akses Kuis berhasil $status.")
        }
    }

    fun toggleGamePermission(enabled: Boolean) {
        viewModelScope.launch {
            repository.setGameUnlocked(enabled)
            val status = if (enabled) "DIBUKA untuk semua siswa" else "DIKUNCI oleh guru"
            showInfo("Akses Game Pembelajaran berhasil $status.")
        }
    }

    // Activity log methods
    fun markLogsAsRead() {
        viewModelScope.launch {
            repository.markAllLogsAsRead()
        }
    }

    fun clearAllLogs() {
        viewModelScope.launch {
            repository.clearLogs()
            showInfo("Seluruh log aktivitas siswa telah dibersihkan.")
        }
    }

    // Material CRUD for teacher
    fun saveTopic(
        id: Int,
        chapterNumber: Int,
        chapterTitle: String,
        topicNumber: Int,
        topicTitle: String,
        summary: String,
        content: String,
        videoTitle: String,
        videoUrl: String,
        videoDuration: String,
        keyTakeaways: String
    ) {
        viewModelScope.launch {
            val topic = MaterialTopic(
                id = id,
                chapterNumber = chapterNumber,
                chapterTitle = chapterTitle,
                topicNumber = topicNumber,
                topicTitle = topicTitle,
                summary = summary,
                content = content,
                videoTitle = videoTitle,
                videoUrl = videoUrl,
                videoDuration = videoDuration,
                keyTakeaways = keyTakeaways
            )
            if (id == 0) {
                repository.insertTopic(topic)
                showInfo("Materi baru berhasil ditambahkan ke Bab $chapterNumber.")
            } else {
                repository.updateTopic(topic)
                showInfo("Materi Bab $chapterNumber berhasil diperbarui.")
            }
        }
    }

    fun deleteTopic(topic: MaterialTopic) {
        viewModelScope.launch {
            repository.deleteTopic(topic)
            showInfo("Materi '${topic.topicTitle}' berhasil dihapus.")
        }
    }

    // Quiz Questions for teacher
    fun getTopicQuestions(topicId: Int): Flow<List<QuizQuestion>> =
        repository.getQuestionsForTopic(topicId)

    fun saveQuestion(question: QuizQuestion) {
        viewModelScope.launch {
            if (question.id == 0) {
                repository.insertQuestion(question)
                showInfo("Soal kuis baru berhasil ditambahkan.")
            } else {
                repository.updateQuestion(question)
                showInfo("Soal kuis berhasil diperbarui.")
            }
        }
    }

    fun deleteQuestion(question: QuizQuestion) {
        viewModelScope.launch {
            repository.deleteQuestion(question)
            showInfo("Soal kuis berhasil dihapus.")
        }
    }

    // Student activities
    fun markMaterialFinished(topic: MaterialTopic) {
        val student = _currentStudent.value ?: return
        viewModelScope.launch {
            repository.recordMaterialRead(student, topic)
        }
    }

    fun markVideoWatched(topic: MaterialTopic) {
        val student = _currentStudent.value ?: return
        viewModelScope.launch {
            repository.recordVideoWatched(student, topic)
            showInfo("Video interaktif tercatat telah ditonton!")
        }
    }

    fun submitQuiz(topicId: Int, topicTitle: String, score: Int, correctCount: Int, totalQuestions: Int) {
        val student = _currentStudent.value ?: return
        viewModelScope.launch {
            repository.saveQuizResult(student, topicId, topicTitle, score, correctCount, totalQuestions)
            showInfo("Kuis berhasil dikirim! Nilai kamu: $score/100")
        }
    }

    fun submitGameScore(score: Int) {
        val student = _currentStudent.value ?: return
        viewModelScope.launch {
            repository.saveGameScore(student, score)
            showInfo("Skor game kamu: $score telah tercatat di Leaderboard!")
        }
    }

    // Grades filtering
    fun setGradeSearchQuery(query: String) {
        _gradeSearchQuery.value = query
    }

    fun setGradeSelectedClass(className: String) {
        _gradeSelectedClass.value = className
    }

    // Export Excel / CSV
    fun exportExcelCsv() {
        viewModelScope.launch {
            try {
                val file = repository.exportGradesToExcelCsv(_gradeSelectedClass.value)
                repository.shareExportedFile(file, "text/csv")
                showInfo("File Excel/CSV berhasil dibuat: ${file.name}")
            } catch (e: Exception) {
                showInfo("Gagal ekspor Excel: ${e.localizedMessage}")
            }
        }
    }

    // Export PDF
    fun exportPdf() {
        viewModelScope.launch {
            try {
                val file = repository.exportGradesToPdf(_gradeSelectedClass.value)
                repository.shareExportedFile(file, "application/pdf")
                showInfo("Laporan PDF berhasil dibuat: ${file.name}")
            } catch (e: Exception) {
                showInfo("Gagal ekspor PDF: ${e.localizedMessage}")
            }
        }
    }
}
