package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ActivityLog
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val activityLogs by viewModel.activityLogs.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadLogsCount.collectAsStateWithLifecycle()
    val gradeRows by viewModel.filteredGradeRows.collectAsStateWithLifecycle()
    val isQuizUnlocked by viewModel.isQuizUnlocked.collectAsStateWithLifecycle()
    val isGameUnlocked by viewModel.isGameUnlocked.collectAsStateWithLifecycle()

    var showNotificationsDialog by remember { mutableStateOf(false) }

    // Analytics calculations
    val totalStudents = students.size
    val totalScores = gradeRows.map { it.averageScore }.filter { it > 0.0 }
    val overallAverage = if (totalScores.isNotEmpty()) totalScores.average() else 0.0
    val passedCount = gradeRows.count { it.averageScore >= 75.0 && it.quizScores.isNotEmpty() }
    val passRate = if (gradeRows.isNotEmpty()) (passedCount.toDouble() / gradeRows.size.toDouble()) * 100.0 else 0.0

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Panel Guru: Nur Salim, S. Pd",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "SMA Negeri 1 Belitang II",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolNavy),
                actions = {
                    // Notifikasi Real-time
                    IconButton(
                        onClick = {
                            showNotificationsDialog = true
                            viewModel.markLogsAsRead()
                        },
                        modifier = Modifier.testTag("btn_teacher_notifications")
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadCount > 0) {
                                    Badge(containerColor = DangerRed) {
                                        Text("$unreadCount", color = Color.White)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifikasi Siswa",
                                tint = Color.White
                            )
                        }
                    }

                    // Logout
                    IconButton(
                        onClick = { viewModel.logout() },
                        modifier = Modifier.testTag("btn_teacher_logout")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Keluar",
                            tint = Color.White
                        )
                    }
                }
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
            // Permission Controls Card (CRITICAL REQUIREMENT)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SchoolNavy.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = SchoolNavy)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Kontrol Izin Akses Siswa",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = SchoolNavy
                                )
                                Text(
                                    text = "Materi bebas dibaca. Kuis & Game hanya bisa dikerjakan dengan izin guru.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(12.dp))

                        // Switch Izin Kuis
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (isQuizUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isQuizUnlocked) SuccessGreen else DangerRed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Izin Kuis Interaktif (10 Soal)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = if (isQuizUnlocked) "Status: DIBUKA (Siswa dapat mengerjakan)" else "Status: TERKUNCI (Siswa tidak bisa mengerjakan)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isQuizUnlocked) SuccessGreen else DangerRed
                                    )
                                }
                            }
                            Switch(
                                checked = isQuizUnlocked,
                                onCheckedChange = { viewModel.toggleQuizPermission(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = SuccessGreen
                                ),
                                modifier = Modifier.testTag("switch_quiz_permission")
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Switch Izin Game
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    if (isGameUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (isGameUnlocked) SchoolTeal else DangerRed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Izin Game Pembelajaran Ekonomi",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = if (isGameUnlocked) "Status: DIBUKA (Siswa dapat bermain)" else "Status: TERKUNCI (Siswa tidak bisa bermain)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isGameUnlocked) SchoolTeal else DangerRed
                                    )
                                }
                            }
                            Switch(
                                checked = isGameUnlocked,
                                onCheckedChange = { viewModel.toggleGamePermission(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = SchoolTeal
                                ),
                                modifier = Modifier.testTag("switch_game_permission")
                            )
                        }
                    }
                }
            }

            // Analytics Dashboard Summary
            item {
                Text(
                    text = "Dashboard Analitik Belajar Siswa",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Siswa",
                        value = "$totalStudents",
                        sub = "Kelas X.1 - X.7",
                        icon = Icons.Default.People,
                        color = SchoolNavy
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Rata-rata Nilai",
                        value = String.format(Locale.US, "%.1f", overallAverage),
                        sub = "KKM: 75.0",
                        icon = Icons.Default.TrendingUp,
                        color = EcoGold
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Tuntas KKM",
                        value = "${passRate.toInt()}%",
                        sub = "$passedCount siswa lulus",
                        icon = Icons.Default.CheckCircle,
                        color = SuccessGreen
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Aktivitas Tercatat",
                        value = "${activityLogs.size}",
                        sub = "Log real-time",
                        icon = Icons.Default.Assessment,
                        color = SchoolTeal
                    )
                }
            }

            // Quick Navigation Action Buttons
            item {
                Text(
                    text = "Menu Utama Panel Guru",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Card(
                    onClick = { viewModel.navigateTo(Screen.TeacherMaterials) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SchoolNavy.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Book, contentDescription = null, tint = SchoolNavy)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Kelola Materi Ajar (4 Bab & Video)",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Edit, hapus, tambah materi, tautan video interaktif, dan kuis.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray)
                    }
                }
            }

            item {
                Card(
                    onClick = { viewModel.navigateTo(Screen.TeacherGrades) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SuccessGreen.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Assessment, contentDescription = null, tint = SuccessGreen)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Rekapitulasi Nilai Siswa (Excel & PDF)",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Tabel nilai kesamping (Kuis 1..N, Game), filter kelas X.1-X.7 & download.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.Gray)
                    }
                }
            }

            // Real-Time Student Activity Feed
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Aktivitas Siswa Real-Time",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    TextButton(onClick = { viewModel.clearAllLogs() }) {
                        Text("Bersihkan Log", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            if (activityLogs.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(modifier = Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                            Text("Belum ada rekaman aktivitas siswa terkini.")
                        }
                    }
                }
            } else {
                items(activityLogs.take(15)) { log ->
                    ActivityLogItem(log = log)
                }
            }
        }
    }

    // Dialog Notifikasi Detail
    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = SchoolNavy)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pemberitahuan Aktivitas Siswa", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Notifikasi otomatis saat siswa menyelesaikan materi, kuis, atau game.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(activityLogs) { log ->
                            ActivityLogItem(log = log)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showNotificationsDialog = false }) {
                    Text("Tutup")
                }
            }
        )
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    sub: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = color
            )
            Text(
                text = sub,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ActivityLogItem(log: ActivityLog) {
    val dateStr = SimpleDateFormat("dd/MM HH:mm", Locale("id", "ID")).format(Date(log.timestamp))

    val badgeColor = when (log.actionType) {
        "KUIS" -> SuccessGreen
        "GAME" -> SchoolTeal
        "VIDEO" -> EcoGold
        else -> SchoolNavy
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(badgeColor)
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = log.actionType,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = log.studentName,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${log.studentClass})",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = log.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = dateStr,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
