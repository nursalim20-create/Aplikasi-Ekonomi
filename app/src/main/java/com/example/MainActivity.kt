package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.StudentHomeScreen
import com.example.ui.screens.StudentMaterialDetailScreen
import com.example.ui.screens.TeacherDashboardScreen
import com.example.ui.screens.TeacherGradesScreen
import com.example.ui.screens.TeacherMaterialsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.EconomicsViewModel
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.UserRole

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: EconomicsViewModel = viewModel()
                EconomicsApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun EconomicsApp(viewModel: EconomicsViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val userRole by viewModel.userRole.collectAsStateWithLifecycle()
    val infoMessage by viewModel.infoMessage.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(infoMessage) {
        infoMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissInfoMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when (val screen = currentScreen) {
            is Screen.Auth -> {
                AuthScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.StudentHome -> {
                BackHandler {
                    viewModel.logout()
                }
                StudentHomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.StudentMaterialDetail -> {
                BackHandler {
                    viewModel.navigateTo(Screen.StudentHome)
                }
                StudentMaterialDetailScreen(
                    viewModel = viewModel,
                    topicId = screen.topicId,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.StudentQuiz -> {
                BackHandler {
                    viewModel.navigateTo(Screen.StudentMaterialDetail(screen.topicId))
                }
                QuizScreen(
                    viewModel = viewModel,
                    topicId = screen.topicId,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.StudentGame -> {
                BackHandler {
                    viewModel.navigateTo(Screen.StudentHome)
                }
                GameScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.StudentLeaderboard -> {
                BackHandler {
                    if (userRole == UserRole.TEACHER) {
                        viewModel.navigateTo(Screen.TeacherDashboard)
                    } else {
                        viewModel.navigateTo(Screen.StudentHome)
                    }
                }
                LeaderboardScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.TeacherDashboard -> {
                BackHandler {
                    viewModel.logout()
                }
                TeacherDashboardScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.TeacherMaterials -> {
                BackHandler {
                    viewModel.navigateTo(Screen.TeacherDashboard)
                }
                TeacherMaterialsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is Screen.TeacherGrades -> {
                BackHandler {
                    viewModel.navigateTo(Screen.TeacherDashboard)
                }
                TeacherGradesScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
