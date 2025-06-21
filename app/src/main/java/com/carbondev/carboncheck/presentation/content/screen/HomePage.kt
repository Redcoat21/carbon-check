package com.carbondev.carboncheck.presentation.content.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carbondev.carboncheck.R
import androidx.compose.foundation.lazy.items

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    val sampleActivities = listOf(
        Activity(Icons.Default.Home, "Car ride", "8:00 pm • 20 minutes", 6f),
        Activity(Icons.Default.Home, "Walk", "5:30 pm • 15 minutes", 0f),
        Activity(Icons.Default.Home, "Car ride", "12:00 pm • 10 minutes", 4f),
        Activity(Icons.Default.Home, "Bike ride", "9:00 am • 30 minutes", 1.2f),
        Activity(Icons.Default.Home, "Flight", "6:00 am • 2 hours", 150f)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Row (
//            modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//        ) {
//            Column {
//                Text(
//                    text = "Hi John Doe, (msh manual)",
//                    fontSize = 26.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = "Track your carbon footprint",
//                    color = MaterialTheme.colorScheme.primary,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
//        }
        Welcoming()

        TodayImpactCard(
            co2Value = 4.2f,
            dailyTarget = 5.6f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

//        WeeklyProgressHeader(modifier = Modifier.weight(1f))
        RecentActivitiesList(sampleActivities)
    }
}

@Composable
fun TodayImpactCard(
    co2Value: Float = 4.2f,
    dailyTarget: Float = 5.6f,
    modifier: Modifier = Modifier
) {
    val progress = (co2Value / dailyTarget).coerceIn(0f, 1f)

    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Today's Impact", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "%.1f kg".format(co2Value),
                style = MaterialTheme.typography.displaySmall.copy(color = Color(0xFF2E7D32))
            )
            Text("CO₂ emissions", style = MaterialTheme.typography.bodySmall.copy(Color.Gray))
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color(0xFFE0E0E0)
            )
            Text(
                text = "${((1 - progress) * 100).toInt()}% below daily target",
                style = MaterialTheme.typography.bodySmall.copy(Color.Gray)
            )
        }
    }
}

@Composable
fun Welcoming(
    modifier: Modifier = Modifier,
    userName: String = "Vivek",
    currentKg: Float = 120f,
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
                    style = MaterialTheme.typography.headlineSmall
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

@Composable
fun WeeklyProgressHeader(
    modifier: Modifier = Modifier,
    userName: String = "Vivek",
    currentKg: Float = 120f,
    weeklyTargetKg: Float = 150f
) {
    val progress = (currentKg / weeklyTargetKg).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.size(180.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 12.dp
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${currentKg.toInt()} kg",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "So far this week",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

//=====================================

data class Activity(val icon: ImageVector, val title: String, val time: String, val co2: Float)

@Composable
fun ActivityRow(item: Activity, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = null, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text(text = "${item.co2.toInt()} kg CO₂", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun RecentActivitiesList(activities: List<Activity>) {
    LazyColumn (
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
                Text("Recent activities", style = MaterialTheme.typography.titleMedium)
                Icon(Icons.Default.AddCircle, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
            }
        }
        items(activities) { activity ->
            ActivityRow(activity)
        }
    }
}

//====================================================


@Preview(showBackground = true, name = "HomePage Preview")
@Composable
fun HomePagePreview() {
    HomePage()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "HomePage Dark")
@Composable
fun HomePagePreviewDark() {
    HomePage()
}