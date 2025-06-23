package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.carbondev.carboncheck.domain.common.ActivityType
import com.carbondev.carboncheck.domain.model.Activity
import com.carbondev.carboncheck.domain.model.CarbonData
import com.carbondev.carboncheck.presentation.Routes
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeUiState
import com.carbondev.carboncheck.presentation.content.viewmodel.HomeViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success<*> -> {
                val homeData = state.data as? HomeUiState
                if (homeData != null) {
                    HomePageContent(
                        homeData = homeData,
                        onAddClick = { navController.navigate(Routes.Add.route) }
                    )
                } else {
                    ErrorText("An unexpected error occurred.")
                }
            }
            is UiState.Error -> ErrorText(message = state.message)
            is UiState.Empty -> ErrorText(message = "No data available. Please try again later.")
        }
    }
}

@Composable
private fun HomePageContent(
    homeData: HomeUiState,
    onAddClick: () -> Unit,
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
            onAddClick = onAddClick
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

// Helper function to map activity type string to a Material Icon
@Composable
private fun mapActivityTypeToIcon(type: ActivityType): ImageVector {
    return when (type) {
        ActivityType.FLIGHT -> Icons.Default.Flight
        ActivityType.FOOD -> Icons.Default.Fastfood
        ActivityType.TRANSPORT -> Icons.Default.DirectionsCar
        else -> Icons.Default.CheckBoxOutlineBlank
    }
}

// Helper function to create a descriptive title from activity data
private fun getActivityTitle(activity: Activity): String {
    return when (activity.type) {
        ActivityType.FLIGHT -> "Flight to ${activity.flightDestination ?: "destination"}"
        ActivityType.FOOD -> activity.foodType?.replaceFirstChar { it.uppercase() } ?: "Meal"
        ActivityType.TRANSPORT -> activity.vehicleType?.replaceFirstChar { it.uppercase() } ?: "Travel"
        else -> "General Activity"
    }
}

// Helper function to format the Instant into a human-readable string
private fun formatActivityDateTime(instant: Instant): String {
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern("MMM dd, hh:mm a")
    return localDateTime.toJavaLocalDateTime().format(formatter)
}

@Composable
fun ActivityRow(item: Activity, modifier: Modifier = Modifier) {
    val icon = mapActivityTypeToIcon(item.type)
    val title = getActivityTitle(item)
    val formattedTime = formatActivityDateTime(item.datetime)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
            Text(text = formattedTime, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(text = "%.1f kg CO₂".format(item.carbon.kilogram), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
    }
}

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
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add Activity", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        items(activities) { activity ->
            ActivityRow(activity)
        }
    }
}

// --- PREVIEWS UPDATED TO USE THE NEW DOMAIN MODEL ---

/**
 * Helper function to create CarbonData for previews. This resolves the
 * 'Unresolved reference' error by providing a concrete implementation.
 */
private fun createPreviewCarbonData(kilogram: Double): CarbonData {
    return CarbonData(gram = (kilogram * 1000))
}

@Preview(showBackground = true, name = "HomePage Preview Light")
@Composable
fun HomePagePreviewLight() {
    val previewActivities = listOf(
        Activity(id="1", userId="", type=ActivityType.TRANSPORT, datetime=Instant.parse("2023-10-27T20:00:00Z"), carbon=createPreviewCarbonData(6.0), flightDestination=null, flightDeparture=null, people=null, distance=null, foodType=null, weightInGrams=null, vehicleType="Car"),
        Activity(id="2", userId="", type=ActivityType.FOOD, datetime=Instant.parse("2023-10-27T12:30:00Z"), carbon=createPreviewCarbonData(1.2), flightDestination=null, flightDeparture=null, people=null, distance=null, foodType="Chicken", weightInGrams=200, vehicleType=null),
        Activity(id="3", userId="", type=ActivityType.FLIGHT, datetime=Instant.parse("2023-10-26T09:00:00Z"), carbon=createPreviewCarbonData(150.0), flightDestination="Bali", flightDeparture="Jakarta", people=1, distance=null, foodType=null, weightInGrams=null, vehicleType=null)
    )
    val previewState = HomeUiState(
        userName = "Vivek",
        todaysCo2 = createPreviewCarbonData(7.2),
        dailyTarget = 5.6f,
        recentActivities = previewActivities
    )
    MaterialTheme {
        Surface {
            HomePageContent(
                homeData = previewState,
                onAddClick = {} // Pass an empty lambda for the preview
            )
        }
    }
}
