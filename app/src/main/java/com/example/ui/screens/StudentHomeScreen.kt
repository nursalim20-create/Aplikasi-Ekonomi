package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val currentStudent by viewModel.currentStudent.collectAsStateWithLifecycle()
    val allTopics by viewModel.allTopics.collectAsStateWithLifecycle()
    val progressList by viewModel.studentProgress.collectAsStateWithLifecycle()
    val isQuizUnlocked by viewModel.isQuizUnlocked.collectAsStateWithLifecycle()
    val isGameUnlocked by viewModel.isGameUnlocked.collectAsStateWithLifecycle()
    val gradeRows by viewModel.filteredGradeRows.collectAsStateWithLifecycle()

    val student = currentStudent
    val studentRow = gradeRows.firstOrNull { it.student.id == student?.id }

    // Group topics by chapter (1, 2, 3, 4)
    val chapters = listOf(
        1 to "Konsep Dasar Ilmu Ekonomi",
        2 to "Masalah Ekonomi & Sistem Ekonomi",
        3 to "Pelaku Ekonomi & Kegiatan Ekonomi",
        4 to "Pasar & Terbentuknya Harga Pasar"
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SMA Negeri 1 Belitang II",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Pembelajaran Ekonomi Kelas X",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.logout() },
                        modifier = Modifier.testTag("btn_student_logout")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Keluar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolNavy)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Student Profile Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(SchoolNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = student?.name?.take(1)?.uppercase() ?: "S",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = student?.name ?: "Siswa",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Kelas ${student?.className} • No. Absen ${student?.studentNumber}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFEF3C7))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Kurikulum Merdeka",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = EcoGold,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.School, contentDescription = null, tint = SchoolNavy, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Guru: ${viewModel.teacherName}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }

                            val avg = studentRow?.averageScore ?: 0.0
                            Text(
                                text = "Rata² Kuis: ${if (avg > 0) String.format("%.1f", avg) else "-"}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (avg >= 75.0) SuccessGreen else SchoolNavy
                                )
                            )
                        }
                    }
                }
            }

            // Teacher Permission Status Alert (KUIS & GAME PERMISSION)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isQuizUnlocked && isGameUnlocked) Color(0xFFF0FDF4) else Color(0xFFFFFBEB))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Status Izin Perintah Guru Nur Salim, S. Pd:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (isQuizUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isQuizUnlocked) SuccessGreen else DangerRed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isQuizUnlocked) "Kuis: Boleh Dikerjakan" else "Kuis: Terkunci (Tunggu Guru)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isQuizUnlocked) SuccessGreen else DangerRed,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (isGameUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isGameUnlocked) SuccessGreen else DangerRed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isGameUnlocked) "Game: Boleh Dimainkan" else "Game: Terkunci (Tunggu Guru)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isGameUnlocked) SuccessGreen else DangerRed,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            // Quick Access: Game & Leaderboard
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Game Card
                    Card(
                        onClick = {
                            if (!isGameUnlocked) {
                                viewModel.showInfo("🔒 Game Pembelajaran masih TERKUNCI! Belum diizinkan oleh Guru Nur Salim, S. Pd.")
                            } else {
                                viewModel.navigateTo(Screen.StudentGame)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_open_game"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SchoolTeal.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Games, contentDescription = null, tint = SchoolTeal, modifier = Modifier.size(20.dp))
                                }
                                if (!isGameUnlocked) {
                                    Icon(Icons.Default.Lock, contentDescription = "Terkunci", tint = DangerRed, modifier = Modifier.size(16.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Game Ekonomi", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text(
                                if (isGameUnlocked) "Tantangan Cerdas" else "Terkunci guru",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isGameUnlocked) SchoolTeal else DangerRed
                            )
                        }
                    }

                    // Leaderboard Card
                    Card(
                        onClick = { viewModel.navigateTo(Screen.StudentLeaderboard) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_open_leaderboard"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EcoGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Leaderboard, contentDescription = null, tint = EcoGold, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Papan Peringkat", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text("Leaderboard Siswa", style = MaterialTheme.typography.labelSmall, color = EcoGold)
                        }
                    }
                }
            }

            // 4 Chapters Header
            item {
                Text(
                    text = "Materi Pembelajaran (4 Bab)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // 4 Chapters Accordion / Lists
            chapters.forEach { (chNum, chTitle) ->
                val topicsInChapter = allTopics.filter { it.chapterNumber == chNum }
                item {
                    ChapterSection(
                        chapterNumber = chNum,
                        chapterTitle = chTitle,
                        topics = topicsInChapter,
                        studentRow = studentRow,
                        isQuizUnlocked = isQuizUnlocked,
                        onTopicClick = { topic ->
                            viewModel.navigateTo(Screen.StudentMaterialDetail(topic.id))
                        },
                        onQuizClick = { topic ->
                            if (!isQuizUnlocked) {
                                viewModel.showInfo("🔒 Kuis Pokok Materi masih TERKUNCI! Tunggu perintah atau izin dari Guru Nur Salim, S. Pd.")
                            } else {
                                viewModel.navigateTo(Screen.StudentQuiz(topic.id))
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ChapterSection(
    chapterNumber: Int,
    chapterTitle: String,
    topics: List<com.example.data.model.MaterialTopic>,
    studentRow: com.example.data.model.StudentGradeRow?,
    isQuizUnlocked: Boolean,
    onTopicClick: (com.example.data.model.MaterialTopic) -> Unit,
    onQuizClick: (com.example.data.model.MaterialTopic) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SchoolNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$chapterNumber", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "BAB $chapterNumber",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SchoolNavy,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = chapterTitle,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            topics.forEach { topic ->
                val quizScore = studentRow?.quizScores?.get(topic.id)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onTopicClick(topic) }
                        .padding(vertical = 8.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(SchoolTeal.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${topic.topicNumber}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SchoolTeal)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = topic.topicTitle,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = EcoGold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Video (${topic.videoDuration})",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Quiz score badge / button
                    if (quizScore != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (quizScore >= 75) Color(0xFFDCFCE7) else Color(0xFFFEE2E2))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Kuis: $quizScore",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (quizScore >= 75) SuccessGreen else DangerRed
                                )
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isQuizUnlocked) SchoolNavy.copy(alpha = 0.1f) else Color(0xFFF1F5F9))
                                .clickable { onQuizClick(topic) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (!isQuizUnlocked) {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = DangerRed, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                }
                                Text(
                                    text = if (isQuizUnlocked) "Kuis 10 Soal" else "Kuis Terkunci",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isQuizUnlocked) SchoolNavy else Color.Gray
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
