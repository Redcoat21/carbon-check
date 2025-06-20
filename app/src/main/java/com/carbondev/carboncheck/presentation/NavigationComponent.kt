package com.carbondev.carboncheck.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.carbondev.carboncheck.presentation.auth.screen.LoginScreen
import com.carbondev.carboncheck.presentation.auth.screen.RegisterScreen

/**
 * Navigation component for the CarbonCheck application.
 */
@Composable
fun NavigationComponent(startDestination: String = Routes.Auth.Login.route) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Auth.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.Auth.Register.route) {
            RegisterScreen(navController = navController)
        }

    }
}