package com.example.neurontask.domain.repository

import com.example.neurontask.domain.entity.User

interface  UserRepository{

    suspend fun saveUser(user: User) : Result<Unit>
    suspend fun getUser(): User?
}
