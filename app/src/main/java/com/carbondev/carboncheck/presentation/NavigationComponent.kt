package com.carbondev.carboncheck.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Navigation component for the CarbonCheck application.
 */
@Composable
fun NavigationComponent(startDestination: String = Routes.Auth.Welcome.route) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavHost(navController = navController, startDestination = startDestination) {

    }
}