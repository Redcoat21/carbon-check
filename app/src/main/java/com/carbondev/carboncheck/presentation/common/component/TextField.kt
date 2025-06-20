package com.carbondev.carboncheck.presentation.common.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.presentation.ui.theme.Typography
import io.konform.validation.Validation
import io.konform.validation.ValidationResult

@Composable
private fun BaseTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    validator: Validation<String>? = null,
    validationResult: ValidationResult<String>? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValidationResultChange: (ValidationResult<String>?) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null
) {

    LaunchedEffect(value) {
        onValidationResultChange(
            if (value.isNotEmpty()) validator?.invoke(value) else null
        )
    }

    val hasErrors = validationResult?.errors?.isNotEmpty() == true

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        Text(
            text = label,
            style = Typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = if (hasErrors) Color.Red else Color.Transparent,
                unfocusedIndicatorColor = if (hasErrors) Color.Red else Color.Transparent,
                focusedTextColor = if (hasErrors) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = if (hasErrors) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            value = value,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        validationResult?.errors?.forEach {
            Text(
                text = it.message,
                color = Color.Red,
                style = Typography.bodySmall
            )
        }
    }
}

/**
 * A reusable text field component for entering strings.
 */
@Composable
fun StringTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult<String>? = null,
    onValidationResultChange: (ValidationResult<String>?) -> Unit = {},
    validator: Validation<String>? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    BaseTextField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        validator = validator,
        validationResult = validationResult,
        onValidationResultChange = onValidationResultChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}

/**
 * A reusable text field component for entering passwords.
 * It includes a toggle to reveal or hide the password.
 */
@Composable
fun PasswordTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult<String>? = null,
    onValidationResultChange: (ValidationResult<String>?) -> Unit = {},
    validator: Validation<String>? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var revealPassword by rememberSaveable { mutableStateOf(false) }
    val visualTransformation = if (revealPassword) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    BaseTextField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        validator = validator,
        validationResult = validationResult,
        onValidationResultChange = onValidationResultChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) {
        val icon = if (revealPassword) {
            painterResource(R.drawable.eco)
        } else {
            painterResource(R.drawable.eco)
        }

        IconButton(
            onClick = { revealPassword = !revealPassword },
        ) {
            Icon(
                painter = icon,
                contentDescription = "Toggle Password Visibility",
            )
        }
    }
}

@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TextFieldDarkModePreview() {
    StringTextField(label = "Username", value = "", onValueChange = {})
}

@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PasswordFieldDarkModePreview() {
    PasswordTextField(label = "Password", value = "", onValueChange = {})
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    StringTextField(label = "Username", value = "", onValueChange = {})
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview() {
    PasswordTextField(label = "Password", value = "", onValueChange = {})
}