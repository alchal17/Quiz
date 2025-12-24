package com.example.quiz.presentation.signIn.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.source.local.InnerStorage
import com.example.quiz.data.source.local.SharedPreferencesKeyNames
import com.example.quiz.domain.DomainResult
import com.example.quiz.domain.models.QuizUser
import com.example.quiz.domain.repositories.EmailRepository
import com.example.quiz.domain.usecase.quizUsers.GetQuizUserByEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface SignInResult {
    object NotPicked : SignInResult
    object Loading : SignInResult
    data class UserFound(val userId: Int) : SignInResult
    data class Error(val message: String) : SignInResult
}

class SignInViewModel(
    private val innerStorage: InnerStorage,
    private val getQuizUserByEmailUseCase: GetQuizUserByEmailUseCase,
    private val emailRepository: EmailRepository,
) : ViewModel() {

    private val _signInResult = MutableStateFlow<SignInResult>(SignInResult.NotPicked)
    val signInResult = _signInResult.asStateFlow()

    private suspend fun findQuizUserByEmail(email: String): QuizUser? =
        withContext(Dispatchers.IO) {
            return@withContext when (val searchingResult = getQuizUserByEmailUseCase(email)) {
                is DomainResult.Error -> null
                is DomainResult.Success -> {
                    searchingResult.data
                }
            }
        }

    fun pickAccount() {
        viewModelScope.launch {
            _signInResult.update { SignInResult.Loading }
            emailRepository.signIn(this, { email ->
                val user = findQuizUserByEmail(email)
                if (user == null) {
                    _signInResult.update { SignInResult.Error("No such user with email $email") }
                } else if (user.id == null) {
                    _signInResult.update { SignInResult.Error("User has no id") }
                } else {
                    innerStorage.setInt(
                        SharedPreferencesKeyNames.USER_ID,
                        user.id
                    )
                    _signInResult.update { SignInResult.UserFound(user.id) }
                }
            }, { error ->
                _signInResult.update {
                    SignInResult.Error(
                        error.message ?: "Unknown error occurred."
                    )
                }
            })
        }
    }
}