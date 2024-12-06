package com.example.neurontask.presentation.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neurontask.R
import com.example.neurontask.presentation.BackBtn

@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            RegistrationHeader(onBack = onBack)

            Spacer(modifier = Modifier.height(16.dp))

            InputFieldsSection(
                uiState = uiState,
                onFieldChange = viewModel::onFieldChange
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TermsAndConditionsText()

            Spacer(modifier = Modifier.height(16.dp))

            RegistrationButton(
                isEnabled = uiState.fields.all { it.value.isNotBlank() },
                onClick = { viewModel.onContinue(onBack) }
            )
        }
    }
}

@Composable
fun RegistrationHeader(onBack: () -> Unit) {
    Column {
        BackBtn(onBack)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.registration_btn),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InputFieldsSection(
    uiState: RegistrationUiState,
    onFieldChange: (RegistrationField, String) -> Unit
) {
    RegistrationField.entries.forEach { field ->
        val value = uiState.fields[field].orEmpty()
        val error = uiState.errors.find { it.first == field }?.second

        InputField(
            value = value,
            onValueChange = { onFieldChange(field, it) },
            label = getFieldLabel(field),
            error = error != null,
            helperText = error ?: getFieldHelperText(field)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TermsAndConditionsText() {
    Text(
        buildAnnotatedString {
            append(stringResource(id = R.string.user_terms_part_1) + ' ')
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(stringResource(id = R.string.user_terms_part_2))
            }
        },
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RegistrationButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            disabledContainerColor = MaterialTheme.colorScheme.onError
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.bodyLarge)
    }
}


@Composable
fun getFieldLabel(field: RegistrationField): String {
    return when (field) {
        RegistrationField.PHONE -> stringResource(id = R.string.phone)
        RegistrationField.CODE -> stringResource(id = R.string.code)
        RegistrationField.NAME -> stringResource(id = R.string.name)
        RegistrationField.SURNAME -> stringResource(id = R.string.surname)
    }
}

@Composable
fun getFieldHelperText(field: RegistrationField): String {
    return when (field) {
        RegistrationField.PHONE -> stringResource(id = R.string.phone_tip)
        RegistrationField.CODE -> stringResource(id = R.string.code_tip)
        RegistrationField.NAME -> stringResource(id = R.string.name_tip)
        RegistrationField.SURNAME -> stringResource(id = R.string.surname_tip)
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: Boolean,
    helperText: String
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor = when {
        error -> MaterialTheme.colorScheme.error
        isFocused -> Color.Transparent
        else -> Color.Transparent
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(1.dp, borderColor, shape = RoundedCornerShape(16.dp))
        ) {
            if (value.isEmpty() && !isFocused) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            BasicTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    isFocused = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (error) stringResource(id = R.string.incorrect_data) else helperText,
            color = if (error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}





