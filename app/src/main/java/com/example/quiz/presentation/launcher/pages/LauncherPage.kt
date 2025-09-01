package com.example.quiz.presentation.launcher.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quiz.presentation.launcher.states.LaunchState
import com.example.quiz.presentation.launcher.viewmodels.LauncherViewModel
import com.example.quiz.presentation.uiUtils.routes.LauncherRoutes
import com.example.quiz.ui.theme.MainColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun LauncherPage() {

    val context = LocalContext.current

    val navController = rememberNavController()

    val launcherViewModel = koinViewModel<LauncherViewModel>()

    val launchState by launcherViewModel.launchState.collectAsStateWithLifecycle()

    LaunchedEffect(launchState) {
        val currentState = launchState

        when (currentState) {
            is LaunchState.Loading -> {
                // Do nothing, stay on loading screen
            }

            is LaunchState.AccountNotExists -> {
                Toast.makeText(context, "Account no longer exists.", Toast.LENGTH_SHORT).show()
                navController.navigate(LauncherRoutes.Auth) {
                    popUpTo(LauncherRoutes.Launcher) { inclusive = true }
                }
            }

            is LaunchState.NotSignedIn -> {
                navController.navigate(LauncherRoutes.Auth) {
                    popUpTo(LauncherRoutes.Launcher) { inclusive = true }
                }
            }

            is LaunchState.SignedIn -> {
                navController.navigate(LauncherRoutes.Quiz(currentState.userId)) {
                    popUpTo(LauncherRoutes.Launcher) { inclusive = true }
                }
            }
        }
    }

    Scaffold(containerColor = MainColor) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            startDestination = LauncherRoutes.Launcher,
            navController = navController
        ) {
            composable<LauncherRoutes.Launcher> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            composable<LauncherRoutes.Auth> {
                Text("Auth")
            }
            composable<LauncherRoutes.Quiz> {
                Text("Quiz")
            }
        }
    }
}