package com.carbondev.carboncheck.domain.repository

import com.carbondev.carboncheck.domain.common.Result

interface AuthRepository {
    /**
     * Logs in a user with the specified email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A result indicating success or failure of the login operation.
     */
    suspend fun loginWithEmail(email: String, password: String): Result<Unit>

    /**
     * Logs in a user with the specified phone number and OTP.
     *
     * @param phoneNumber The user's phone number.
     * @param otp The one-time password sent to the user's phone.
     * @return A result indicating success or failure of the login operation.
     */
    suspend fun loginWithPhone(phoneNumber: String, otp: String): Result<Unit>
}