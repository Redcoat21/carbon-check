package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.content.screen.ProfileEditContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test

class ProfileEditPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileEdit_displaysFields_andAllowsEditing() {
        composeTestRule.setContent {
            CarbonCheckTheme {
                ProfileEditContent()
            }
        }

        composeTestRule.onNodeWithTag("FirstNameInput").assertIsDisplayed()
        composeTestRule.onNodeWithTag("LastNameInput").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AvatarUrlInput").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AvatarImage").assertIsDisplayed()
        composeTestRule.onNodeWithTag("SaveButton").assertIsDisplayed()

        composeTestRule.onNodeWithTag("FirstNameInput")
            .performTextReplacement("Alice")

        composeTestRule.onNodeWithTag("LastNameInput")
            .performTextReplacement("Smith")

        composeTestRule.onNodeWithTag("AvatarUrlInput")
            .performTextReplacement("https://i.pravatar.cc/150?img=10")

        composeTestRule.onNodeWithTag("SaveButton").performClick()
    }
}