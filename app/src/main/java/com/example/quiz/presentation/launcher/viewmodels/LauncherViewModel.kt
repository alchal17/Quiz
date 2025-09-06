package com.example.quiz.presentation.launcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.sp.InnerStorage
import com.example.quiz.data.sp.SharedPreferencesKeyNames
import com.example.quiz.domain.repositories.quizUserRepository.QuizUserRepository
import com.example.quiz.presentation.launcher.states.LaunchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LauncherViewModel(
    innerStorage: InnerStorage,
    quizUserRepository: QuizUserRepository
) : ViewModel() {

    private val _currentLoadingState = MutableStateFlow<LaunchState>(LaunchState.Loading)
    val currentState = _currentLoadingState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            //3 scenarios:
            // -user is signed in and account exists and it's id is equal to the value from datastore;
            // -user isn't signed in and it's id is currently -1;
            // -user signed in but the account is no longer exists, so it's value is equal to null;
            val innerStorageUserId = innerStorage.getInt(SharedPreferencesKeyNames.USER_ID, -1)
            //userId is equal to -1 when the user isn't sign in
            if (innerStorageUserId != -1) {
                //Add api call here to check if user exists by given id
                val user = quizUserRepository.getById(innerStorageUserId).getOrNull()

                if (user == null) {
                    _currentLoadingState.update { LaunchState.NotSignedIn }
                } else {
                    user.id?.let { userId ->
                        _currentLoadingState.update { LaunchState.SignedIn(userId) }
                    }
                }
            } else {
                _currentLoadingState.update { LaunchState.NotSignedIn }
            }
        }
    }
}