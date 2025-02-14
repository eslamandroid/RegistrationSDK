package com.valify.registrationsdk.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CustomTextField(
    value: String,
    label: String,
    placeholder: String = "",
    enabled: Boolean = true,
    passwordToggle: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    supportingText: String? = null,
    minLines: Int = 1,
    onDone: () -> Unit = {},
    onNext: () -> Unit = {},
    onClearText: () -> Unit = {},
) {
    val passwordVisible = rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        value = value,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = {
            Text(label, style = MaterialTheme.typography.titleMedium)
        },
        enabled = enabled,
        minLines = minLines,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = KeyboardActions(onDone = {
            onDone()
        }, onNext = {
            onNext()
        }),
        shape = RoundedCornerShape(15),
        visualTransformation =
        if (keyboardType == KeyboardType.Password && passwordToggle && passwordVisible.value )
            PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        supportingText = {
            if (supportingText != null)
                Text(supportingText, style = MaterialTheme.typography.labelMedium)
        },
        singleLine = minLines == 1,
        trailingIcon = {
            if (keyboardType == KeyboardType.Password && passwordToggle) {
                val image = if (passwordVisible.value) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            } else
                if (value.isNotEmpty()) {
                    IconButton(onClick = onClearText) {
                        Icon(
                            imageVector = Icons.Outlined.Close, contentDescription = null
                        )
                    }
                }
        })
}

@Composable
@Preview(device = "spec:width=411dp,height=891dp")
fun PreviewTextField() {
    CustomTextField("", "Username", focusRequester = FocusRequester(), onValueChange = {})
}