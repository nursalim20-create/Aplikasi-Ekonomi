package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VideoLibrary
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentMaterialDetailScreen(
    viewModel: EconomicsViewModel,
    topicId: Int,
    modifier: Modifier = Modifier
) {
    val allTopics by viewModel.allTopics.collectAsStateWithLifecycle()
    val isQuizUnlocked by viewModel.isQuizUnlocked.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val topic = allTopics.firstOrNull { it.id == topicId }

    // Video interactive player simulation state
    var isPlaying by remember { mutableStateOf(false) }
    var playbackProgress by remember { mutableFloatStateOf(0.15f) }
    var showInteractiveQuestionDialog by remember { mutableStateOf(false) }
    var interactiveAnswerSubmitted by remember { mutableStateOf(false) }
    var selectedInteractiveOption by remember { mutableIntStateOf(-1) }

    // Locked quiz alert dialog
    var showQuizLockedAlert by remember { mutableStateOf(false) }

    // Auto progress simulation when playing
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(500)
            if (playbackProgress < 1.0f) {
                playbackProgress += 0.02f
                if (playbackProgress in 0.49f..0.52f && !interactiveAnswerSubmitted) {
                    isPlaying = false
                    showInteractiveQuestionDialog = true
                }
            } else {
                isPlaying = false
                topic?.let { viewModel.markVideoWatched(it) }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = topic?.topicTitle ?: "Materi Ekonomi",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = "Bab ${topic?.chapterNumber}: ${topic?.chapterTitle}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A)),
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.StudentHome) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolNavy)
            )
        }
    ) { innerPadding ->
        if (topic == null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Materi tidak ditemukan.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Interactive Video Player Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = EcoGold, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Video Pembelajaran Interaktif",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Text(
                                text = topic.videoDuration,
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Video Canvas Area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF1E293B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = topic.videoTitle,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "SMA N 1 Belitang II • Pengampu: Nur Salim, S. Pd",
                                    style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                // Play / Pause button
                                IconButton(
                                    onClick = { isPlaying = !isPlaying },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(SchoolTeal)
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = if (isPlaying) "Jeda" else "Putar",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            // Interactive Checkpoint badge on top right
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(EcoGold)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Ada Soal Interaktif!",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Progress slider
                        Slider(
                            value = playbackProgress,
                            onValueChange = {
                                playbackProgress = it
                                if (it >= 0.5f && !interactiveAnswerSubmitted) {
                                    isPlaying = false
                                    showInteractiveQuestionDialog = true
                                }
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = SchoolTeal,
                                activeTrackColor = SchoolTeal,
                                inactiveTrackColor = Color(0xFF334155)
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Progres: ${(playbackProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF94A3B8))
                            )

                            Row {
                                // Watch on external link
                                TextButton(
                                    onClick = {
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(topic.videoUrl))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            viewModel.showInfo("Membuka: ${topic.videoUrl}")
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = Color(0xFF60A5FA), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Buka YouTube", color = Color(0xFF60A5FA), fontSize = 12.sp)
                                }

                                // Mark video watched button
                                TextButton(
                                    onClick = { viewModel.markVideoWatched(topic) }
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Selesai Nonton", color = SuccessGreen, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                // Summary Box
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = SchoolTeal, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ringkasan Pokok Materi", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(topic.summary, style = MaterialTheme.typography.bodySmall)
                    }
                }

                // Full Content Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Penjelasan Materi Pelajaran",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SchoolNavy
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = topic.content,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                        )
                    }
                }

                // Key Takeaways Card
                if (topic.keyTakeaways.isNotBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = EcoGold, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Poin Kunci Yang Harus Diingat",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = EcoGold,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = topic.keyTakeaways,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF78350F),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                // Action Buttons at Bottom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.markMaterialFinished(topic)
                            viewModel.showInfo("Materi tercatat telah selesai dibaca!")
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Tandai Selesai", fontSize = 12.sp)
                    }

                    // Button Kuis Pokok Materi (Checks permission)
                    Button(
                        onClick = {
                            if (!isQuizUnlocked) {
                                showQuizLockedAlert = true
                            } else {
                                viewModel.navigateTo(Screen.StudentQuiz(topic.id))
                            }
                        },
                        modifier = Modifier
                            .weight(1.2f)
                            .testTag("btn_start_quiz"),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isQuizUnlocked) SchoolNavy else Color.Gray),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            if (isQuizUnlocked) Icons.Default.Quiz else Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isQuizUnlocked) "Mulai Kuis (10 Soal)" else "Kuis Terkunci Guru",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    // Interactive Checkpoint Question Dialog in Video
    if (showInteractiveQuestionDialog) {
        AlertDialog(
            onDismissRequest = { showInteractiveQuestionDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = EcoGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Uji Pemahaman Cepat", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Menurut penjelasan video barusan, apa yang menjadi inti perbedaan biaya peluang dengan biaya eksplisit tunai?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val options = listOf(
                        "Biaya peluang mencakup manfaat alternatif terbaik yang dikorbankan.",
                        "Biaya peluang selalu harus dibayar menggunakan uang tunai rupiah.",
                        "Biaya peluang hanya berlaku bagi pengusaha berskala besar.",
                        "Biaya peluang tidak pernah ada dalam kehidupan sehari-hari."
                    )

                    options.forEachIndexed { idx, opt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedInteractiveOption == idx,
                                onClick = { selectedInteractiveOption = idx }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(opt, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        interactiveAnswerSubmitted = true
                        showInteractiveQuestionDialog = false
                        if (selectedInteractiveOption == 0) {
                            viewModel.showInfo("Jawabanmu Benar! Biaya peluang adalah manfaat alternatif terbaik yang dikorbankan.")
                        } else {
                            viewModel.showInfo("Catatan Guru: Biaya peluang adalah manfaat terbaik berikutnya yang harus dikorbankan.")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy)
                ) {
                    Text("Jawab & Lanjut")
                }
            }
        )
    }

    // Dialog Alert Kuis Terkunci (Ketika Guru Belum Memberi Izin)
    if (showQuizLockedAlert) {
        AlertDialog(
            onDismissRequest = { showQuizLockedAlert = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = DangerRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kuis Belum Diizinkan Guru!", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Kuis 10 soal untuk pokok materi ini sedang dikunci oleh Guru Pengampu Nur Salim, S. Pd.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Silakan ikuti instruksi pembelajaran di kelas dan tunggu hingga Bapak Guru membuka akses kuis dari panel kontrol guru.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showQuizLockedAlert = false }) {
                    Text("Saya Mengerti")
                }
            }
        )
    }
}
