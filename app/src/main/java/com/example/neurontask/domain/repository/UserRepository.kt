package com.example.neurontask.domain.repository

import com.example.neurontask.domain.entity.User
import com.example.neurontask.domain.entity.UserState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface  UserRepository{

    suspend fun saveUser(user: User) : Result<Unit>
    suspend fun getUser(): SharedFlow<User>
    suspend fun getUserStateFlow(): StateFlow<UserState>
    suspend fun checkUserState(): Result<Unit>
}
