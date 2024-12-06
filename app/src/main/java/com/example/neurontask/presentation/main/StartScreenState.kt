package com.example.neurontask.presentation.main

import com.example.neurontask.domain.entity.User

sealed class StartScreenState {
    data object Loading : StartScreenState()
    data class Authorized(val user: User) : StartScreenState()
    data object NotAuthorized : StartScreenState()
}