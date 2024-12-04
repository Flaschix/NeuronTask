package com.example.neurontask.domain.repository

import com.example.neurontask.domain.entity.User
import kotlinx.coroutines.flow.StateFlow

interface  UserRepository{

    suspend fun saveUser(user: User) : Result<Unit>
    suspend fun getUser(): StateFlow<User?>
}
