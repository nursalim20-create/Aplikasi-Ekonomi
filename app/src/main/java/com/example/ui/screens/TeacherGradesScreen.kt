package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.data.model.MaterialTopic
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherGradesScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val gradeRows by viewModel.filteredGradeRows.collectAsStateWithLifecycle()
    val allTopics by viewModel.allTopics.collectAsStateWithLifecycle()
    val searchQuery by viewModel.gradeSearchQuery.collectAsStateWithLifecycle()
    val selectedClass by viewModel.gradeSelectedClass.collectAsStateWithLifecycle()

    val classList = listOf("Semua", "X.1", "X.2", "X.3", "X.4", "X.5", "X.6", "X.7")
    val sortedTopics = remember(allTopics) { allTopics.sortedBy { it.id } }

    val horizontalScrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Rekapitulasi Nilai Siswa",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "SMA N 1 Belitang II • Pengampu: Nur Salim, S. Pd",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFDE68A))
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.navigateTo(Screen.TeacherDashboard) }) {
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
            // Export Buttons Row (Excel & PDF)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.exportExcelCsv() },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_export_excel")
                ) {
                    Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Download Excel (.csv)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Button(
                    onClick = { viewModel.exportPdf() },
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_export_pdf")
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cetak / Simpan PDF", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setGradeSearchQuery(it) },
                label = { Text("Cari nama siswa atau no. absen...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setGradeSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Hapus")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_search_grades"),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Class Filter Chips (X.1 sampai X.7)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                classList.forEach { c ->
                    val isSelected = selectedClass.equals(c, ignoreCase = true)
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setGradeSelectedClass(c) },
                        label = { Text(if (c == "Semua") "Semua Kelas" else "Kelas $c") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SchoolNavy,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_class_$c")
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Summary stats banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Menampilkan ${gradeRows.size} Siswa (${if (selectedClass == "Semua") "Seluruh Kelas X" else "Kelas $selectedClass"})",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "*Geser tabel ke samping ▶",
                    style = MaterialTheme.typography.labelSmall,
                    color = SchoolNavy
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal Scrollable Grades Table (CRITICAL USER REQUIREMENT)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(horizontalScrollState)
                    ) {
                        // Table Header Row
                        Row(
                            modifier = Modifier
                                .background(SchoolNavy)
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TableHeaderCell("No", 36.dp)
                            TableHeaderCell("Nama Siswa", 160.dp)
                            TableHeaderCell("Kelas", 60.dp)
                            TableHeaderCell("Absen", 50.dp)
                            sortedTopics.forEachIndexed { idx, t ->
                                TableHeaderCell("Kuis ${idx + 1}\n(B${t.chapterNumber}.${t.topicNumber})", 68.dp)
                            }
                            TableHeaderCell("Nilai\nGame", 65.dp)
                            TableHeaderCell("Rata²\nKuis", 65.dp)
                            TableHeaderCell("Status\nKKM (75)", 80.dp)
                        }

                        // Table Body Rows
                        if (gradeRows.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Tidak ada data nilai siswa untuk filter pencarian ini.")
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                itemsIndexed(gradeRows, key = { _, r -> r.student.id }) { index, row ->
                                    val bg = if (index % 2 == 1) Color(0xFFF8FAFC) else Color.White
                                    Row(
                                        modifier = Modifier
                                            .background(bg)
                                            .border(0.5.dp, Color(0xFFE2E8F0))
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TableCell("${index + 1}", 36.dp)
                                        TableCell(row.student.name, 160.dp, isBold = true, alignLeft = true)
                                        TableCell(row.student.className, 60.dp)
                                        TableCell("${row.student.studentNumber}", 50.dp)

                                        sortedTopics.forEach { t ->
                                            val score = row.quizScores[t.id]
                                            TableScoreCell(score, 68.dp)
                                        }

                                        // Game score
                                        TableCell(
                                            text = if (row.highestGameScore > 0) "${row.highestGameScore}" else "-",
                                            width = 65.dp,
                                            isBold = true,
                                            textColor = if (row.highestGameScore > 0) SchoolTeal else Color.Gray
                                        )

                                        // Average
                                        TableCell(
                                            text = if (row.averageScore > 0) String.format(Locale.US, "%.1f", row.averageScore) else "-",
                                            width = 65.dp,
                                            isBold = true,
                                            textColor = if (row.averageScore >= 75.0) SuccessGreen else if (row.averageScore > 0) EcoGold else Color.Gray
                                        )

                                        // Status KKM
                                        Box(
                                            modifier = Modifier
                                                .width(80.dp)
                                                .padding(horizontal = 4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            val isPassed = row.averageScore >= 75.0
                                            val hasTaken = row.quizScores.isNotEmpty()
                                            val statusText = if (!hasTaken) "BELUM" else if (isPassed) "TUNTAS" else "REMEDIAL"
                                            val statusColor = if (!hasTaken) Color.Gray else if (isPassed) SuccessGreen else DangerRed
                                            val statusBg = if (!hasTaken) Color(0xFFF1F5F9) else if (isPassed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)

                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(statusBg)
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = statusText,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = statusColor,
                                                        fontSize = 10.sp
                                                    )
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
        }
    }
}

@Composable
fun TableHeaderCell(text: String, width: androidx.compose.ui.unit.Dp) {
    Text(
        text = text,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 4.dp),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun TableCell(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    isBold: Boolean = false,
    alignLeft: Boolean = false,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        modifier = Modifier
            .width(width)
            .padding(horizontal = 6.dp),
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = if (isBold) FontWeight.SemiBold else FontWeight.Normal,
            color = textColor,
            textAlign = if (alignLeft) TextAlign.Start else TextAlign.Center
        ),
        maxLines = 1
    )
}

@Composable
fun TableScoreCell(score: Int?, width: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (score == null) {
            Text("-", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
        } else {
            val isGood = score >= 75
            Text(
                text = "$score",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isGood) SuccessGreen else DangerRed
                )
            )
        }
    }
}
