package com.example.neurontask.domain.usecase

import com.example.neurontask.domain.repository.UserRepository
import javax.inject.Inject

class CheckUseStateUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.checkUserState()
    }
}