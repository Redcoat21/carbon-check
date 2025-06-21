package com.carbondev.carboncheck.presentation.auth.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.carbondev.carboncheck.presentation.auth.viewmodel.LoginViewModel
import com.carbondev.carboncheck.presentation.common.UiStateHandler
import com.carbondev.carboncheck.presentation.common.component.PasswordTextField
import com.carbondev.carboncheck.presentation.common.component.StringTextField
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import com.carbondev.carboncheck.presentation.ui.theme.Green80

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
    UiStateHandler(
        viewModel = viewModel,
        onSuccess = {
            navController.popBackStack(Routes.Auth.Welcome.route, inclusive = false)
            navController.navigate(Routes.Home.route)
        }) {
        LoginScreenContent(onSignInClicked = { email, password ->
            viewModel.login(email = email, password = password)
        }, onSignUpClicked = {
            navController.navigate(Routes.Auth.Register.route)
        })
    }
}

@Composable
fun LoginScreenContent(
    onSignInClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Section
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Green80)
        ) {
            Image(
                painter = painterResource(id = R.drawable.eco), // Replace with your actual drawable
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Inside
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Sign in to CarbonCheck",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier
                .height(64.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(0.7f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StringTextField(label = "Email", value = email, onValueChange = { email = it })
            PasswordTextField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSignInClicked(email, password)
                    }
                ))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSignInClicked(email, password) },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(48.dp),
        ) {
            Text("Sign In", fontSize = 16.sp)
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onSignUpClicked) {
                Text(
                    text = "Sign up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(
    name = "Dark mode preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenComposeDarkModePreview() {
    CarbonCheckTheme {
        LoginScreenContent(onSignInClicked = { _, _ -> }, onSignUpClicked = {})
    }
}

@Preview(name = "Light mode preview")
@Composable
fun LoginScreenComposeLightModePreview() {
    CarbonCheckTheme {
        LoginScreenContent(onSignInClicked = { _, _ -> }, onSignUpClicked = {})
    }
}

@Preview(
    name = "Dark mode preview in Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun LoginScreenComposeDarkModeLandscapePreview() {
    CarbonCheckTheme {
        LoginScreenContent(onSignInClicked = { _, _ -> }, onSignUpClicked = {})
    }
}

@Preview(
    name = "Light mode preview in Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun LoginScreenComposeLightModeLandscapePreview() {
    CarbonCheckTheme {
        LoginScreenContent(onSignInClicked = { _, _ -> }, onSignUpClicked = {})
    }
}


