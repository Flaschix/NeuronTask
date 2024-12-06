package com.example.neurontask.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neurontask.domain.entity.UserState
import com.example.neurontask.domain.usecase.CheckUseStateUseCase
import com.example.neurontask.domain.usecase.GetUserStateFlowUseCase
import com.example.neurontask.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val getUserStateFlowUseCase: GetUserStateFlowUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<StartScreenState>(StartScreenState.Loading)
    val state: StateFlow<StartScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUserStateFlowUseCase().collect { userState ->
                when (userState) {
                    is UserState.Authorized -> observeUserData()
                    is UserState.NotAuthorized -> _state.value = StartScreenState.NotAuthorized
                    else -> Unit
                }
            }
        }
    }

    private fun observeUserData() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                _state.value = StartScreenState.Authorized(user)
            }
        }
    }
}

