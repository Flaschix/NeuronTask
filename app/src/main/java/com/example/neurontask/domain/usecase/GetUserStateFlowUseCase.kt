package com.example.neurontask.domain.usecase

import com.example.neurontask.domain.entity.UserState
import com.example.neurontask.domain.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetUserStateFlowUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): StateFlow<UserState> {
        return repository.getUserStateFlow()
    }
}