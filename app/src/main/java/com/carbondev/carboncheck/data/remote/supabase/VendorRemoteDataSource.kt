package com.carbondev.carboncheck.data.remote.supabase

import com.carbondev.carboncheck.data.remote.model.NetworkVendor
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import jakarta.inject.Inject

class VendorRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    suspend fun getVendor(id: String): NetworkVendor? {
        val columns = Columns.list(NetworkVendor.Columns.ALL)
        val req = client.from(NetworkVendor.TABLE_NAME).select(columns = columns) {
            filter {
                and {
                    eq(NetworkVendor.Columns.ID, id)
                    exact(NetworkVendor.Columns.DELETED_AT, null)
                }
            }
        }
        val res = req.decodeSingleOrNull<NetworkVendor>()
        return res
    }
}