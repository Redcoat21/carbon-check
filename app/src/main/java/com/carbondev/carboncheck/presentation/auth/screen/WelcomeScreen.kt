package com.carbondev.carboncheck.presentation.auth.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import com.carbondev.carboncheck.presentation.ui.theme.Green80
import com.carbondev.carboncheck.presentation.ui.theme.Typography

@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    WelcomeScreenContent(onGetStartedClick = {
        navHostController.navigate(Routes.Auth.Login.route)
    })
}

@Composable
fun WelcomeScreenContent(onGetStartedClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.eco),
                contentDescription = "App logo",
            )
            Text("Carbon Check", style = Typography.titleLarge)
            Text(
                "Ready to start your eco-friendly journey?",
                style = Typography.bodyMedium
            )
            Button(onClick = onGetStartedClick, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Get started")
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
fun WelcomeScreenDarkModePreview() {
    CarbonCheckTheme {
        WelcomeScreenContent(onGetStartedClick = {})
    }
}

@Preview(name = "Light mode preview")
@Composable
fun WelcomeScreenLightModePreview() {
    CarbonCheckTheme {
        WelcomeScreenContent(onGetStartedClick = {})
    }
}

@Preview(
    name = "Dark mode preview in Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun WelcomeScreenDarkModeLandscapePreview() {
    CarbonCheckTheme {
        WelcomeScreenContent(onGetStartedClick = {})
    }
}

@Preview(
    name = "Light mode preview in Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun WelcomeScreenLightModeLandscapePreview() {
    CarbonCheckTheme {
        WelcomeScreenContent(onGetStartedClick = {})
    }
}
