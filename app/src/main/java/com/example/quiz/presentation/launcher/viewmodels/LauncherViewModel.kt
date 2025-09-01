package com.example.quiz.presentation.launcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.sp.InnerStorage
import com.example.quiz.data.sp.SharedPreferencesKeyNames
import com.example.quiz.presentation.launcher.states.LaunchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LauncherViewModel(
    innerStorage: InnerStorage,
) : ViewModel() {

    private val isLoaded = MutableStateFlow(false)

    private val userId = MutableStateFlow<Int?>(null)

    val launchState = combine(isLoaded, userId) { loaded, id ->
        if (loaded) {
            when (id) {
                null -> LaunchState.AccountNotExists
                -1 -> LaunchState.NotSignedIn
                else -> LaunchState.SignedIn(id)
            }
        } else {
            LaunchState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LaunchState.Loading
    )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            //3 scenarios:
                // -user is signed in and account exists and it's id is equal to the value from datastore;
                // -user isn't signed in and it's id is currently -1;
                // -user signed in but the account is no longer exists, so it's value is equal to null;
            val innerStorageValue = innerStorage.getInt(SharedPreferencesKeyNames.USER_ID, -1)
            //userId is equal to -1 when the user isn't sign in
            if (innerStorageValue != -1) {
                //Add api call here to check if user exists by given id
            } else{
                userId.update { -1 }
            }
            isLoaded.update { true }
        }
    }
}