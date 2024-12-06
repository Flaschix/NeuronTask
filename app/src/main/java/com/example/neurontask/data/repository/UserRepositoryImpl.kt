package com.example.neurontask.data.repository

import android.content.SharedPreferences
import com.example.neurontask.domain.entity.User
import com.example.neurontask.domain.entity.UserState
import com.example.neurontask.domain.repository.UserRepository
import com.example.neurontask.ext.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _userStateFlow = MutableStateFlow<UserState>(UserState.Init)
    private val userStateFlow: StateFlow<UserState>
        get() = _userStateFlow

    private val _userFlow = MutableSharedFlow<User>(replay = 1)
    private val userFlow: SharedFlow<User> = _userFlow.asSharedFlow()

    init {
        coroutineScope.launch {
            checkUserState()
        }
    }

    override suspend fun saveUser(user: User): Result<Unit> {
        return runCatching {
            sharedPreferences.edit()
                .putString("firstName", user.firstName)
                .putString("lastName", user.lastName)
                .putString("phone", user.phone)
                .apply()

            _userFlow.emit(user)
            _userStateFlow.value = UserState.Authorized
        }
    }

    override suspend fun getUser(): SharedFlow<User> = userFlow

    override suspend fun getUserStateFlow(): StateFlow<UserState> = userStateFlow

    override suspend fun checkUserState(): Result<Unit> {
        return runCatching {
            val firstName = sharedPreferences.getString("firstName", null)
            val lastName = sharedPreferences.getString("lastName", "") ?: ""
            val phone = sharedPreferences.getString("phone", "") ?: ""

            if (firstName != null) {
                val user = User(firstName = firstName, lastName = lastName, phone = phone)
                _userFlow.emit(user)
                _userStateFlow.value = UserState.Authorized
            } else {
                _userStateFlow.value = UserState.NotAuthorized
            }
        }
    }
}


