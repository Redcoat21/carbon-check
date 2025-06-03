package com.carbondev.carboncheck.data.remote.supabase

import com.carbondev.carboncheck.data.remote.model.NetworkVoucher
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class VoucherRemoteDataSource @Inject constructor(private val client: SupabaseClient) {
    suspend fun getVouchersByVendor(vendorId: String): List<NetworkVoucher> {
        val columns = Columns.list(NetworkVoucher.Columns.ALL)
        val req = client.from(NetworkVoucher.TABLE_NAME).select(columns = columns) {
            filter {
                eq(NetworkVoucher.Columns.VENDOR_ID, vendorId)
            }
        }
        val res = req.decodeList<NetworkVoucher>()
        return res
    }

    suspend fun getVouchers(): List<NetworkVoucher> {
        val columns = Columns.list(NetworkVoucher.Columns.ALL)
        val req = client.from(NetworkVoucher.TABLE_NAME).select(columns = columns)
        val res = req.decodeList<NetworkVoucher>()
        return res
    }

    suspend fun getVoucher(id: String): NetworkVoucher? {
        val columns = Columns.list(NetworkVoucher.Columns.ALL)
        val req = client.from(NetworkVoucher.TABLE_NAME).select(columns = columns) {
            filter {
                eq(NetworkVoucher.Columns.ID, id)
            }
        }
        val res = req.decodeSingleOrNull<NetworkVoucher>()
        return res
    }
}