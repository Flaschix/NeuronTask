package com.example.neurontask.presentation.registration

enum class RegistrationField { PHONE, CODE, NAME, SURNAME,  }

data class RegistrationUiState(
    val fields: Map<RegistrationField, String> = mapOf(
        RegistrationField.PHONE to "",
        RegistrationField.CODE to "",
        RegistrationField.NAME to "",
        RegistrationField.SURNAME to ""
    ),
    val errors: List<Pair<RegistrationField, String>> = emptyList()
)

sealed class RegistrationValidateState {
    data object Success : RegistrationValidateState()
    data class Failure(val errors: List<Pair<RegistrationField, String>>) : RegistrationValidateState()
}