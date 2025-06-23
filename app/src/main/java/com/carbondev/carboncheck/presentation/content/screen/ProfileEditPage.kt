package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
import com.carbondev.carboncheck.presentation.ui.theme.Typography

@Composable
fun ProfileEditPage(navController: NavHostController) {
    ProfileEditContent(
        onBackClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun ProfileEditContent(modifier: Modifier = Modifier, onBackClick: () -> Unit = {}) {
    var firstName by remember { mutableStateOf(TextFieldValue("John")) }
    var lastName by remember { mutableStateOf(TextFieldValue("Doe")) }
    var avatarUrl by remember { mutableStateOf(TextFieldValue("https://i.pravatar.cc/150?img=5")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Edit Profile",
                style = Typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.size(48.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(avatarUrl.text),
                    contentDescription = "Avatar Preview",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .testTag("AvatarImage"),
                    contentScale = ContentScale.Crop
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("FirstNameInput")
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("LastNameInput")
                )

                OutlinedTextField(
                    value = avatarUrl,
                    onValueChange = { avatarUrl = it },
                    label = { Text("Avatar URL") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("AvatarUrlInput")
                )

                Button(
                    onClick = {
                        println("Save clicked: $firstName $lastName $avatarUrl")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("SaveButton")
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
@Preview(name = "Light Mode - Portrait", showBackground = true)
@Composable
fun ProfileEditLightPreview() {
    CarbonCheckTheme {
        ProfileEditContent()
    }
}

@Preview(name = "Dark Mode - Portrait", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileEditDarkPreview() {
    CarbonCheckTheme {
        ProfileEditContent()
    }
}

@Preview(
    name = "Light Mode - Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun ProfileEditLandscapeLightPreview() {
    CarbonCheckTheme {
        ProfileEditContent()
    }
}

@Preview(
    name = "Dark Mode - Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun ProfileEditLandscapeDarkPreview() {
    CarbonCheckTheme {
        ProfileEditContent()
    }
}