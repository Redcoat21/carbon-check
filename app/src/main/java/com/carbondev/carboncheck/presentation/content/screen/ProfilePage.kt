package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.content.viewmodel.ProfileUiState
import com.carbondev.carboncheck.presentation.content.viewmodel.ProfileViewModel
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import com.carbondev.carboncheck.presentation.ui.theme.Typography

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success<*> -> {
                val profileData = state.data as? ProfileUiState
                if (profileData != null) {
                    ProfilePageContent(profileData = profileData)
                } else {
                    ErrorText("An unexpected error occurred.")
                }
            }

            is UiState.Error -> {
                ErrorText(message = state.message)
            }

            is UiState.Empty -> {
                ErrorText("No profile data available.")
            }
        }
    }
}

@Composable
fun ProfilePageContent(profileData: ProfileUiState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = if (profileData.avatarUrl.isNotBlank()) profileData.avatarUrl
                            else "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"
                        ),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "${profileData.firstName} ${profileData.lastName}".trim(),
                        style = Typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = profileData.email,
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Text(
                text = "My Vouchers",
                style = Typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }

        //            items(profileData.vouchers) { voucher ->
//                VoucherCardOwned(
//                    name = voucher.name,
//                    vendor = voucher.vendor,
//                    amount = voucher.amount
//                )
//            }

        items(3) {
            VoucherCardOwned(
                name = "halo",
                vendor = "vendor",
                amount = 15
            )
        }
    }
}

@Composable
fun VoucherCardOwned(
    name: String,
    vendor: String,
    amount: Int,
    onUseClick: () -> Unit = {}
) {
    var used by remember { mutableStateOf(false) }

    if (!used) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Vendor: $vendor", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Amount: $amount", style = MaterialTheme.typography.bodyMedium)

                Button(
                    onClick = {
                        used = true
                        onUseClick()
                    },
                ) {
                    Text("Use")
                }
            }
        }
    }
}



@Composable
private fun ErrorText(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(
    name = "Light mode - Portrait",
    showBackground = true
)
@Composable
fun ProfileScreenPreviewLight() {
    CarbonCheckTheme {
        ProfilePageContent(
            profileData = ProfileUiState(
                firstName = "John",
                lastName = "Doe",
                avatarUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                email = "johndoe@example.com"
            )
        )
    }
}

@Preview(
    name = "Dark mode - Portrait",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileScreenPreviewDark() {
    CarbonCheckTheme {
        ProfilePageContent(
            profileData = ProfileUiState(
                firstName = "John",
                lastName = "Doe",
                avatarUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                email = "johndoe@example.com"
            )
        )
    }
}

@Preview(
    name = "Light mode - Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun ProfileScreenPreviewLandscapeLight() {
    CarbonCheckTheme {
        ProfilePageContent(
            profileData = ProfileUiState(
                firstName = "John",
                lastName = "Doe",
                avatarUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                email = "johndoe@example.com"
            )
        )
    }
}

@Preview(
    name = "Dark mode - Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun ProfileScreenPreviewLandscapeDark() {
    CarbonCheckTheme {
        ProfilePageContent(
            profileData = ProfileUiState(
                firstName = "John",
                lastName = "Doe",
                avatarUrl = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                email = "johndoe@example.com"
            )
        )
    }
}
