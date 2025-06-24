package com.carbondev.carboncheck.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.carbondev.carboncheck.presentation.content.screen.ActivityFormPage
import com.carbondev.carboncheck.presentation.auth.screen.LoginScreen
import com.carbondev.carboncheck.presentation.auth.screen.RegisterScreen
import com.carbondev.carboncheck.presentation.auth.screen.WelcomeScreen
import com.carbondev.carboncheck.presentation.content.screen.AboutPage
import com.carbondev.carboncheck.presentation.content.screen.EditActivityFormPage
import com.carbondev.carboncheck.presentation.content.screen.ProfileEditPage
import com.carbondev.carboncheck.presentation.content.screen.SettingsPage
import com.carbondev.carboncheck.presentation.navbar.screen.MainScreen

/**
 * Navigation component for the CarbonCheck application.
 */
@Composable
fun NavigationComponent(startDestination: String = Routes.Auth.Welcome.route) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Auth.Welcome.route) {
            WelcomeScreen(navHostController = navController)
        }

        composable(Routes.Auth.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.Auth.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Routes.Home.route) {
            MainScreen(navController = navController)
        }

        composable(Routes.Add.route) {
            ActivityFormPage(navController = navController)
        }

        composable(
            route = Routes.Edit.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { entry ->
            EditActivityFormPage(navController = navController, userId =  entry.arguments?.getString("userId"))
        }

        composable(Routes.Settings.route) {
            SettingsPage(navController = navController)
        }

        composable(Routes.About.route) {
            AboutPage(navController = navController)
        }

        composable(Routes.ProfileEdit.route) {
            ProfileEditPage(navController = navController)
        }
    }
}