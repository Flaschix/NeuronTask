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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _userStateFlow = MutableStateFlow<UserState>(UserState.Init)
    private val userStateFlow: StateFlow<UserState>
        get() = _userStateFlow


    private val updateUserFlow = MutableSharedFlow<User>()

    private val user: SharedFlow<User> = flow {
        val user = sharedPreferences.getString("firstName", null)?.let {
            User(
                firstName = it,
                lastName = sharedPreferences.getString("lastName", "") ?: "",
                phone = sharedPreferences.getString("phone", "") ?: ""
            )
        }
        user?.let { emit(user) }
    }.mergeWith(updateUserFlow).shareIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )

    override suspend fun saveUser(user: User): Result<Unit> {
        return runCatching {
            sharedPreferences.edit()
                .putString("firstName", user.firstName)
                .putString("lastName", user.lastName)
                .putString("phone", user.phone)
                .apply()

            updateUserFlow.emit(user)

            if(userStateFlow.value != UserState.Authorized) _userStateFlow.value = UserState.Authorized
        }
    }

    override suspend fun getUser() = user

    override suspend fun getUserStateFlow(): StateFlow<UserState> = userStateFlow

    override suspend fun checkUserState(): Result<Unit> {
        return runCatching {
            val isUserSaved = sharedPreferences.getString("firstName", null) != null
            _userStateFlow.value = if (isUserSaved) UserState.Authorized else UserState.NotAuthorized
        }
    }
}

