package com.carbondev.carboncheck.presentation

/**
 * Routes for the CarbonCheck application.
 */
sealed class Routes(val route: String) {
    object Auth : Routes("auth") {
        object Welcome : Routes("auth/welcome")
        object Login : Routes("auth/login")
        object Register : Routes("auth/register")

    }

    object Home : Routes("home")

    object Add : Routes("add")

    object Settings : Routes("settings")

    object About : Routes("about")

    object ProfileEdit : Routes("profileEdit")

    object  Edit : Routes("edit/{userId}"){
        fun createRoute(userId: String) = "edit/$userId"
    }
}