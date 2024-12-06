package com.example.neurontask.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neurontask.domain.entity.User
import com.example.neurontask.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    fun onFieldChange(field: RegistrationField, newValue: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.fields.toMutableMap()
            updatedFields[field] = newValue
            currentState.copy(fields = updatedFields, errors = emptyList())
        }
    }

    fun onContinue(onSuccess: () -> Unit) {
        val currentState = _uiState.value

        when (val validationResult = validateFields(currentState.fields)) {
            is RegistrationValidateState.Success -> {
                viewModelScope.launch {
                    val user = User(
                        firstName = currentState.fields[RegistrationField.NAME].orEmpty(),
                        lastName = currentState.fields[RegistrationField.SURNAME].orEmpty(),
                        phone = currentState.fields[RegistrationField.PHONE].orEmpty()
                    )
                    saveUserUseCase(user)
                    onSuccess()
                }
            }
            is RegistrationValidateState.Failure -> {
                _uiState.update { it.copy(errors = validationResult.errors) }
            }
        }
    }

    private fun validateFields(fields: Map<RegistrationField, String>): RegistrationValidateState {
        val errors = mutableListOf<Pair<RegistrationField, String>>()

        if (fields[RegistrationField.NAME].isNullOrBlank()) {
            errors.add(RegistrationField.NAME to "Введите имя")
        }
        if (fields[RegistrationField.SURNAME].isNullOrBlank()) {
            errors.add(RegistrationField.SURNAME to "Введите фамилию")
        }
        if (fields[RegistrationField.PHONE].orEmpty().length != 16) {
            errors.add(RegistrationField.PHONE to "Введите корректный номер телефона")
        }
        if (fields[RegistrationField.CODE].isNullOrBlank()) {
            errors.add(RegistrationField.CODE to "Введите код")
        }

        return if (errors.isEmpty()) {
            RegistrationValidateState.Success
        } else {
            RegistrationValidateState.Failure(errors)
        }
    }
}






