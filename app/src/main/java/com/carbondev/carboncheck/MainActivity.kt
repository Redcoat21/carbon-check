package com.carbondev.carboncheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carbondev.carboncheck.screen.LoginScreenCompose
import com.carbondev.carboncheck.screen.RegisterScreenCompose
import com.carbondev.carboncheck.ui.theme.CarbonCheckTheme

object AppDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home" // Example for after login
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarbonCheckTheme {
                AppNavigation()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN_ROUTE // Or check auth state to decide
    ) {
        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreenCompose(
                onSignInClicked = { email, password ->
                    // ViewModel logic for sign in
                },
                onSignUpClicked = {
                    navController.navigate(AppDestinations.REGISTER_ROUTE)
                }
            )
        }
        composable(AppDestinations.REGISTER_ROUTE) {
            RegisterScreenCompose(
                onRegisterClicked = { fullName, email, password ->
                    // Call your ViewModel to handle registration logic
                    // e.g., viewModel.registerUser(fullName, email, password)
                    // On success, navigate to login or home
                },
                onSignInClicked = {
                    navController.navigate(AppDestinations.LOGIN_ROUTE) {
                        // Optional: Pop up to REGISTER_ROUTE to remove it from backstack
                        popUpTo(AppDestinations.REGISTER_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(AppDestinations.HOME_ROUTE) {
            // Your main app content after login
            // Example: HomeScreen()
        }
        // Add other destinations as needed
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarbonCheckTheme {
        Greeting("Android")
    }
}