package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.content.screen.SettingsPageContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test

class SettingsPageContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsPage_displaysItems_andTriggersCallbacks() {
        var editClicked = false
        var aboutClicked = false
        var logoutClicked = false

        composeTestRule.setContent {
            CarbonCheckTheme {
                SettingsPageContent(
                    onEditProfileClick = { editClicked = true },
                    onAboutClick = { aboutClicked = true },
                    onLogoutClick = { logoutClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
        composeTestRule.onNodeWithText("Logout").assertIsDisplayed()

        composeTestRule.onNodeWithText("Edit Profile").performClick()
        assert(editClicked)

        composeTestRule.onNodeWithText("About").performClick()
        assert(aboutClicked)

        composeTestRule.onNodeWithText("Logout").performClick()
        assert(logoutClicked)
    }
}
