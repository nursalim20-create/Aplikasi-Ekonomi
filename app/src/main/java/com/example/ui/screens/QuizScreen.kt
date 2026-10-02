package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.QuizQuestion
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: EconomicsViewModel,
    topicId: Int,
    modifier: Modifier = Modifier
) {
    val allTopics by viewModel.allTopics.collectAsStateWithLifecycle()
    val topic = allTopics.firstOrNull { it.id == topicId }
    val questions: List<QuizQuestion> by viewModel.getTopicQuestions(topicId).collectAsStateWithLifecycle(initialValue = emptyList())

    var currentIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionIndex -> selectedOptionIndex (0..3)
    var isSubmitted by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    // Timer (15 minutes = 900 seconds)
    var timeRemainingSeconds by remember { mutableIntStateOf(900) }

    LaunchedEffect(isSubmitted) {
        while (!isSubmitted && timeRemainingSeconds > 0) {
            delay(1000)
            timeRemainingSeconds--
            if (timeRemainingSeconds == 0) {
                // Auto submit on time up
                isSubmitted = true
            }
        }
    }

    val minutes = timeRemainingSeconds / 60
    val seconds = timeRemainingSeconds % 60
    val timerStr = String.format("%02d:%02d", minutes, seconds)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kuis 10 Soal: ${topic?.topicTitle ?: "Ekonomi"}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = "Bab ${topic?.chapterNumber} • SMA N 1 Belitang II",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.StudentMaterialDetail(topicId)) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    if (!isSubmitted) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (timeRemainingSeconds < 120) DangerRed else SchoolTeal)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(timerStr, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolNavy)
            )
        }
    ) { innerPadding ->
        if (questions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Memuat soal kuis...")
            }
        } else if (!isSubmitted) {
            // QUIZ IN PROGRESS
            val totalQuestions = questions.size
            val currentQ = questions.getOrNull(currentIndex) ?: questions[0]
            val selectedOption = userAnswers[currentIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Progress Bar & Question Counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Soal ${currentIndex + 1} dari $totalQuestions",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = SchoolNavy
                    )
                    Text(
                        text = "Dijawab: ${userAnswers.size}/$totalQuestions",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = SchoolNavy,
                    trackColor = Color(0xFFE2E8F0)
                )

                // Number jumping chips
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(questions) { idx, _ ->
                        val isAnswered = userAnswers.containsKey(idx)
                        val isCur = idx == currentIndex
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when {
                                        isCur -> SchoolNavy
                                        isAnswered -> SuccessGreen
                                        else -> Color(0xFFE2E8F0)
                                    }
                                )
                                .clickable { currentIndex = idx },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                fontWeight = FontWeight.Bold,
                                color = if (isCur || isAnswered) Color.White else Color.Black,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // Question Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentQ.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 24.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // 4 Multiple Choice Options (A, B, C, D)
                        val options = listOf(
                            "A" to currentQ.optionA,
                            "B" to currentQ.optionB,
                            "C" to currentQ.optionC,
                            "D" to currentQ.optionD
                        )

                        options.forEachIndexed { optIndex, (label, text) ->
                            val isSelected = selectedOption == optIndex
                            val borderColor = if (isSelected) SchoolNavy else Color(0xFFE2E8F0)
                            val bgColor = if (isSelected) Color(0xFFEFF6FF) else Color.White

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
                                    .background(bgColor)
                                    .clickable { userAnswers[currentIndex] = optIndex }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SchoolNavy else Color(0xFFE2E8F0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = label,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else Color.DarkGray,
                                        fontSize = 13.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (isSelected) SchoolNavy else MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // Navigation Controls: Prev, Next, Submit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { if (currentIndex > 0) currentIndex-- },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sebelumnya")
                    }

                    if (currentIndex < totalQuestions - 1) {
                        Button(
                            onClick = { currentIndex++ },
                            colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Berikutnya")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    } else {
                        Button(
                            onClick = { showConfirmDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("btn_submit_quiz")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Kirim Jawaban", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            // QUIZ RESULTS & EXPLANATION REVIEW
            val totalQuestions = questions.size
            var correctCount = 0
            questions.forEachIndexed { idx, q ->
                if (userAnswers[idx] == q.correctOption) {
                    correctCount++
                }
            }
            val finalScore = (correctCount.toDouble() / totalQuestions.toDouble() * 100).toInt()
            val isPassed = finalScore >= 75

            // Submit once to database
            LaunchedEffect(Unit) {
                topic?.let {
                    viewModel.submitQuiz(
                        topicId = it.id,
                        topicTitle = it.topicTitle,
                        score = finalScore,
                        correctCount = correctCount,
                        totalQuestions = totalQuestions
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Score Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isPassed) "Selamat! Kamu Tuntas KKM" else "Perlu Belajar Lebih Giat",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isPassed) SuccessGreen else DangerRed
                        )
                        Text(
                            text = "Standar KKM Mata Pelajaran Ekonomi = 75",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score Circle
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(if (isPassed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$finalScore",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPassed) SuccessGreen else DangerRed
                                )
                                Text(
                                    text = "dari 100",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("$correctCount", fontWeight = FontWeight.Bold, color = SuccessGreen, fontSize = 18.sp)
                                Text("Jawaban Benar", style = MaterialTheme.typography.labelSmall)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${totalQuestions - correctCount}", fontWeight = FontWeight.Bold, color = DangerRed, fontSize = 18.sp)
                                Text("Jawaban Salah", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }

                // Button Return
                Button(
                    onClick = { viewModel.navigateTo(Screen.StudentHome) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Kembali ke Beranda Belajar", fontWeight = FontWeight.Bold)
                }

                // Question Explanations Review
                Text(
                    text = "Pembahasan Lengkap 10 Soal:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                )

                questions.forEachIndexed { idx, q ->
                    val userChoice = userAnswers[idx]
                    val isCorrect = userChoice == q.correctOption

                    val optList = listOf(q.optionA, q.optionB, q.optionC, q.optionD)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isCorrect) SuccessGreen else DangerRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Soal ${idx + 1}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(q.questionText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Jawabanmu: ${if (userChoice != null) "${listOf("A", "B", "C", "D")[userChoice]} - ${optList[userChoice]}" else "Tidak dijawab"}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isCorrect) SuccessGreen else DangerRed,
                                fontWeight = FontWeight.Bold
                            )
                            if (!isCorrect) {
                                Text(
                                    text = "Kunci Benar: ${listOf("A", "B", "C", "D")[q.correctOption]} - ${optList[q.correctOption]}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF8FAFC))
                                    .padding(8.dp)
                            ) {
                                Row {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = EcoGold, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Pembahasan: ${q.explanation}",
                                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF334155))
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    // Confirmation Dialog before submission
    if (showConfirmDialog) {
        val unanswered = questions.size - userAnswers.size
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Kirim Jawaban Kuis?") },
            text = {
                Text(
                    if (unanswered > 0)
                        "Masih ada $unanswered soal yang belum kamu jawab. Yakin ingin mengirim sekarang?"
                    else
                        "Semua ${questions.size} soal telah dijawab. Nilai akan otomatis dikalkulasi dan dilaporkan ke Guru Nur Salim, S. Pd."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        isSubmitted = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Text("Kirim Sekarang")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Periksa Lagi")
                }
            }
        )
    }
}
