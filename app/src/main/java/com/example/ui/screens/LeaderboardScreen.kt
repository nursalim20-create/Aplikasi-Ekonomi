package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val leaderboard by viewModel.leaderboard.collectAsStateWithLifecycle()
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()
    var selectedClassFilter by remember { mutableStateOf("Semua") }

    val classOptions = listOf("Semua", "X.1", "X.2", "X.3", "X.4", "X.5", "X.6", "X.7")

    val filteredList = remember(leaderboard, selectedClassFilter) {
        if (selectedClassFilter == "Semua") leaderboard
        else leaderboard.filter { it.studentClass == selectedClassFilter }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Papan Peringkat Siswa",
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
                    IconButton(
                        onClick = {
                            if (userRole == UserRole.TEACHER) viewModel.navigateTo(Screen.TeacherDashboard)
                            else viewModel.navigateTo(Screen.StudentHome)
                        }
                    ) {
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
                .padding(14.dp)
        ) {
            // Filter Kelas X.1 - X.7
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                classOptions.forEach { c ->
                    val isSel = selectedClassFilter == c
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedClassFilter = c },
                        label = { Text(if (c == "Semua") "Semua Kelas X" else "Kelas $c") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SchoolNavy,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("filter_leaderboard_$c")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Top 3 Podium
            if (filteredList.isNotEmpty()) {
                val top1 = filteredList.getOrNull(0)
                val top2 = filteredList.getOrNull(1)
                val top3 = filteredList.getOrNull(2)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = EcoGold, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Bintang Prestasi Ekonomi Kelas X",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = SchoolNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            // 2nd Place
                            if (top2 != null) {
                                PodiumItem(rank = 2, name = top2.studentName, className = top2.studentClass, score = top2.combinedScore, color = Color(0xFF94A3B8), height = 75.dp)
                            } else {
                                Spacer(modifier = Modifier.width(70.dp))
                            }

                            // 1st Place
                            if (top1 != null) {
                                PodiumItem(rank = 1, name = top1.studentName, className = top1.studentClass, score = top1.combinedScore, color = EcoGold, height = 95.dp)
                            }

                            // 3rd Place
                            if (top3 != null) {
                                PodiumItem(rank = 3, name = top3.studentName, className = top3.studentClass, score = top3.combinedScore, color = Color(0xFFD97706).copy(alpha = 0.7f), height = 65.dp)
                            } else {
                                Spacer(modifier = Modifier.width(70.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // Rankings List
            Text(
                text = "Peringkat Akumulasi Nilai Kuis & Game:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada skor yang masuk untuk kelas ini.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(filteredList) { index, entry ->
                        val rank = index + 1
                        val medalColor = when (rank) {
                            1 -> EcoGold
                            2 -> Color(0xFF94A3B8)
                            3 -> Color(0xFFB45309)
                            else -> Color(0xFFCBD5E1)
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
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
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(medalColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$rank",
                                        fontWeight = FontWeight.Bold,
                                        color = if (rank <= 3) Color.White else Color.Black,
                                        fontSize = 13.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = entry.studentName,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Kelas ${entry.studentClass}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Kuis: ${entry.totalQuizScore} pt  •  Game: ${entry.bestGameScore} pt",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = SchoolTeal
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "${entry.combinedScore}",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = SchoolNavy
                                    )
                                    Text(
                                        text = "Total Skor",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PodiumItem(rank: Int, name: String, className: String, score: Int, color: Color, height: androidx.compose.ui.unit.Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(90.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text("$rank", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = className,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(75.dp)
                .height(height)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$score",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.Black
                )
                Text(
                    text = "Poin",
                    fontSize = 10.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
