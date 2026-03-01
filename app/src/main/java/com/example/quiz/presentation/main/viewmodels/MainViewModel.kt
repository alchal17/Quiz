package com.example.quiz.presentation.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz.data.source.local.InnerStorage
import com.example.quiz.data.source.local.SharedPreferencesKeyNames
import com.example.common.domain.DomainResult
import com.example.common.domain.models.QuizUser
import com.example.auth.domain.useCase.quizUsers.GetQuizUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    innerStorage: InnerStorage,
    userId: Int,
    getQuizUserByIdUseCase: GetQuizUserByIdUseCase
) : ViewModel() {
    sealed interface UserSearchResult {
        object InProgress : UserSearchResult
        data class Success(val user: QuizUser) : UserSearchResult
        data class Error(val message: String) : UserSearchResult
    }

    private val _userSearchResult = MutableStateFlow<UserSearchResult>(UserSearchResult.InProgress)
    val userSearchResult = _userSearchResult.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getQuizUserByIdUseCase(userId)) {
                is DomainResult.Error -> {
                    innerStorage.setInt(SharedPreferencesKeyNames.USER_ID, -1)
                    _userSearchResult.update { UserSearchResult.Error(result.message) }
                }

                is DomainResult.Success -> _userSearchResult.update {
                    UserSearchResult.Success(
                        result.data
                    )
                }
            }
        }
    }
}