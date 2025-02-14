@file:OptIn(ExperimentalMaterial3Api::class)

package com.valify.registrationsdk.presentation.ui.registration

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valify.registrationsdk.R
import com.valify.registrationsdk.domain.model.UserModel
import com.valify.registrationsdk.domain.utils.AppError
import com.valify.registrationsdk.domain.utils.AppError.ValidateError
import com.valify.registrationsdk.domain.utils.ValidationType
import com.valify.registrationsdk.presentation.components.CustomTextField
import com.valify.registrationsdk.presentation.theme.RegistrationSDKTheme
import com.valify.registrationsdk.presentation.theme.dimens
import com.valify.registrationsdk.presentation.ui.registration.viewmodel.RegistrationIntent
import com.valify.registrationsdk.presentation.ui.registration.viewmodel.RegistrationViewModel

@Composable
internal fun RegistrationScreen(viewModel: RegistrationViewModel = hiltViewModel(), onNavigateToCameraDetect: () -> Unit) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.success) {
        if (state.success) {
            viewModel.sendIntent(RegistrationIntent.ResetState)
            onNavigateToCameraDetect()
        }
    }

    LaunchedEffect(state.appFailure) {
        snackbarHostState.currentSnackbarData?.dismiss()
        state.appFailure?.let {
            val message = when (it) {
                is AppError.DatabaseError -> context.getString(R.string.database_error_msg)
                else -> context.getString(R.string.something_went_wrong_msg)
            }
            if (it !is ValidateError) {
                snackbarHostState.showSnackbar(message)
                viewModel.sendIntent(RegistrationIntent.ResetState)
            }
        }
    }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(R.string.registration_title), style = MaterialTheme.typography.titleMedium)
        }, modifier = Modifier.shadow(1.dp))
    }, snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) { contentPadding ->
        FormContent(
            validationError = state.appFailure?.let { if (it is ValidateError) it else null },
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(start = MaterialTheme.dimens.paddingLarge, end = MaterialTheme.dimens.paddingLarge, top = MaterialTheme.dimens.paddingLarge),
            onRestError = {
                if (state.appFailure is ValidateError)
                    viewModel.sendIntent(RegistrationIntent.ResetState)
            }
        ) {
            viewModel.sendIntent(RegistrationIntent.SaveUserIntent(it))
        }

    }

}

@Composable
internal fun FormContent(
    validationError: ValidateError? = null,
    modifier: Modifier = Modifier,
    onRestError: () -> Unit = {},
    onSubmitted: (UserModel) -> Unit = {},
) {
    val usernameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val usernameFocus = remember { FocusRequester() }
    val emailFocus = remember { FocusRequester() }
    val phoneFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            CustomTextField(
                value = usernameState.value,
                label = stringResource(R.string.username_text_field_label),
                supportingText = getValidationMessage(validationError, "username", R.string.please_enter_valid_username_error_msg),
                isError = validationError?.fieldKey == "username",
                onValueChange = {
                    usernameState.value = it
                    onRestError()
                },
                onClearText = { usernameState.value = "" },
                imeAction = ImeAction.Next,
                onNext = { emailFocus.requestFocus() },
                focusRequester = usernameFocus,
            )
        }
        item {
            CustomTextField(
                value = emailState.value,
                label = stringResource(R.string.email_text_field_label),
                supportingText = getValidationMessage(validationError, "email", R.string.please_enter_valid_email_error_msg),
                isError = validationError?.fieldKey == "email",
                onValueChange = {
                    emailState.value = it
                    onRestError()
                },
                onClearText = { emailState.value = "" },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                onNext = { phoneFocus.requestFocus() },
                focusRequester = emailFocus,
            )
        }
        item {
            CustomTextField(
                value = phoneState.value,
                label = stringResource(R.string.phone_text_field_label),
                supportingText = getValidationMessage(validationError, "phone", R.string.please_enter_valid_phone_error_msg),
                isError = validationError?.fieldKey == "phone",
                keyboardType = KeyboardType.Phone,
                onValueChange = {
                    phoneState.value = it
                    onRestError()
                },
                imeAction = ImeAction.Next,
                onClearText = { phoneState.value = "" },
                onNext = { passwordFocus.requestFocus() },
                focusRequester = phoneFocus,
            )
        }
        item {
            CustomTextField(
                value = passwordState.value,
                label = stringResource(R.string.password_text_field_label),
                supportingText = getValidationMessage(validationError, "password", R.string.please_enter_valid_password_error_msg),
                isError = validationError?.fieldKey == "password",
                keyboardType = KeyboardType.Password,
                onValueChange = {
                    passwordState.value = it
                    onRestError()

                },
                imeAction = ImeAction.Done,
                passwordToggle = true,
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    passwordFocus.freeFocus()
                },
                focusRequester = passwordFocus,
            )
        }
        item {
            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onSubmitted(
                        UserModel(
                            username = usernameState.value,
                            email = emailState.value,
                            phone = phoneState.value,
                            password = passwordState.value
                        )
                    )
                }, modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.paddingMedium)
                    .fillMaxWidth()
                    .height(MaterialTheme.dimens.heightMedium)
            ) {
                Text(stringResource(R.string.register_button_label), style = MaterialTheme.typography.titleMedium)
            }
        }
    }

}

@Composable
fun getValidationMessage(error: ValidateError?, fieldKey: String, @StringRes errorMessage: Int): String {
    return if (error?.fieldKey == fieldKey) {
        when (error.type) {
            ValidationType.Invalid -> stringResource(errorMessage)
            else -> stringResource(R.string.required_text_field)
        }
    } else ""
}

@Preview(device = "id:small_phone", backgroundColor = 0xFFFFFFFF, showBackground = true)
@PreviewScreenSizes
@Composable
private fun PreviewRegistrationForm() {
    RegistrationSDKTheme {
        FormContent(
            validationError = ValidateError("username", ValidationType.Invalid),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.paddingLarge)
        ) {
        }
    }
}