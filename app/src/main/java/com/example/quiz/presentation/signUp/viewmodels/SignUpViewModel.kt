package com.example.quiz.presentation.signUp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.source.local.InnerStorage
import com.example.quiz.domain.repositories.EmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val innerStorage: InnerStorage,
    private val emailRepository: EmailRepository
) : ViewModel() {

    sealed interface AccountPickingResult {
        object InProgress : AccountPickingResult
        data class Error(val message: String) : AccountPickingResult
    }

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow<String?>(null)
    val email = _email.asStateFlow()

    private val _accountPickingResult =
        MutableStateFlow<AccountPickingResult>(AccountPickingResult.InProgress)
    val accountPickingResult = _accountPickingResult.asStateFlow()

    fun setUsername(newUsername: String) {
        _username.update { newUsername }
    }

    fun pickGoogleAccount() {
        viewModelScope.launch {
            emailRepository.signIn(
                coroutineScope = this,
                onSuccess = { newEmail -> _email.update { newEmail } },
                onError = { error ->
                    _accountPickingResult.update {
                        AccountPickingResult.Error(
                            error.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }
}