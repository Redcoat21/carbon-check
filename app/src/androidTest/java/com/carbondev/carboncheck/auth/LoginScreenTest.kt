package com.carbondev.carboncheck.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.carbondev.carboncheck.presentation.auth.screen.LoginScreenContent
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysElements_andRespondsToClicks() {
        var signInCalled = false
        var signUpCalled = false
        var capturedEmail = ""
        var capturedPassword = ""

        composeTestRule.setContent {
            CarbonCheckTheme {
                LoginScreenContent(
                    onSignInClicked = { email, password ->
                        signInCalled = true
                        capturedEmail = email
                        capturedPassword = password
                    },
                    onSignUpClicked = {
                        signUpCalled = true
                    }
                )
            }
        }

        val email = "test@example.com"
        val password = "password123"

        composeTestRule.onAllNodes(hasSetTextAction())[0]
            .performTextInput(email)

        composeTestRule.onAllNodes(hasSetTextAction())[1]
            .performTextInput(password)

        composeTestRule.onNodeWithText("Sign In")
            .performClick()

        assertTrue("Sign in callback should be called", signInCalled)
        assertEquals(email, capturedEmail)
        assertEquals(password, capturedPassword)

        composeTestRule.onNodeWithText("Sign up")
            .performClick()

        assertTrue("Sign up callback should be called", signUpCalled)
    }
}
