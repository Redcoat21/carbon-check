package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.carbondev.carboncheck.presentation.content.screen.ActivityFormPage
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test

class ActivityFormPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun activityFormPage_displaysTabsAndForms() {
        composeTestRule.setContent {
            CarbonCheckTheme {
                ActivityFormPage(navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Car").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("Flight").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("Food").assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithText("Log Car Ride").assertIsDisplayed()
        composeTestRule.onNodeWithText("Distance (km)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Car Type").assertIsDisplayed()

        composeTestRule.onNodeWithText("Flight").performClick()

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodesWithText("Log Flight").fetchSemanticsNodes().isNotEmpty()
        }

        // Verifikasi tab Flight aktif
        composeTestRule.onNodeWithText("Log Flight").assertIsDisplayed()
        composeTestRule.onNodeWithText("Departure Airport (IATA Code)").assertIsDisplayed()

        composeTestRule.onNodeWithText("Food").performClick()

        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodesWithText("Log Food Consumption").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Log Food Consumption").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight (grams)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Food Type").assertIsDisplayed()
    }
}
