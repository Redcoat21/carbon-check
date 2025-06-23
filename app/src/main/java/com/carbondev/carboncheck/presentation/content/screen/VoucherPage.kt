package com.carbondev.carboncheck.presentation.content.screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carbondev.carboncheck.domain.model.Vendor
import com.carbondev.carboncheck.domain.model.Voucher
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.content.viewmodel.VoucherViewModel

@Composable
fun VoucherPage(
    modifier: Modifier = Modifier,
    currentPoints: Int = 120, // You can pass this from your ViewModel later
    viewModel: VoucherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Your Points: $currentPoints",
                style = MaterialTheme.typography.titleMedium
            )
        }

        when (val state = uiState) {
            is UiState.Loading -> {
                Text("Loading vouchers...")
            }

            is UiState.Error -> {
                Text("Error: ${state.message}")
            }

            is UiState.Success<*> -> {
                val voucherList = (state.data as? List<Voucher>).orEmpty()
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                ) {
                    items(voucherList) { voucher ->
                        VoucherCard(
                            name = voucher.name,
                            vendor = voucher.vendor.name,
                            amount = voucher.amount,
                            pointsRequired = voucher.points,
                            currentPoints = currentPoints,
                        )
                    }
                }
            }

            is UiState.Empty -> {
                Text("No vouchers available.")
            }
        }
    }
}

val sampleVouchers = listOf(
    Voucher("0", Vendor("0","McDonald's"), "40% Food Coupon", 3, 100),
    Voucher("1", Vendor("1","Starbuck"),"Free Coffee", 0, 50),
    Voucher("2", Vendor("1","Starbuck"), "Buy 1 Get 1", 5, 200),
    Voucher("3", Vendor("2", "Mixue"), "Free 2 Ice Cream", 0, 150),
    Voucher("4", Vendor("3", "KFC"), "Free Fries", 10, 80),
    Voucher("5", Vendor("4", "Pizza Hut"), "Discount Pizza", 2, 120),
)


@Composable
fun VoucherCard(
    name: String,
    vendor: String,
    amount: Int,
    pointsRequired: Int,
    currentPoints: Int,
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Text(text = "Vendor: $vendor", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Remaining: $amount", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Points Required: $pointsRequired", style = MaterialTheme.typography.bodyMedium)

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.End),
            enabled = amount > 0 && currentPoints >= pointsRequired
        ) {
            Text("Claim")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Voucher Claimed!") },
                text = {
                    Text("You have successfully claimed $name by $vendor. Check your vouchers for details in Profile.")
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherCardPreview() {
    MaterialTheme {
        VoucherCard(
            name = "40% Food Coupon",
            vendor = "McDonald's",
            amount = 3,
            pointsRequired = 40,
            currentPoints = 50,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VoucherPagePreview() {
    MaterialTheme {
        VoucherPage()
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun VoucherPagePreviewDark() {
    MaterialTheme {
        VoucherPage()
    }
}