package com.example.neurontask.domain.usecase

import com.example.neurontask.domain.entity.User
import com.example.neurontask.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val repository: UserRepository)  {
    suspend operator fun invoke(user: User){
        repository.saveUser(user)
    }
}