package com.carbondev.carboncheck.data.remote.supabase

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import com.carbondev.carboncheck.domain.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import timber.log.Timber
import javax.inject.Inject

/**
 * This class is responsible for handling authentication-related operations with Supabase.
 * It uses the Supabase client to perform login and other auth-related tasks.
 *
 * @param client The Supabase client used to make network requests.
 */
class AuthRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    /**
     * Logs in a user and then fetches their full profile from the 'profiles' table.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return The domain [User] object on success, or null if login or profile fetch fails.
     */
    suspend fun loginWithEmailAndPassword(email: String, password: String): User? {
        return try {
            // Step 1: Authenticate the user
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val authUser = client.auth.currentUserOrNull() ?: return null
            Timber.tag("Auth").d("User ${authUser.id} authenticated successfully.")

            // Step 2: Fetch the user profile from the "users" table.
            val networkUser = client.postgrest
                .from(NetworkUser.TABLE_NAME)
                .select {
                    filter {
                        eq(NetworkUser.Columns.ID, authUser.id)
                    }
                }
                .decodeSingle<NetworkUser>()

            Timber.tag("Auth").d("Fetched profile for user: %s", networkUser)

            // Step 3: Map the NetworkUser to domain model and return it
            networkUser.toDomainModel()

        } catch (e: Exception) {
            Timber.tag("Auth").e(e, "Login or profile fetch failed")
            client.auth.signOut()
            return null
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