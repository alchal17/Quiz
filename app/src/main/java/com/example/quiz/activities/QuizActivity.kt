package com.example.quiz.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.navigation.toRoute
import com.example.quiz.ui.elements.Base64QuizQuestionManaging
import com.example.quiz.ui.elements.MainTopBar
import com.example.quiz.ui.pages.MainPage
import com.example.quiz.ui.pages.ManageQuizPage
import com.example.quiz.ui.pages.SettingsPage
import com.example.quiz.ui.pages.UserQuizzesPage
import com.example.quiz.ui.routing.QuizRoutes
import com.example.quiz.ui.theme.MainColor
import com.example.quiz.ui.theme.QuizTheme
import kotlinx.coroutines.launch

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userID = intent.getIntExtra("user_id", 0)
        enableEdgeToEdge()
        setContent {
            val coroutineScope = rememberCoroutineScope()

            val navController = rememberNavController()
            var currentPageIndex by remember { mutableIntStateOf(0) }
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navigationDrawerItems =
                listOf(
                    TabBarItem(
                        "Home",
                        selectedIcon = Icons.Default.Home,
                        QuizRoutes.MainPage
                    ),
                    TabBarItem(
                        "Settings",
                        selectedIcon = Icons.Default.Settings,
                        QuizRoutes.Settings
                    ),
                    TabBarItem(
                        title = "Your quizzes",
                        selectedIcon = Icons.Default.Person,
                        destination = QuizRoutes.UserQuizzesPage,
                    )
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
                        containerColor = MainColor,
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
                                MainPage(userID)
                            }

                            composable<QuizRoutes.ManageQuiz.QuizMainInfoPage> {
                                val args = it.toRoute<QuizRoutes.ManageQuiz.QuizMainInfoPage>()
                                ManageQuizPage(
                                    navController = navController,
                                    userId = userID,
                                    initialQuizId = args.base64QuizId,
                                )
                            }

                            composable<QuizRoutes.ManageQuiz.QuestionInfoPage> {
                                val args = it.toRoute<QuizRoutes.ManageQuiz.QuestionInfoPage>()
                                Base64QuizQuestionManaging(
                                    quizId = args.quizId,
                                    initialQuestionId = args.base64QuestionId
                                )
                            }

                            composable<QuizRoutes.Settings> { SettingsPage() }

                            composable<QuizRoutes.UserQuizzesPage> {
                                UserQuizzesPage(
                                    userID,
                                    navController
                                )
                            }
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