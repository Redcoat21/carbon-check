package com.carbondev.carboncheck.presentation.content.viewmodel

import androidx.lifecycle.viewModelScope
import com.carbondev.carboncheck.domain.common.Result
import com.carbondev.carboncheck.domain.usecase.voucher.GetVouchersUseCase
import com.carbondev.carboncheck.presentation.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(
    private val getVouchersUseCase: GetVouchersUseCase
) : BaseViewModel() {

    init {
        loadVouchers()
    }

    fun loadVouchers() = viewModelScope.launch {
        setLoading()
        when (val result = getVouchersUseCase()) {
            is Result.Success -> {
                val vouchers = result.data
                if (vouchers.isEmpty()) {
                    setEmpty()
                } else {
                    setSuccess(vouchers)
                }
            }
            is Result.Error -> {
                setError(result.message ?: "Failed to load vouchers")
            }
        }
    }
}
