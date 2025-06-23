package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.carbondev.carboncheck.R
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeUiState
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Collect the UiState wrapper from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // A Box to center content for Loading and Error states
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Use a 'when' block to handle the different states
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success<*> -> {
                // Cast the data to the expected HomeUiState type
                val homeData = state.data as? HomeUiState
                if (homeData != null) {
                    // On success, show the main content
                    HomePageContent(
                        homeData = homeData,
                        navController = navController
                    )
                } else {
                    // This case handles a type mismatch, which would be an unexpected error
                    ErrorText("An unexpected error occurred.")
                }
            }
            is UiState.Error -> {
                // On error, show the error message
                ErrorText(message = state.message)
            }
            is UiState.Empty -> {
                // Show an empty state message
                ErrorText(message = "No data available. Please try again later.")
            }
        }
    }
}

/**
 * This composable contains the actual UI for the home screen when data is successfully loaded.
 * Extracting it keeps the state-handling logic in HomePage clean.
 */
@Composable
private fun HomePageContent(
    homeData: HomeUiState,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Welcoming(userName = homeData.userName)

        TodayImpactCard(
            carbonData = homeData.todaysCo2,
            dailyTarget = homeData.dailyTarget,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecentActivitiesList(
            activities = homeData.recentActivities,
            onAddClick = { navController.navigate(Routes.Add.route) }
        )
    }
}

/**
 * A simple composable for displaying centered error or empty state text.
 */
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

// Welcoming composable remains the same
@Composable
fun Welcoming(
    modifier: Modifier = Modifier,
    userName: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Hi $userName,",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Track Your Carbon Footprint!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Image(
                painter = painterResource(id = R.drawable.eco),
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Inside
            )
        }
    }
}

// TodayImpactCard composable remains the same
@Composable
fun TodayImpactCard(
    carbonData: CarbonData,
    dailyTarget: Float,
    modifier: Modifier = Modifier
) {
    val progress = if (dailyTarget > 0) (carbonData.kilogram.toFloat() / dailyTarget).coerceIn(0f, 1f) else 0f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Today's Impact", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "%.1f kg".format(carbonData.kilogram),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text("CO₂ emissions", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Text(
                text = "${((1 - progress) * 100).toInt()}% below daily target",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}

// ActivityRow composable remains the same
@Composable
fun ActivityRow(item: Activity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
            Text(text = item.time, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(text = "%.1f kg CO₂".format(item.carbon.kilogram), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

// RecentActivitiesList composable remains the same
@Composable
fun RecentActivitiesList(
    activities: List<Activity>,
    onAddClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent activities", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                IconButton(onClick = onAddClick) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Activity",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        items(activities) { activity ->
            ActivityRow(activity)
        }
    }
}


//================ PREVIEWS ================

fun createPreviewCarbonData(kg: Double) = CarbonData(kg * 1000, kg, kg * 2.20462, kg / 1000)

// The preview state no longer needs the `isLoading` flag
val previewState = HomeUiState(
    userName = "Vivek",
    todaysCo2 = createPreviewCarbonData(4.2),
    dailyTarget = 5.6f,
    recentActivities = listOf(
        Activity(Icons.Default.Home, "Car ride", "8:00 pm • 20 minutes", createPreviewCarbonData(6.0)),
        Activity(Icons.Default.Home, "Walk", "5:30 pm • 15 minutes", createPreviewCarbonData(0.0)),
        Activity(Icons.Default.Home, "Car ride", "12:00 pm • 10 minutes", createPreviewCarbonData(4.0))
    )
)

@Preview(showBackground = true, name = "HomePage Preview Light")
@Composable
fun HomePagePreviewLight() {
    MaterialTheme {
        Surface {
            // Preview now calls HomePageContent directly with the sample data
            HomePageContent(
                homeData = previewState,
                navController = NavHostController(androidx.compose.ui.platform.LocalContext.current)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "HomePage Preview Dark")
@Composable
fun HomePagePreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface {
            // Preview now calls HomePageContent directly with the sample data
            HomePageContent(
                homeData = previewState,
                navController = NavHostController(androidx.compose.ui.platform.LocalContext.current)
            )
        }
    }
}