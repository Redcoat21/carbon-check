package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme

@Composable
fun SettingsPage(navController: NavHostController) {
    SettingsPageContent(
        onEditProfileClick = {
            println("Edit profile clicked")
            navController.navigate(Routes.ProfileEdit.route)
        },
        onAboutClick = {
            println("About clicked")
            navController.navigate(Routes.About.route)
        },
        onLogoutClick = {
            println("Logout clicked")
            navController.navigate(Routes.Auth.Login.route)
        }
    )
}

@Composable
fun SettingsPageContent(
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val isDarkMode = isSystemInDarkTheme()
    val defaultIconTint = if (isDarkMode) Color.White else Color.Black

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Settings",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        SettingItem(
            iconRes = R.drawable.outline_person_edit_24,
            text = "Edit Profile",
            onClick = onEditProfileClick,
            iconTint = defaultIconTint,
            textColor = MaterialTheme.colorScheme.onSurface
        )
        HorizontalDivider()

        SettingItem(
            iconRes = R.drawable.baseline_info_outline_24,
            text = "About",
            onClick = onAboutClick,
            iconTint = defaultIconTint,
            textColor = MaterialTheme.colorScheme.onSurface
        )
        HorizontalDivider()

        SettingItem(
            iconRes = R.drawable.outline_logout_24_red,
            text = "Logout",
            onClick = onLogoutClick,
            iconTint = Color.Red,
            textColor = Color.Red
        )
        HorizontalDivider()
    }
}

@Composable
fun SettingItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    iconTint: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "$text Icon",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconTint)
        )
        Text(
            text = text,
            fontSize = 20.sp,
            color = textColor
        )
    }
}
@Preview(name = "Light mode - Portrait", showBackground = true)
@Composable
fun SettingsPagePreviewLight() {
    CarbonCheckTheme {
        Surface {
            SettingsPageContent()
        }
    }
}

@Preview(name = "Dark mode - Portrait", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsPagePreviewDark() {
    CarbonCheckTheme {
        Surface {
            SettingsPageContent()
        }
    }
}

@Preview(
    name = "Light mode - Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun SettingsPagePreviewLandscapeLight() {
    CarbonCheckTheme {
        Surface {
            SettingsPageContent()
        }
    }
}

@Preview(
    name = "Dark mode - Landscape",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
)
@Composable
fun SettingsPagePreviewLandscapeDark() {
    CarbonCheckTheme {
        Surface {
            SettingsPageContent()
        }
    }
}
