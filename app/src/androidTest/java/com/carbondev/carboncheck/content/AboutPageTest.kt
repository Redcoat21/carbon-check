package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.content.screen.AboutContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class AboutPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun aboutPage_displaysContent_andHandlesBackClick() {
        var backClicked = false

        composeTestRule.setContent {
            CarbonCheckTheme {
                AboutContent(onBackClick = { backClicked = true })
            }
        }

        composeTestRule.onNodeWithText("About")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(
            "CarbonCheck is a mobile app designed to help you understand and reduce your carbon footprint in your daily life.",
            substring = true
        ).assertExists()

        composeTestRule.onNodeWithContentDescription("Back")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        assertTrue("Back icon should trigger onBackClick", backClicked)
    }
}
