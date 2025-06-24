package com.carbondev.carboncheck.content

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.carbondev.carboncheck.presentation.content.screen.StatsPage
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test

class StatsPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun statsPage_displaysAllPieChartSectionsWithScroll() {
        composeTestRule.setContent {
            CarbonCheckTheme {
                StatsPage()
            }
        }

        composeTestRule.onNodeWithText("Your weekly Comparison").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your monthly Comparison").assertIsDisplayed()
    }
}
