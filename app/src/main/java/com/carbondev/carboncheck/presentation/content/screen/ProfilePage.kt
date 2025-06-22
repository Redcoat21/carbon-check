package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import com.carbondev.carboncheck.presentation.ui.theme.Typography

@Composable
fun ProfilePage() {
    ProfileScreenContent()
}

@Composable
fun ProfileScreenContent() {
    // Dummy user data
    val firstName = "John"
    val lastName = "Doe"
    val email = "johndoe@example.com"
    val avatarUrl = "https://i.pravatar.cc/150?img=5"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "$firstName $lastName",
                    style = Typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = email,
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
@Preview(
    name = "Light mode - Portrait",
    showBackground = true
)
@Composable
fun ProfileScreenPreviewLight() {
    CarbonCheckTheme {
        ProfileScreenContent()
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
        ProfileScreenContent()
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
        ProfileScreenContent()
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
        ProfileScreenContent()
    }
}
