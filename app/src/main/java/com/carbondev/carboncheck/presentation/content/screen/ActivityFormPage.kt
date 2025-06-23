package com.carbondev.carboncheck.presentation.content.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFormPage(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Car", "Flight", "Food")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Add Activity") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(Modifier.height(8.dp))

                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                when (selectedTabIndex) {
                    0 -> CarActivityForm()
                    1 -> FlightActivityForm()
                    2 -> FoodActivityForm()
                }
            }
        }
    )
}

@Composable
fun CarActivityForm() {
    Text("Car form here", modifier = Modifier.padding(16.dp))
}

@Composable
fun FlightActivityForm() {
    Text("Flight form here", modifier = Modifier.padding(16.dp))
}

@Composable fun FoodActivityForm() {
    Text("Food form here", modifier = Modifier.padding(16.dp))
}

@Preview(showBackground = true)
@Composable fun ActivityTabsPreview() {
    MaterialTheme {
        ActivityFormPage(
            navController = rememberNavController()
        )
    }
}