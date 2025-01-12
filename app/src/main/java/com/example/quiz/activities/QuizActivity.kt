package com.example.quiz.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.pages.MainPage
import com.example.quiz.ui.pages.NewQuizPage
import com.example.quiz.ui.pages.SettingsPage
import com.example.quiz.ui.routing.QuizRoutes
import com.example.quiz.ui.theme.LapisLazuli
import com.example.quiz.ui.theme.QuizTheme
import com.example.quiz.viewmodels.QuizUserViewModel
import com.example.quiz.viewmodels.QuizViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userID = intent.getIntExtra("user_id", 0)
        setContent {
            val coroutineScope = rememberCoroutineScope()

            val navController = rememberNavController()
            var currentPageIndex by remember { mutableIntStateOf(0) }
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navigationDrawerItems =
                listOf(
                    TabBarItem("Home", selectedIcon = Icons.Default.Home, QuizRoutes.MainPage),
                    TabBarItem(
                        "Settings",
                        selectedIcon = Icons.Default.Settings,
                        QuizRoutes.Settings
                    ),
                    TabBarItem(
                        "New quiz",
                        selectedIcon = Icons.Default.Add,
                        QuizRoutes.NewQuizPage
                    ),
                )

            QuizTheme {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(drawerContainerColor = Color.White.copy(alpha = 0.5f)) {
                            Text("Choose an option")
                            navigationDrawerItems.forEachIndexed { index, navigationDrawerItem ->
                                NavigationDrawerItem(
                                    label = { Text(navigationDrawerItem.title) },
                                    selected = currentPageIndex == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            drawerState.close()
                                            if (currentPageIndex != index) {
                                                currentPageIndex = index
                                                navController.navigate(navigationDrawerItem.destination)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }) {
                    Scaffold(
                        containerColor = LapisLazuli,
                        topBar = {
                            MainTopBar {
                                IconButton({
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }) { Icon(Icons.Default.Menu, null) }
                            }
                        },

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
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val destination: QuizRoutes,
    val badgeAmount: Int? = null
)