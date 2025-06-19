package com.carbondev.carboncheck.domain.validation

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ValidationRulesTest {
    @Test
    fun `should pass when given valid email`() = runTest {
        // Arrange
        val email = "email@example.com"
        // Act
        val result = ValidationRules.email.validate(email)
        // Assert
        assertTrue(result.isValid)
    }

    @Test
    fun `should fail when given invalid email`() = runTest {
        // Arrange
        val email = "invalid-email"
        // Act
        val result = ValidationRules.email.validate(email)
        // Assert
        assertFalse(result.isValid)
    }

    @Test
    fun `should pass when given valid password`() = runTest {
        // Arrange
        val password = "ValidPassword123!"
        // Act
        val result = ValidationRules.password.validate(password)
        // Assert
        assertTrue(result.isValid)
    }

    @Test
    fun `should fail when given invalid password`() = runTest {
        // Arrange
        val password = "short"
        // Act
        val result = ValidationRules.password.validate(password)
        // Assert
        assertFalse(result.isValid)
    }
}