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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Stars
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.delay

data class GameScenario(
    val title: String,
    val story: String,
    val options: List<String>,
    val correctIndex: Int,
    val feedback: String,
    val pointsReward: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val scenarios = remember {
        listOf(
            GameScenario(
                title = "Misi 1: Kelangkaan & Skala Prioritas",
                story = "Kamu menjadi manajer koperasi siswa SMA N 1 Belitang II dengan sisa kas Rp500.000. Saat ini stok buku tulis habis menjelang ujian, tetapi ada tawaran diskon poster dekoratif kelas. Keputusanmu?",
                options = listOf(
                    "Membeli poster dekoratif kelas agar dinding terlihat meriah",
                    "Membeli stok buku tulis pelajaran untuk kebutuhan ujian siswa",
                    "Menyimpan uang kas tanpa membeli apapun sampai semester depan",
                    "Membagi-bagikan uang kas ke anggota secara merata"
                ),
                correctIndex = 1,
                feedback = "Tepat! Buku tulis merupakan kebutuhan primer penunjang belajar yang mendesak sesuai skala prioritas.",
                pointsReward = 200
            ),
            GameScenario(
                title = "Misi 2: Analisis Biaya Peluang",
                story = "Rizky memiliki waktu luang 4 jam sore ini. Opsi A: Les privat ekonomi (potensi nilai naik 20 poin). Opsi B: Main game online tanpa henti. Jika Rizky memilih B, berapakah biaya peluangnya?",
                options = listOf(
                    "Waktu baterai ponsel yang berkurang",
                    "Kenaikan nilai 20 poin pada pelajaran ekonomi",
                    "Biaya kuota internet untuk game",
                    "Tidak ada biaya peluang sama sekali"
                ),
                correctIndex = 1,
                feedback = "Hebat! Biaya peluang adalah manfaat alternatif terbaik yang hilang (kenaikan 20 poin).",
                pointsReward = 200
            ),
            GameScenario(
                title = "Misi 3: Keseimbangan Pasar Beras",
                story = "Di pasar Belitang, terjadi gagal panen lokal sehingga pasokan beras turun drastis (Shortage). Agar pasar kembali seimbang, apa kecenderungan mekanisme pasar?",
                options = listOf(
                    "Harga beras akan cenderung naik hingga permintaan menyesuaikan pasokan",
                    "Harga beras harus dipaksa turun menjadi Rp1.000/kg",
                    "Pedagang membuang sisa beras yang ada",
                    "Konsumen langsung berhenti makan nasi selamanya"
                ),
                correctIndex = 0,
                feedback = "Benar! Shortage (kelebihan permintaan) mendorong kenaikan harga menuju titik ekuilibrium baru.",
                pointsReward = 250
            ),
            GameScenario(
                title = "Misi 4: Pengendalian Inflasi",
                story = "Terjadi kenaikan harga barang kebutuhan secara umum dan terus menerus (inflasi tinggi). Kebijakan moneter kontraktif apa yang tepat dilakukan Bank Sentral?",
                options = listOf(
                    "Mencetak uang kertas rupiah sebanyak-banyaknya",
                    "Menaikkan tingkat suku bunga acuan (BI-Rate) untuk menarik peredaran uang",
                    "Menurunkan cadangan kas minimum bank komersial",
                    "Mewajibkan warga berbelanja barang mewah"
                ),
                correctIndex = 1,
                feedback = "Luar biasa! Menaikkan suku bunga meredam jumlah uang beredar sehingga inflasi terkendali.",
                pointsReward = 250
            ),
            GameScenario(
                title = "Misi 5: Diagram Arus Melingkar (Circular Flow)",
                story = "Rumah Tangga Konsumen (RTK) menyerahkan tenaga kerja dan tanah ke Rumah Tangga Produsen (RTP). Balas jasa apa yang diterima RTK?",
                options = listOf(
                    "Pajak dan tagihan denda",
                    "Upah (gaji) dan sewa tanah",
                    "Barang modal bekas pabrik",
                    "Surat izin usaha perdagangan"
                ),
                correctIndex = 1,
                feedback = "Tepat sekali! RTK menerima upah (wage) dan sewa (rent) atas faktor produksi yang diserahkan.",
                pointsReward = 300
            )
        )
    }

    var currentRound by remember { mutableIntStateOf(0) }
    var currentScore by remember { mutableIntStateOf(0) }
    var lives by remember { mutableIntStateOf(3) }
    var streak by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var roundTimerSeconds by remember { mutableIntStateOf(20) }
    var roundFeedback by remember { mutableStateOf<String?>(null) }
    var selectedOption by remember { mutableIntStateOf(-1) }

    // Timer per round
    LaunchedEffect(currentRound, isGameOver, roundFeedback) {
        if (!isGameOver && roundFeedback == null) {
            roundTimerSeconds = 20
            while (roundTimerSeconds > 0 && roundFeedback == null) {
                delay(1000)
                roundTimerSeconds--
            }
            if (roundTimerSeconds == 0 && roundFeedback == null) {
                // Time up
                lives--
                streak = 0
                roundFeedback = "Waktu Habis! Waktu berpikir per ronde adalah 20 detik."
                if (lives <= 0) isGameOver = true
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
                            text = "Ekonomi Cerdas - Simulasi Pasar",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "SMA N 1 Belitang II • Guru: Nur Salim, S. Pd",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // HUD Bar: Score, Lives, Combo, Timer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Lives
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(3) { i ->
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Nyawa",
                                tint = if (i < lives) DangerRed else Color.LightGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Score
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Stars, contentDescription = null, tint = EcoGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("$currentScore Poin", fontWeight = FontWeight.Bold, color = SchoolNavy, fontSize = 16.sp)
                    }

                    // Streak
                    if (streak > 1) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(EcoGold)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("${streak}x Combo!", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    // Timer
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = if (roundTimerSeconds <= 5) DangerRed else SchoolTeal, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${roundTimerSeconds}s", fontWeight = FontWeight.Bold, color = if (roundTimerSeconds <= 5) DangerRed else SchoolTeal)
                    }
                }
            }

            if (!isGameOver && currentRound < scenarios.size) {
                val sc = scenarios[currentRound]

                // Round Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Misi ${currentRound + 1} dari ${scenarios.size}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SchoolNavy
                            )
                            Text(
                                text = "+${sc.pointsReward} Poin",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SuccessGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = sc.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = sc.story,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(12.dp))

                        // Decision Options
                        sc.options.forEachIndexed { idx, optText ->
                            val isSel = selectedOption == idx
                            val isCorrect = idx == sc.correctIndex

                            val borderCol = when {
                                roundFeedback != null && isCorrect -> SuccessGreen
                                roundFeedback != null && isSel && !isCorrect -> DangerRed
                                isSel -> SchoolNavy
                                else -> Color(0xFFE2E8F0)
                            }

                            val bgCol = when {
                                roundFeedback != null && isCorrect -> Color(0xFFDCFCE7)
                                roundFeedback != null && isSel && !isCorrect -> Color(0xFFFEE2E2)
                                isSel -> Color(0xFFEFF6FF)
                                else -> Color.White
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.5.dp, borderCol, RoundedCornerShape(10.dp))
                                    .background(bgCol)
                                    .clickable(enabled = roundFeedback == null) {
                                        selectedOption = idx
                                        if (idx == sc.correctIndex) {
                                            val bonus = (streak * 30)
                                            currentScore += sc.pointsReward + bonus
                                            streak++
                                            roundFeedback = "Keputusan Benar! ${sc.feedback}"
                                        } else {
                                            lives--
                                            streak = 0
                                            roundFeedback = "Keputusan Kurang Tepat! ${sc.feedback}"
                                            if (lives <= 0) isGameOver = true
                                        }
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = optText,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        }

                        // Round Feedback Box
                        if (roundFeedback != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedOption == sc.correctIndex) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                                )
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = roundFeedback!!,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            roundFeedback = null
                                            selectedOption = -1
                                            if (currentRound + 1 < scenarios.size && lives > 0) {
                                                currentRound++
                                            } else {
                                                isGameOver = true
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Text("Misi Berikutnya ▶", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // GAME OVER / COMPLETED SUMMARY
                LaunchedEffect(Unit) {
                    viewModel.submitGameScore(currentScore)
                }

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
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = EcoGold,
                            modifier = Modifier.size(60.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (lives > 0) "Misi Simulasi Selesai!" else "Permainan Berakhir",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = SchoolNavy
                        )
                        Text(
                            text = "Nilai tersimpan otomatis ke Panel Guru Nur Salim, S. Pd",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "$currentScore",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = EcoGold
                        )
                        Text(
                            text = "Total Skor Game Pembelajaran",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    currentRound = 0
                                    currentScore = 0
                                    lives = 3
                                    streak = 0
                                    isGameOver = false
                                    roundFeedback = null
                                    selectedOption = -1
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Main Lagi", fontSize = 13.sp)
                            }

                            Button(
                                onClick = { viewModel.navigateTo(Screen.StudentLeaderboard) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Leaderboard, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Leaderboard", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
