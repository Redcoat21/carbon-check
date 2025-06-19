package com.carbondev.carboncheck.data.remote.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

/**
 * This class is responsible for handling authentication-related operations with Supabase.
 * It uses the Supabase client to perform login and other auth-related tasks.
 *
 * @param client The Supabase client used to make network requests.
 */
class AuthRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    /**
     * Logs in a user using their email and password.
     * @param email The user's email address.
     * @param password The user's password.
     */
    suspend fun loginWithEmailAndPassword(email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    /**
     * Registers a new user with an email and password.
     * @param email The user's email address.
     * @param password The user's password.
     */
    suspend fun registerWithEmailAndPassword(email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    /**
     * Logs out the current user.
     */
    suspend fun logout() {
        client.auth.signOut()
    }
}