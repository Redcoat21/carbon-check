package com.carbondev.carboncheck.content

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.content.screen.HomePagePreviewLight
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test

class HomePageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homePagePreview_displaysUserInfoAndActivities() {
        composeTestRule.setContent {
            CarbonCheckTheme {
                HomePagePreviewLight()
            }
        }

        composeTestRule.onNodeWithText("Hi Vivek,").assertIsDisplayed()
        composeTestRule.onNodeWithText("Recent activities").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add Activity").assertIsDisplayed()
    }
}
