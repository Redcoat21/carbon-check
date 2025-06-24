package com.carbondev.carboncheck.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.auth.screen.WelcomeScreenContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

class WelcomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun welcomeScreen_displaysContentAndHandlesClick() {
        var buttonClicked = false

        composeTestRule.setContent {
            CarbonCheckTheme {
                WelcomeScreenContent(
                    onGetStartedClick = { buttonClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("App logo")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Carbon Check")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Ready to start your eco-friendly journey?")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Get started")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        assertTrue("Button click should trigger onGetStartedClick", buttonClicked)
    }
}
