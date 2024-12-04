package com.example.neurontask.data.repository

import android.content.SharedPreferences
import com.example.neurontask.domain.entity.User
import com.example.neurontask.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _userStateFlow = MutableStateFlow<User?>(null)

    private val userStateFlow: StateFlow<User?>
        get() = _userStateFlow

    init {
        val user = loadUserFromPreferences()
        _userStateFlow.value = user
    }

    override suspend fun saveUser(user: User): Result<Unit> {
        return try {
            sharedPreferences.edit()
                .putString("firstName", user.firstName)
                .putString("lastName", user.lastName)
                .putString("phone", user.phone)
                .apply()
            _userStateFlow.value = user
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser() = flow {
        emit(userStateFlow.value)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    private fun loadUserFromPreferences(): User? {
        val firstName = sharedPreferences.getString("firstName", null)
        val lastName = sharedPreferences.getString("lastName", null)
        val phone = sharedPreferences.getString("phone", null)
        return if (firstName != null && lastName != null && phone != null) {
            User(firstName, lastName, phone)
        } else {
            null
        }
    }
}
