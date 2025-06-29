    package com.carbondev.carboncheck.presentation.content.screen

    import android.content.res.Configuration
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.material3.Button
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import coil3.compose.AsyncImage
    import androidx.compose.ui.platform.LocalUriHandler
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    import com.carbondev.carboncheck.R
    import com.carbondev.carboncheck.presentation.ui.theme.CarbonCheckTheme
    import com.carbondev.carboncheck.presentation.ui.theme.Typography

    @Composable
    fun AboutPage(navController: NavHostController) {
        AboutContent(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    @Composable
    fun AboutContent(
        modifier: Modifier = Modifier,
        onBackClick: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "About",
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(28.dp)
                        .clickable { onBackClick() }
                )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "CarbonCheck is a mobile app designed to help you understand and reduce your carbon footprint in your daily life. Whether you're commuting, shopping, or managing your household, CarbonCheck makes it easy to monitor the environmental impact of your actions.\n" +
                            "\n" +
                            "\uD83D\uDE80 Key Features:\n" +
                            "\uD83D\uDCCA Daily Emission Tracking – Log your activities and instantly see your carbon output.\n" +
                            "\uD83D\uDCC8 Progress Insights – View your carbon stats over the week, month, or year.\n" +
                            "\uD83C\uDF81 Rewards System – Earn points based on your progress and redeem them for vouchers.\n" +
                            "\n" +
                            "Start making smarter, greener decisions today — one action at a time.\n" +
                            "Because every step counts toward a cleaner planet. \uD83C\uDF0E",
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Follow us on:",
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val uriHandler = LocalUriHandler.current
                    AsyncImage(
                        model = "https://upload.wikimedia.org/wikipedia/commons/a/a5/Instagram_icon.png",
                        contentDescription = "Instagram",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { uriHandler.openUri("https://instagram.com") }
                    )

                    AsyncImage(
                        model = "https://upload.wikimedia.org/wikipedia/commons/b/b7/X_logo.jpg",
                        contentDescription = "X (Twitter)",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { uriHandler.openUri("https://x.com") }
                    )

                    AsyncImage(
                        model = "https://upload.wikimedia.org/wikipedia/commons/6/6c/Facebook_Logo_2023.png",
                        contentDescription = "Facebook",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { uriHandler.openUri("https://facebook.com") }
                    )
                }
    }
}

    @Preview(
        name = "Light mode - Portrait",
        showBackground = true
    )
    @Composable
    fun AboutPagePreviewLight() {
        CarbonCheckTheme {
            AboutContent()
        }
    }

    @Preview(
        name = "Dark mode - Portrait",
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES
    )
    @Composable
    fun AboutPagePreviewDark() {
        CarbonCheckTheme {
            AboutContent()
        }
    }

    @Preview(
        name = "Light mode - Landscape",
        showBackground = true,
        device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
    )
    @Composable
    fun AboutPagePreviewLandscapeLight() {
        CarbonCheckTheme {
            AboutContent()
        }
    }

    @Preview(
        name = "Dark mode - Landscape",
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        device = "spec:width=1280dp,height=800dp,dpi=480,orientation=landscape"
    )
    @Composable
    fun AboutPagePreviewLandscapeDark() {
        CarbonCheckTheme {
            AboutContent()
        }
    }