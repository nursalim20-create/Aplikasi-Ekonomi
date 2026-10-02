package com.example.data.repository

import android.content.Context
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import android.content.Intent
import android.net.Uri
import com.example.data.db.EconomicsDao
import com.example.data.model.ActivityLog
import com.example.data.model.AppSetting
import com.example.data.model.GameScore
import com.example.data.model.LeaderboardEntry
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.data.model.QuizResult
import com.example.data.model.Student
import com.example.data.model.StudentGradeRow
import com.example.data.model.StudentProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EconomicsRepository(
    private val dao: EconomicsDao,
    private val context: Context
) {

    val allTopics: Flow<List<MaterialTopic>> = dao.getAllTopics()
    val allStudents: Flow<List<Student>> = dao.getAllStudents()
    val allQuizResults: Flow<List<QuizResult>> = dao.getAllQuizResults()
    val allGameScores: Flow<List<GameScore>> = dao.getAllGameScores()
    val allLogs: Flow<List<ActivityLog>> = dao.getAllLogs()
    val unreadLogsCount: Flow<Int> = dao.getUnreadLogsCount()
    val allSettings: Flow<List<AppSetting>> = dao.getAllSettings()

    fun getQuestionsForTopic(topicId: Int): Flow<List<QuizQuestion>> =
        dao.getQuestionsForTopic(topicId)

    suspend fun getQuestionsForTopicSync(topicId: Int): List<QuizQuestion> =
        dao.getQuestionsForTopicSync(topicId)

    fun getTopicsByChapter(chapter: Int): Flow<List<MaterialTopic>> =
        dao.getTopicsByChapter(chapter)

    fun getTopicById(id: Int): Flow<MaterialTopic?> =
        dao.getTopicById(id)

    suspend fun findOrCreateStudent(name: String, className: String, studentNumber: Int): Student {
        val existing = dao.findStudent(name.trim(), className.trim(), studentNumber)
        if (existing != null) {
            return existing
        }
        val newStudent = Student(
            name = name.trim(),
            className = className.trim(),
            studentNumber = studentNumber
        )
        val id = dao.insertStudent(newStudent)
        // Log activity
        dao.insertLog(
            ActivityLog(
                studentName = newStudent.name,
                studentClass = newStudent.className,
                actionType = "LOGIN",
                description = "Siswa ${newStudent.name} (${newStudent.className}, Absen $studentNumber) bergabung ke aplikasi."
            )
        )
        return newStudent.copy(id = id.toInt())
    }

    suspend fun saveQuizResult(
        student: Student,
        topicId: Int,
        topicTitle: String,
        score: Int,
        correctCount: Int,
        totalQuestions: Int
    ) {
        val result = QuizResult(
            studentId = student.id,
            studentName = student.name,
            studentClass = student.className,
            topicId = topicId,
            score = score,
            correctCount = correctCount,
            totalQuestions = totalQuestions
        )
        dao.insertQuizResult(result)

        // Activity log for teacher
        dao.insertLog(
            ActivityLog(
                studentName = student.name,
                studentClass = student.className,
                actionType = "KUIS",
                description = "Menyelesaikan kuis '$topicTitle' dengan nilai $score/100 (Benar $correctCount/$totalQuestions)."
            )
        )
    }

    suspend fun saveGameScore(student: Student, score: Int) {
        val gameScore = GameScore(
            studentId = student.id,
            studentName = student.name,
            studentClass = student.className,
            score = score
        )
        dao.insertGameScore(gameScore)

        // Activity log for teacher
        dao.insertLog(
            ActivityLog(
                studentName = student.name,
                studentClass = student.className,
                actionType = "GAME",
                description = "Bermain Game Pembelajaran Ekonomi Cerdas meraih skor $score poin."
            )
        )
    }

    suspend fun recordMaterialRead(student: Student, topic: MaterialTopic) {
        val current = dao.getProgressForStudentTopic(student.id, topic.id)
        if (current == null) {
            dao.insertOrUpdateProgress(
                StudentProgress(
                    studentId = student.id,
                    topicId = topic.id,
                    isMaterialRead = true,
                    isVideoWatched = false
                )
            )
            dao.insertLog(
                ActivityLog(
                    studentName = student.name,
                    studentClass = student.className,
                    actionType = "MATERI",
                    description = "Membaca dan menuntaskan materi Bab ${topic.chapterNumber}: ${topic.topicTitle}."
                )
            )
        } else if (!current.isMaterialRead) {
            dao.insertOrUpdateProgress(current.copy(isMaterialRead = true, lastUpdated = System.currentTimeMillis()))
            dao.insertLog(
                ActivityLog(
                    studentName = student.name,
                    studentClass = student.className,
                    actionType = "MATERI",
                    description = "Menuntaskan materi Bab ${topic.chapterNumber}: ${topic.topicTitle}."
                )
            )
        }
    }

    suspend fun recordVideoWatched(student: Student, topic: MaterialTopic) {
        val current = dao.getProgressForStudentTopic(student.id, topic.id)
        if (current == null) {
            dao.insertOrUpdateProgress(
                StudentProgress(
                    studentId = student.id,
                    topicId = topic.id,
                    isMaterialRead = false,
                    isVideoWatched = true
                )
            )
            dao.insertLog(
                ActivityLog(
                    studentName = student.name,
                    studentClass = student.className,
                    actionType = "VIDEO",
                    description = "Menonton video pembelajaran interaktif: ${topic.videoTitle}."
                )
            )
        } else if (!current.isVideoWatched) {
            dao.insertOrUpdateProgress(current.copy(isVideoWatched = true, lastUpdated = System.currentTimeMillis()))
            dao.insertLog(
                ActivityLog(
                    studentName = student.name,
                    studentClass = student.className,
                    actionType = "VIDEO",
                    description = "Menonton video pembelajaran interaktif: ${topic.videoTitle}."
                )
            )
        }
    }

    fun getStudentProgress(studentId: Int): Flow<List<StudentProgress>> =
        dao.getProgressForStudent(studentId)

    // Teacher Management
    suspend fun insertTopic(topic: MaterialTopic) = dao.insertTopic(topic)
    suspend fun updateTopic(topic: MaterialTopic) = dao.updateTopic(topic)
    suspend fun deleteTopic(topic: MaterialTopic) {
        dao.deleteTopic(topic)
        dao.deleteQuestionsForTopic(topic.id)
    }

    suspend fun insertQuestion(question: QuizQuestion) = dao.insertQuestion(question)
    suspend fun updateQuestion(question: QuizQuestion) = dao.updateQuestion(question)
    suspend fun deleteQuestion(question: QuizQuestion) = dao.deleteQuestion(question)

    suspend fun setQuizUnlocked(unlocked: Boolean) {
        dao.insertSetting(AppSetting("quiz_unlocked", unlocked.toString()))
    }

    suspend fun setGameUnlocked(unlocked: Boolean) {
        dao.insertSetting(AppSetting("game_unlocked", unlocked.toString()))
    }

    suspend fun markAllLogsAsRead() = dao.markAllLogsAsRead()
    suspend fun clearLogs() = dao.clearLogs()

    suspend fun getSettingSync(key: String): String? = dao.getSettingSync(key)?.value

    // Grade Table Flow (Wide Table: Student, Quiz 1..N, Game, Avg)
    fun getGradeRows(): Flow<List<StudentGradeRow>> {
        return combine(
            dao.getAllStudents(),
            dao.getAllQuizResults(),
            dao.getAllGameScores()
        ) { students, results, games ->
            students.map { student ->
                val studentResults = results.filter { it.studentId == student.id }
                // Highest score per topic
                val topicScores = mutableMapOf<Int, Int>()
                studentResults.forEach { r ->
                    val curr = topicScores[r.topicId] ?: 0
                    if (r.score > curr) {
                        topicScores[r.topicId] = r.score
                    }
                }
                val bestGame = games.filter { it.studentId == student.id }
                    .maxOfOrNull { it.score } ?: 0

                val scoresList = topicScores.values
                val avg = if (scoresList.isNotEmpty()) {
                    scoresList.average()
                } else 0.0

                val passedCount = scoresList.count { it >= 75 }

                StudentGradeRow(
                    student = student,
                    quizScores = topicScores,
                    highestGameScore = bestGame,
                    averageScore = avg,
                    passedCount = passedCount
                )
            }
        }
    }

    // Leaderboard flow (Combined Quiz + Game)
    fun getLeaderboard(): Flow<List<LeaderboardEntry>> {
        return combine(
            dao.getAllStudents(),
            dao.getAllQuizResults(),
            dao.getAllGameScores()
        ) { students, results, games ->
            students.map { student ->
                val sResults = results.filter { it.studentId == student.id }
                val bestQuizzes = mutableMapOf<Int, Int>()
                sResults.forEach { r ->
                    val c = bestQuizzes[r.topicId] ?: 0
                    if (r.score > c) bestQuizzes[r.topicId] = r.score
                }
                val totalQuiz = bestQuizzes.values.sum()
                val bestGame = games.filter { it.studentId == student.id }
                    .maxOfOrNull { it.score } ?: 0
                val combined = totalQuiz + bestGame

                LeaderboardEntry(
                    studentId = student.id,
                    studentName = student.name,
                    studentClass = student.className,
                    totalQuizScore = totalQuiz,
                    bestGameScore = bestGame,
                    combinedScore = combined,
                    quizzesCompleted = bestQuizzes.size
                )
            }.sortedByDescending { it.combinedScore }
        }
    }

    // EXCEL / CSV Export
    suspend fun exportGradesToExcelCsv(selectedClass: String? = null): File {
        val gradeRows = getGradeRows().first().let { list ->
            if (selectedClass.isNullOrBlank() || selectedClass == "Semua") list
            else list.filter { it.student.className == selectedClass }
        }
        val topics = allTopics.first().sortedBy { it.id }

        val sb = java.lang.StringBuilder()
        // Header info
        sb.append("LAPORAN NILAI PEMBELAJARAN EKONOMI KELAS X\n")
        sb.append("SMA NEGERI 1 BELITANG II\n")
        sb.append("Guru Pengampu: Nur Salim, S. Pd\n")
        val dateStr = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id", "ID")).format(Date())
        sb.append("Tanggal Ekspor: $dateStr\n")
        sb.append("Filter Kelas: ${selectedClass ?: "Semua Kelas"}\n\n")

        // Table Columns
        sb.append("No,Nama Siswa,Kelas,No Absen")
        topics.forEachIndexed { index, t ->
            sb.append(",Kuis ${index + 1} (Bab ${t.chapterNumber}.${t.topicNumber})")
        }
        sb.append(",Nilai Game,Nilai Rata-rata,Status Kelulusan\n")

        // Data Rows
        gradeRows.forEachIndexed { idx, row ->
            sb.append("${idx + 1},\"${row.student.name}\",${row.student.className},${row.student.studentNumber}")
            topics.forEach { t ->
                val score = row.quizScores[t.id] ?: 0
                sb.append(",$score")
            }
            val status = if (row.averageScore >= 75.0) "TUNTAS" else "REMEDIAL"
            sb.append(",${row.highestGameScore},${String.format(Locale.US, "%.1f", row.averageScore)},$status\n")
        }

        val exportDir = File(context.cacheDir, "exports")
        if (!exportDir.exists()) exportDir.mkdirs()
        val fileName = "Nilai_Ekonomi_SMAN1_Belitang2_${System.currentTimeMillis()}.csv"
        val file = File(exportDir, fileName)
        file.writeText(sb.toString())
        return file
    }

    // PDF Export
    suspend fun exportGradesToPdf(selectedClass: String? = null): File {
        val gradeRows = getGradeRows().first().let { list ->
            if (selectedClass.isNullOrBlank() || selectedClass == "Semua") list
            else list.filter { it.student.className == selectedClass }
        }
        val topics = allTopics.first().sortedBy { it.id }

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create() // Landscape A4: 842 x 595 pt
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply {
            color = AndroidColor.parseColor("#1E3A8A")
            textSize = 15f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val subPaint = Paint().apply {
            color = AndroidColor.DKGRAY
            textSize = 9.5f
            isAntiAlias = true
        }
        val textPaint = Paint().apply {
            color = AndroidColor.BLACK
            textSize = 7.5f
            isAntiAlias = true
        }
        val headerPaint = Paint().apply {
            color = AndroidColor.parseColor("#1E3A8A")
            textSize = 7.5f
            isFakeBoldText = true
            isAntiAlias = true
        }
        val linePaint = Paint().apply {
            color = AndroidColor.LTGRAY
            strokeWidth = 0.8f
        }
        val bgHeaderPaint = Paint().apply {
            color = AndroidColor.parseColor("#E0E7FF")
        }
        val bgAltPaint = Paint().apply {
            color = AndroidColor.parseColor("#F8FAFC")
        }

        var y = 35f
        canvas.drawText("SMA NEGERI 1 BELITANG II - REKAPITULASI NILAI EKONOMI KELAS X", 30f, y, titlePaint)
        y += 15f
        val dateStr = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id", "ID")).format(Date())
        canvas.drawText("Guru Pengampu: Nur Salim, S. Pd  |  Filter Kelas: ${selectedClass ?: "Semua Kelas (X.1 - X.7)"}  |  Dicetak: $dateStr", 30f, y, subPaint)
        y += 20f

        // Table layout
        val startX = 30f
        val rowHeight = 18f
        val colWidths = mutableListOf(24f, 130f, 40f, 32f) // No, Nama, Kelas, Absen
        // For each topic: width 34f
        val maxTopicsToShow = minOf(topics.size, 8)
        for (i in 0 until maxTopicsToShow) {
            colWidths.add(36f)
        }
        colWidths.add(44f) // Game
        colWidths.add(44f) // Rata-rata
        colWidths.add(54f) // Status

        val totalTableWidth = colWidths.sum()

        // Draw header background
        canvas.drawRect(startX, y, startX + totalTableWidth, y + rowHeight, bgHeaderPaint)
        canvas.drawLine(startX, y, startX + totalTableWidth, y, linePaint)
        canvas.drawLine(startX, y + rowHeight, startX + totalTableWidth, y + rowHeight, linePaint)

        var curX = startX
        val headers = mutableListOf("No", "Nama Siswa", "Kelas", "Abs")
        for (i in 0 until maxTopicsToShow) {
            headers.add("K${i + 1}")
        }
        headers.add("Game")
        headers.add("Rata²")
        headers.add("Status")

        headers.forEachIndexed { i, title ->
            canvas.drawText(title, curX + 3f, y + 12f, headerPaint)
            curX += colWidths[i]
        }
        y += rowHeight

        // Draw Rows
        gradeRows.forEachIndexed { rowIdx, row ->
            if (y > 540f) return@forEachIndexed // prevent out of page
            if (rowIdx % 2 == 1) {
                canvas.drawRect(startX, y, startX + totalTableWidth, y + rowHeight, bgAltPaint)
            }
            canvas.drawLine(startX, y + rowHeight, startX + totalTableWidth, y + rowHeight, linePaint)

            var xPos = startX
            val values = mutableListOf(
                "${rowIdx + 1}",
                if (row.student.name.length > 22) row.student.name.take(20) + ".." else row.student.name,
                row.student.className,
                "${row.student.studentNumber}"
            )
            for (i in 0 until maxTopicsToShow) {
                val t = topics.getOrNull(i)
                val s = if (t != null) row.quizScores[t.id] ?: 0 else 0
                values.add("$s")
            }
            values.add("${row.highestGameScore}")
            values.add(String.format(Locale.US, "%.1f", row.averageScore))
            values.add(if (row.averageScore >= 75.0) "TUNTAS" else "REMEDIAL")

            values.forEachIndexed { colIdx, text ->
                canvas.drawText(text, xPos + 3f, y + 12f, textPaint)
                xPos += colWidths[colIdx]
            }
            y += rowHeight
        }

        // Footer note
        y += 20f
        canvas.drawText("* KKM Mata Pelajaran Ekonomi = 75  |  Diverifikasi oleh Guru Mata Pelajaran: Nur Salim, S. Pd", startX, y, subPaint)

        pdfDocument.finishPage(page)

        val exportDir = File(context.cacheDir, "exports")
        if (!exportDir.exists()) exportDir.mkdirs()
        val pdfFile = File(exportDir, "Rekap_Nilai_Ekonomi_SMAN1_${System.currentTimeMillis()}.pdf")
        val out = FileOutputStream(pdfFile)
        pdfDocument.writeTo(out)
        out.close()
        pdfDocument.close()

        return pdfFile
    }

    fun shareExportedFile(file: File, mimeType: String) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Laporan Nilai Ekonomi Kelas X - SMAN 1 Belitang II")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Buka atau Bagikan Nilai Siswa:").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }
}
