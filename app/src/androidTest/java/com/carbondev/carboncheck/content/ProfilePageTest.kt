package com.carbondev.carboncheck.content

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carbondev.carboncheck.presentation.content.screen.ProfileScreenContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfilePageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profilePage_displaysUserInfoCorrectly() {
        // Start the UI
        composeTestRule.setContent {
            CarbonCheckTheme {
                ProfileScreenContent()
            }
        }

        // Check avatar image is displayed
        composeTestRule.onNodeWithContentDescription("User Avatar")
            .assertIsDisplayed()

        // Check name is displayed
        composeTestRule.onNodeWithText("John Doe")
            .assertIsDisplayed()

        // Check email is displayed
        composeTestRule.onNodeWithText("johndoe@example.com")
            .assertIsDisplayed()
    }
}

