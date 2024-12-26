package com.example.quiz.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quiz.ui.elements.MainBottomBar
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.pages.MainPage
import com.example.quiz.ui.pages.NewQuizPage
import com.example.quiz.ui.pages.SettingsPage
import com.example.quiz.ui.routing.QuizRoutes
import com.example.quiz.ui.theme.LapisLazuli
import com.example.quiz.ui.theme.QuizTheme
import com.example.quiz.viewmodels.QuizUserViewModel
import com.example.quiz.viewmodels.QuizViewModel
import org.koin.androidx.compose.koinViewModel

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userID = intent.getIntExtra("user_id", 0)
        setContent {

            val navController = rememberNavController()
            var selectedBottomBarIndex by rememberSaveable { mutableIntStateOf(0) }

            QuizTheme {
                Scaffold(
                    containerColor = LapisLazuli,
                    topBar = { MainTopBar() },
                    bottomBar = {
                        MainBottomBar(
                            options = listOf(TabBarItem(
                                title = "Home",
                                selectedIcon = Icons.Filled.Home,
                                unselectedIcon = Icons.Outlined.Home,
                                badgeAmount = null,
                                onClick = {
                                    navController.navigate(QuizRoutes.MainPage)
                                    selectedBottomBarIndex = it
                                }
                            ), TabBarItem(
                                title = "New quiz",
                                selectedIcon = Icons.Filled.Add,
                                unselectedIcon = Icons.Outlined.Add,
                                badgeAmount = null,
                                onClick = {
                                    navController.navigate(QuizRoutes.NewQuizPage)
                                    selectedBottomBarIndex = it
                                }
                            ),
                                TabBarItem(
                                    title = "Setting",
                                    selectedIcon = Icons.Filled.Settings,
                                    unselectedIcon = Icons.Outlined.Settings,
                                    badgeAmount = null,
                                    onClick = {
                                        navController.navigate(QuizRoutes.Settings)
                                        selectedBottomBarIndex = it
                                    }
                                )),
                            selectedIndex = selectedBottomBarIndex
                        )
                    }
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = QuizRoutes.MainPage
                    ) {
                        composable<QuizRoutes.MainPage> {
                            val quizUserViewModel = koinViewModel<QuizUserViewModel>()
                            MainPage(userID, quizUserViewModel)
                        }
                        composable<QuizRoutes.NewQuizPage> {
                            val quizViewModel = koinViewModel<QuizViewModel>()
                            NewQuizPage(userID, quizViewModel)
                        }
                        composable<QuizRoutes.Settings> { SettingsPage() }
                    }
                }
            }
        }
    }
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null,
    val onClick: (Int) -> Unit
)