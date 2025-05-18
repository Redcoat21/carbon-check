package com.carbondev.carboncheck.data.remote.supabase

import com.carbondev.carboncheck.data.remote.model.NetworkProfile
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

/**
 * This class is responsible for fetching the profile data from the remote data source.
 * Note that fetching profile is not the same as fetching user data, since user data (e.g. email) lived in auth.users
 * If you have need to fetch for email, please use the users view, which joined public.profiles and auth.users
 * It uses the Supabase client to make the network requests.
 *
 * @param client The Supabase client used to make network requests.
 */
class ProfileRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    suspend fun getProfile(id: String): NetworkProfile? {
        val columns = Columns.list(NetworkProfile.Columns.ALL)

        val req = client.from(NetworkProfile.TABLE_NAME).select(columns = columns) {
            filter {
                eq(NetworkProfile.Columns.ID, id)
            }
        }
        val res = req.decodeSingleOrNull<NetworkProfile>()
        return res
    }
}