//package com.carbondev.carboncheck.auth
//
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
//import com.carbondev.carboncheck.presentation.auth.screen.RegisterScreenContent
//import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
//import org.junit.Assert.*
//import org.junit.Rule
//import org.junit.Test
//
//class RegisterScreenTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun registerScreen_displaysFields_andHandlesInteractions() {
//        var onRegisterCalled = false
//        var onSignInCalled = false
//        var capturedData: List<String> = emptyList()
//
//        composeTestRule.setContent {
//            CarbonCheckTheme {
//                RegisterScreenContent(
////                    onRegisterClicked = { email, password, confirmPassword, firstName, lastName ->
////                        onRegisterCalled = true
////                        capturedData = listOf(email, password, confirmPassword, firstName, lastName)
////                    },
////                    onSignInClicked = { onSignInCalled = true }
////                )
////            }
//        }
//
//        val testEmail = "test@example.com"
//        val testPassword = "password123"
//        val testFirstName = "John"
//        val testLastName = "Doe"
//
//        val fields = composeTestRule.onAllNodes(hasSetTextAction())
//
//        fields[0].performTextInput(testFirstName)
//        fields[1].performTextInput(testLastName)
//        fields[2].performTextInput(testEmail)
//        fields[3].performTextInput(testPassword)
//        fields[4].performTextInput(testPassword)
//
//        composeTestRule.onNodeWithText("Register")
//            .assertExists()
//            .performClick()
//
//        assertTrue("Register button should trigger callback", onRegisterCalled)
//        assertEquals(listOf(testEmail, testPassword, testPassword, testFirstName, testLastName), capturedData)
//
//        composeTestRule.onNodeWithText("Sign in")
//            .assertExists()
//            .performClick()
//
//        assertTrue("Sign in button should trigger callback", onSignInCalled)
//    }
//}
