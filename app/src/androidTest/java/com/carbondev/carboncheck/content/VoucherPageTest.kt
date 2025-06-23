package com.carbondev.carboncheck.presentation.content.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VoucherPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun voucherPage_displaysPointsAndClaimDialog() {
        val currentPoints = 120

        composeTestRule.setContent {
            CarbonCheckTheme {
                VoucherPage(currentPoints = currentPoints)
            }
        }

        composeTestRule.onNodeWithText("Your Points: $currentPoints")
            .assertIsDisplayed()

        val claimButtons = composeTestRule.onAllNodesWithText("Claim")

        for (i in 0 until claimButtons.fetchSemanticsNodes().size) {
            try {
                claimButtons[i].assertIsDisplayed()
                claimButtons[i].assertIsEnabled()
                claimButtons[i].performClick()
                break
            } catch (e: AssertionError) {
            }
        }

        composeTestRule.onNodeWithText("Voucher Claimed!")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.onNodeWithText("Voucher Claimed!")
            .assertDoesNotExist()
    }
}
