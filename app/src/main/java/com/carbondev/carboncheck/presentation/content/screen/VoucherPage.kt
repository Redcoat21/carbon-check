package com.carbondev.carboncheck.presentation.content.screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VoucherPage(
    modifier: Modifier = Modifier,
    currentPoints: Int = 120 // You can pass this from your ViewModel later
) {
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
        ) {
            items(sampleVouchers) { voucher ->
                VoucherCard(
                    name = voucher.name,
                    vendor = voucher.vendor,
                    amount = voucher.amount,
                    pointsRequired = voucher.pointsRequired,
                    currentPoints = currentPoints,
                    onClaimClick = { println("${voucher.name} claimed!") }
                )
            }
        }

    }
}

data class Voucher(
    val name: String,
    val vendor: String,
    val amount: Int,
    val pointsRequired: Int
)

val sampleVouchers = listOf(
    Voucher("40% Food Coupon", "McDonald's", 3, 100),
    Voucher("Free Coffee", "Starbucks", 0, 50),
    Voucher("Buy 1 Get 1", "Starbucks", 5, 200),
    Voucher("Free 2 Ice Cream", "Mixue", 0, 150),
    Voucher("Free Fries", "KFC", 10, 80),
    Voucher("Discount Pizza", "Pizza Hut", 2, 120),
)


@Composable
fun VoucherCard(
    name: String,
    vendor: String,
    amount: Int,
    pointsRequired: Int,
    currentPoints: Int,
    onClaimClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(2.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Text(text = "Vendor: $vendor", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Remaining: $amount", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Points Required: $pointsRequired", style = MaterialTheme.typography.bodyMedium)

        Button(
            onClick = onClaimClick,
            modifier = Modifier.align(Alignment.End),
            enabled = amount > 0 && currentPoints >= pointsRequired
        ) {
            Text("Claim")
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
            onClaimClick = {}
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