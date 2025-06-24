package com.carbondev.carboncheck.presentation.auth.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.auth.viewmodel.RegisterViewModel
import com.carbondev.carboncheck.presentation.common.UiStateHandler
import com.carbondev.carboncheck.presentation.common.component.PasswordTextField
import com.carbondev.carboncheck.presentation.common.component.StringTextField
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>()
) {
    UiStateHandler(
        viewModel = viewModel,
        onSuccess = {
            navController.navigate(Routes.Auth.Login.route) {
                popUpTo(Routes.Auth.Welcome.route)
            }
        }) {
        RegisterScreenContent(
            onRegisterClicked = { email, password, confirmPassword ->
                viewModel.register(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
                navController.navigate(Routes.Auth.Login.route)
            },
            onSignInClicked = {
                navController.navigate(Routes.Auth.Login.route)
            }
        )
    }
}

@Composable
fun RegisterScreenContent(
    onRegisterClicked: (String, String, String) -> Unit, // Updated signature
    onSignInClicked: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Section
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.eco),
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Inside
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Join CarbonCheck",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Start tracking your carbon footprint",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Form Fields
        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StringTextField(label = "Email", value = email, onValueChange = { email = it })
            PasswordTextField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
            )
            PasswordTextField(
                label = "Confirm Password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onRegisterClicked(
                            email,
                            password,
                            confirmPassword,
                        )
                    }
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = { onRegisterClicked(email, password, confirmPassword) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(48.dp),
        ) {
            Text("Register", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign In Link
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onSignInClicked) {
                Text(
                    text = "Sign in",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegisterScreenComposeDarkModePreview() {
    CarbonCheckTheme {
        // Updated preview call
        RegisterScreenContent(onRegisterClicked = { _, _, _ -> }, onSignInClicked = {})
    }
}

@Preview(name = "Light mode preview")
@Composable
fun RegisterScreenComposeLightModePreview() {
    CarbonCheckTheme {
        // Updated preview call
        RegisterScreenContent(onRegisterClicked = { _, _, _ -> }, onSignInClicked = {})
    }
}

@Preview(
    name = "Dark mode preview in Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun RegisterScreenComposeDarkModeLandscapePreview() {
    CarbonCheckTheme {
        // Updated preview call
        RegisterScreenContent(onRegisterClicked = { _, _, _ -> }, onSignInClicked = {})
    }
}

@Preview(
    name = "Light mode preview in Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun RegisterScreenComposeLightModeLandscapePreview() {
    CarbonCheckTheme {
        // Updated preview call
        RegisterScreenContent(onRegisterClicked = { _, _, _ -> }, onSignInClicked = {})
    }
}