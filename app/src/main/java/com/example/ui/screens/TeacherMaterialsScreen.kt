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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.ui.theme.DangerRed
import com.example.ui.theme.EcoGold
import com.example.ui.theme.SchoolNavy
import com.example.ui.theme.SchoolTeal
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherMaterialsScreen(
    viewModel: EconomicsViewModel,
    modifier: Modifier = Modifier
) {
    val allTopics by viewModel.allTopics.collectAsStateWithLifecycle()
    var selectedChapterTab by remember { mutableIntStateOf(0) } // 0: Semua, 1: Bab 1, 2: Bab 2, 3: Bab 3, 4: Bab 4

    // Dialog state for adding/editing material
    var showMaterialDialog by remember { mutableStateOf(false) }
    var editingTopic by remember { mutableStateOf<MaterialTopic?>(null) }

    // Dialog state for managing quiz questions
    var managingQuizTopic by remember { mutableStateOf<MaterialTopic?>(null) }

    // Dialog state for deleting confirmation
    var deletingTopic by remember { mutableStateOf<MaterialTopic?>(null) }

    val filteredTopics = remember(allTopics, selectedChapterTab) {
        if (selectedChapterTab == 0) allTopics
        else allTopics.filter { it.chapterNumber == selectedChapterTab }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kelola Materi & Video Ajar",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Kurikulum Ekonomi Kelas X - SMA N 1 Belitang II",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingTopic = null
                    showMaterialDialog = true
                },
                containerColor = SchoolNavy,
                contentColor = Color.White,
                modifier = Modifier.testTag("fab_add_material")
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tambah Materi")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Chapter Tabs
            val tabs = listOf("Semua Bab", "Bab 1: Konsep Dasar", "Bab 2: Masalah Ekonomi", "Bab 3: Pelaku & Kegiatan", "Bab 4: Pasar & Harga")
            ScrollableTabRow(
                selectedTabIndex = selectedChapterTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        selected = selectedChapterTab == index,
                        onClick = { selectedChapterTab = index },
                        text = { Text(tabTitle, fontWeight = if (selectedChapterTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Daftar Materi Pokok (${filteredTopics.size} Pokok Bahasan)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(filteredTopics, key = { it.id }) { topic ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Badge & Title
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SchoolNavy.copy(alpha = 0.1f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "Bab ${topic.chapterNumber}.${topic.topicNumber}: ${topic.chapterTitle}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SchoolNavy,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                Row {
                                    IconButton(
                                        onClick = {
                                            editingTopic = topic
                                            showMaterialDialog = true
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = SchoolNavy, modifier = Modifier.size(18.dp))
                                    }
                                    IconButton(
                                        onClick = { deletingTopic = topic },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = DangerRed, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = topic.topicTitle,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = topic.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(10.dp))

                            // Video Info
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PlayCircle, contentDescription = null, tint = EcoGold, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Video: ${topic.videoTitle}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = "Durasi: ${topic.videoDuration}  •  ${topic.videoUrl}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Actions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { managingQuizTopic = topic },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Kelola Kuis (10 Soal)", fontSize = 12.sp)
                                }
                                Button(
                                    onClick = {
                                        editingTopic = topic
                                        showMaterialDialog = true
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Edit Materi", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
        }
    }

    // Add / Edit Material Dialog
    if (showMaterialDialog) {
        AddEditMaterialDialog(
            topic = editingTopic,
            onDismiss = { showMaterialDialog = false },
            onSave = { chNum, chTitle, tNum, tTitle, summary, content, vTitle, vUrl, vDur, takeaways ->
                viewModel.saveTopic(
                    id = editingTopic?.id ?: 0,
                    chapterNumber = chNum,
                    chapterTitle = chTitle,
                    topicNumber = tNum,
                    topicTitle = tTitle,
                    summary = summary,
                    content = content,
                    videoTitle = vTitle,
                    videoUrl = vUrl,
                    videoDuration = vDur,
                    keyTakeaways = takeaways
                )
                showMaterialDialog = false
            }
        )
    }

    // Manage Quiz Questions Dialog
    managingQuizTopic?.let { topic ->
        ManageQuizDialog(
            viewModel = viewModel,
            topic = topic,
            onDismiss = { managingQuizTopic = null }
        )
    }

    // Delete Confirmation Dialog
    deletingTopic?.let { topic ->
        AlertDialog(
            onDismissRequest = { deletingTopic = null },
            title = { Text("Hapus Materi?") },
            text = { Text("Apakah Anda yakin ingin menghapus materi '${topic.topicTitle}' beserta soal kuis di dalamnya?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteTopic(topic)
                        deletingTopic = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingTopic = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMaterialDialog(
    topic: MaterialTopic?,
    onDismiss: () -> Unit,
    onSave: (Int, String, Int, String, String, String, String, String, String, String) -> Unit
) {
    var chapterNumber by remember { mutableIntStateOf(topic?.chapterNumber ?: 1) }
    var chapterTitle by remember { mutableStateOf(topic?.chapterTitle ?: "Konsep Dasar Ilmu Ekonomi") }
    var topicNumber by remember { mutableIntStateOf(topic?.topicNumber ?: 1) }
    var topicTitle by remember { mutableStateOf(topic?.topicTitle ?: "") }
    var summary by remember { mutableStateOf(topic?.summary ?: "") }
    var content by remember { mutableStateOf(topic?.content ?: "") }
    var videoTitle by remember { mutableStateOf(topic?.videoTitle ?: "") }
    var videoUrl by remember { mutableStateOf(topic?.videoUrl ?: "https://www.youtube.com/") }
    var videoDuration by remember { mutableStateOf(topic?.videoDuration ?: "12 Menit") }
    var keyTakeaways by remember { mutableStateOf(topic?.keyTakeaways ?: "") }

    val chapterOptions = listOf(
        1 to "Konsep Dasar Ilmu Ekonomi",
        2 to "Masalah Ekonomi & Sistem Ekonomi",
        3 to "Pelaku Ekonomi & Kegiatan Ekonomi",
        4 to "Pasar & Terbentuknya Harga Pasar"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (topic == null) "Tambah Materi Pokok Baru" else "Edit Materi Pokok",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Bab Selector
                Text("Pilih Bab (1 - 4):", style = MaterialTheme.typography.labelMedium)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    chapterOptions.forEach { (num, title) ->
                        OutlinedButton(
                            onClick = {
                                chapterNumber = num
                                chapterTitle = title
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (chapterNumber == num) SchoolNavy.copy(alpha = 0.15f) else Color.Transparent
                            )
                        ) {
                            Text("Bab $num", fontSize = 11.sp, fontWeight = if (chapterNumber == num) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }

                Text("Judul Bab: $chapterTitle", style = MaterialTheme.typography.bodySmall, color = SchoolNavy, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = topicTitle,
                    onValueChange = { topicTitle = it },
                    label = { Text("Judul Pokok Materi") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("Ringkasan Singkat") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Isi Penjelasan Lengkap Materi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    maxLines = 10
                )

                HorizontalDivider()
                Text("Video Pembelajaran Interaktif:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = videoTitle,
                    onValueChange = { videoTitle = it },
                    label = { Text("Judul Video") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = videoUrl,
                    onValueChange = { videoUrl = it },
                    label = { Text("URL Link Video (YouTube / Modul)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = videoDuration,
                    onValueChange = { videoDuration = it },
                    label = { Text("Estimasi Durasi Video (misal: 12 Menit)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = keyTakeaways,
                    onValueChange = { keyTakeaways = it },
                    label = { Text("Poin Kunci / Rangkuman") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (topicTitle.isNotBlank()) {
                        onSave(chapterNumber, chapterTitle, topicNumber, topicTitle, summary, content, videoTitle, videoUrl, videoDuration, keyTakeaways)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy)
            ) {
                Text("Simpan Materi")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
fun ManageQuizDialog(
    viewModel: EconomicsViewModel,
    topic: MaterialTopic,
    onDismiss: () -> Unit
) {
    val questions: List<QuizQuestion> by viewModel.getTopicQuestions(topic.id).collectAsStateWithLifecycle(initialValue = emptyList())
    var editingQuestion by remember { mutableStateOf<QuizQuestion?>(null) }
    var isAddingNew by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("Kelola Kuis Pokok Materi", fontWeight = FontWeight.Bold)
                Text(
                    text = "${topic.topicTitle} (${questions.size} Soal Pilihan Ganda)",
                    style = MaterialTheme.typography.bodySmall,
                    color = SchoolNavy
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Soal: ${questions.size}/10", fontWeight = FontWeight.SemiBold)
                    Button(
                        onClick = { isAddingNew = true },
                        colors = ButtonDefaults.buttonColors(containerColor = SchoolNavy),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tambah Soal", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(questions.sortedBy { it.id }) { q ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Soal:",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = SchoolNavy
                                    )
                                    Row {
                                        IconButton(onClick = { editingQuestion = q }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                        }
                                        IconButton(onClick = { viewModel.deleteQuestion(q) }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Delete, contentDescription = null, tint = DangerRed, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                                Text(text = q.questionText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Kunci: ${listOf("A", "B", "C", "D")[q.correctOption]} - ${listOf(q.optionA, q.optionB, q.optionC, q.optionD)[q.correctOption]}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SchoolTeal,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Selesai")
            }
        }
    )

    // Sub-dialog for adding/editing a question
    if (isAddingNew || editingQuestion != null) {
        val target = editingQuestion
        var qText by remember { mutableStateOf(target?.questionText ?: "") }
        var optA by remember { mutableStateOf(target?.optionA ?: "") }
        var optB by remember { mutableStateOf(target?.optionB ?: "") }
        var optC by remember { mutableStateOf(target?.optionC ?: "") }
        var optD by remember { mutableStateOf(target?.optionD ?: "") }
        var correctIndex by remember { mutableIntStateOf(target?.correctOption ?: 0) }
        var explanation by remember { mutableStateOf(target?.explanation ?: "") }

        AlertDialog(
            onDismissRequest = {
                isAddingNew = false
                editingQuestion = null
            },
            title = { Text(if (target == null) "Tambah Soal Kuis Baru" else "Edit Soal Kuis") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = qText,
                        onValueChange = { qText = it },
                        label = { Text("Teks Pertanyaan") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = optA,
                        onValueChange = { optA = it },
                        label = { Text("Opsi A") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = optB,
                        onValueChange = { optB = it },
                        label = { Text("Opsi B") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = optC,
                        onValueChange = { optC = it },
                        label = { Text("Opsi C") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = optD,
                        onValueChange = { optD = it },
                        label = { Text("Opsi D") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Kunci Jawaban Benar:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("A", "B", "C", "D").forEachIndexed { idx, label ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = correctIndex == idx, onClick = { correctIndex = idx })
                                Text(label)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = explanation,
                        onValueChange = { explanation = it },
                        label = { Text("Pembahasan Ilmiah Soal") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (qText.isNotBlank() && optA.isNotBlank() && optB.isNotBlank()) {
                            viewModel.saveQuestion(
                                QuizQuestion(
                                    id = target?.id ?: 0,
                                    topicId = topic.id,
                                    questionText = qText,
                                    optionA = optA,
                                    optionB = optB,
                                    optionC = optC,
                                    optionD = optD,
                                    correctOption = correctIndex,
                                    explanation = explanation
                                )
                            )
                            isAddingNew = false
                            editingQuestion = null
                        }
                    }
                ) {
                    Text("Simpan Soal")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAddingNew = false
                    editingQuestion = null
                }) {
                    Text("Batal")
                }
            }
        )
    }
}
