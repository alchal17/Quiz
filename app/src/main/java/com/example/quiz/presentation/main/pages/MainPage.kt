package com.example.quiz.presentation.main.pages

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quiz.presentation.main.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainPage(userId: Int, navigateToSignIn: () -> Unit) {
    val context = LocalContext.current

    val mainViewModel = koinViewModel<MainViewModel> { parametersOf(userId) }

    val userSearchResult by mainViewModel.userSearchResult.collectAsStateWithLifecycle()

    LaunchedEffect(userSearchResult) {
        when (val userSearchResultCopy = userSearchResult) {
            is MainViewModel.UserSearchResult.Error -> {
                Toast.makeText(context, userSearchResultCopy.message, Toast.LENGTH_SHORT).show()
                navigateToSignIn()
            }

            MainViewModel.UserSearchResult.InProgress -> {}
            is MainViewModel.UserSearchResult.Success -> {}
        }
    }

    Text(if (userSearchResult is MainViewModel.UserSearchResult.Success) (userSearchResult as MainViewModel.UserSearchResult.Success).user.username else "")
}