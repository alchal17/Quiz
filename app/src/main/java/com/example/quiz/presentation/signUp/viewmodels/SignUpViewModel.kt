package com.example.quiz.presentation.signUp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.source.local.InnerStorage
import com.example.quiz.data.source.local.SharedPreferencesKeyNames
import com.example.quiz.domain.DomainResult
import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.repositories.EmailRepository
import com.example.quiz.domain.usecase.quizUsers.CreateQuizUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val innerStorage: InnerStorage,
    private val emailRepository: EmailRepository,
    private val createQuizUserUseCase: CreateQuizUserUseCase
) : ViewModel() {

    sealed interface AccountPickingResult {
        object InProgress : AccountPickingResult
        data class Error(val message: String) : AccountPickingResult
    }

    sealed interface RegistrationResult {
        object NotTriedYet : RegistrationResult
        object Loading : RegistrationResult
        data class Error(val message: String) : RegistrationResult
        data class Success(val userId: Int) : RegistrationResult
    }

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow<String?>(null)
    val email = _email.asStateFlow()

    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl = _photoUrl.asStateFlow()

    private val _accountPickingResult =
        MutableStateFlow<AccountPickingResult>(AccountPickingResult.InProgress)
    val accountPickingResult = _accountPickingResult.asStateFlow()

    private val _registrationResult =
        MutableStateFlow<RegistrationResult>(RegistrationResult.NotTriedYet)
    val registrationResult = _registrationResult.asStateFlow()

    fun setUsername(newUsername: String) {
        _username.update { newUsername }
    }

    fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _registrationResult.update { RegistrationResult.Loading }
            val quizUser = QuizUser(
                id = null,
                username = _username.value,
                email = _email.value ?: run {
                    _registrationResult.update { RegistrationResult.Error("Choose an email") }
                    return@launch
                }
            )
            when (val result = createQuizUserUseCase(quizUser)) {
                is DomainResult.Error -> _registrationResult.update {
                    RegistrationResult.Error(
                        result.message
                    )
                }

                is DomainResult.Success -> {
                    innerStorage.setInt(SharedPreferencesKeyNames.USER_ID, result.data)
                    _registrationResult.update {
                        RegistrationResult.Success(
                            result.data
                        )
                    }
                }            }
        }
    }

    fun pickGoogleAccount() {
        viewModelScope.launch {
            emailRepository.signIn(
                coroutineScope = this,
                onSuccess = { newEmail, newProfilePhoto ->
                    _email.update { newEmail }
                    _photoUrl.update { newProfilePhoto }
                },
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