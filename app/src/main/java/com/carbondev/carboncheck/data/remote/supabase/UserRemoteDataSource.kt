package com.carbondev.carboncheck.data.remote.supabase

import com.carbondev.carboncheck.data.remote.model.NetworkUser
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

/**
 * This class is responsible for fetching the user data from the remote data source.
 * It uses the Supabase client to make the network requests.
 *
 * @param client The Supabase client used to make network requests.
 */
class UserRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    suspend fun getUser(id: String): NetworkUser {
        val columns = Columns.list(NetworkUser.Columns.ALL.toList())

        val req = client.from(NetworkUser.TABLE_NAME).select(columns = columns) {
            filter {
                and {
                    eq(NetworkUser.Columns.ID, id)
                }
            }
        }

        val res = req.decodeSingle<NetworkUser>()
        return res
    }

    suspend fun getCurrentUser(): NetworkUser {
        val currentUserInfo = client.auth.currentUserOrNull()
        // Well it shouldn't ever return null, but who knows
        val currentUser =
            getUser(currentUserInfo?.id ?: throw NoSuchElementException("No current user found"))
        return currentUser
    }
}