package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carbondev.carboncheck.presentation.content.screen.ProfilePageContent
import com.carbondev.carboncheck.presentation.content.viewmodel.ProfileUiState
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
        composeTestRule.setContent {
            CarbonCheckTheme {
                ProfilePageContent(
                    profileData = ProfileUiState(
                        firstName = "John",
                        lastName = "Doe",
                        avatarUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                        email = "johndoe@example.com"
                    )
                )
            }
        }

        // Optional: wait for any image loading or recomposition
        composeTestRule.waitForIdle()

        // Ensure avatar contentDescription is correct in the actual Composable
        composeTestRule.onNodeWithContentDescription("User Avatar")
            .assertExists()
            .assertIsDisplayed()

        // Check name
        composeTestRule.onNodeWithText("John Doe")
            .assertExists()
            .assertIsDisplayed()

        // Check email
        composeTestRule.onNodeWithText("johndoe@example.com")
            .assertExists()
            .assertIsDisplayed()
    }
}
