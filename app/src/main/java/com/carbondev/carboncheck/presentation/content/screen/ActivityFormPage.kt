package com.carbondev.carboncheck.presentation.content.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.carbondev.carboncheck.presentation.common.UiState
import com.carbondev.carboncheck.presentation.content.viewmodel.ActivityFormViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ActivityFormPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ActivityFormViewModel = hiltViewModel()
) {
    val tabs = listOf("Car", "Flight", "Food")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    // Listen for side effects from the ViewModel
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is UiState.Success<*> -> {
                // On successful save, navigate back
                navController.popBackStack()
            }
            is UiState.Error -> {
                // On error, show a snackbar
                snackbarHostState.showSnackbar(message = state.message)
            }
            else -> {
                // Handle Loading, Empty if needed
            }
        }
    }

    // Car states
    var distanceKm by remember { mutableStateOf("") }
    var selectedCarType by remember { mutableStateOf("Petrol") }
    var carTypeExpanded by remember { mutableStateOf(false) }

    // Flight states
    var departureCode by remember { mutableStateOf("") }
    var arrivalCode by remember { mutableStateOf("") }
    var peopleCount by remember { mutableStateOf("1") }

    // Food states
    var foodWeightGram by remember { mutableStateOf("") }
    var selectedFoodType by remember { mutableStateOf("Chicken") }
    var foodTypeExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Add Activity") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val isDarkTheme = isSystemInDarkTheme()
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                text = {
                                    Text(
                                        text = title,
                                        color = if (isDarkTheme) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        when (page) {
                            0 -> CarActivityForm(
                                distanceKm = distanceKm,
                                onDistanceChange = { distanceKm = it },
                                selectedCarType = selectedCarType,
                                onCarTypeChange = { selectedCarType = it },
                                carTypeExpanded = carTypeExpanded,
                                onCarTypeExpandedChange = { carTypeExpanded = it },
                                onSave = {
                                    // Call the ViewModel to save the activity
                                    viewModel.saveCarActivity(distanceKm, selectedCarType)
                                }
                            )

                            1 -> FlightActivityForm(
                                departureCode = departureCode,
                                onDepartureChange = { departureCode = it },
                                arrivalCode = arrivalCode,
                                onArrivalChange = { arrivalCode = it },
                                peopleCount = peopleCount,
                                onPeopleCountChange = { peopleCount = it },
                                onSave = {
                                    viewModel.saveFlightActivity(departureCode, arrivalCode, peopleCount)
                                }
                            )

                            2 -> FoodActivityForm(
                                weightGram = foodWeightGram,
                                onWeightChange = { foodWeightGram = it },
                                selectedFoodType = selectedFoodType,
                                onFoodTypeChange = { selectedFoodType = it },
                                foodTypeExpanded = foodTypeExpanded,
                                onFoodTypeExpandedChange = { foodTypeExpanded = it },
                                onSave = {
                                    viewModel.saveFoodActivity(foodWeightGram, selectedFoodType)
                                }
                            )
                        }
                    }
                }
                // Show a loading indicator overlay when saving
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarActivityForm(
    distanceKm: String,
    onDistanceChange: (String) -> Unit,
    selectedCarType: String,
    onCarTypeChange: (String) -> Unit,
    carTypeExpanded: Boolean,
    onCarTypeExpandedChange: (Boolean) -> Unit,
    onSave: () -> Unit
) {
    val carTypes = listOf("Petrol", "Diesel", "Electric")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Log Car Ride", modifier = Modifier.padding(top = 16.dp), style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = distanceKm,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) onDistanceChange(it) },
                label = { Text("Distance (km)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            )

            ExposedDropdownMenuBox(
                expanded = carTypeExpanded,
                // FIX: Directly pass the new state from the lambda
                onExpandedChange = onCarTypeExpandedChange
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedCarType,
                    onValueChange = {},
                    label = { Text("Car Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = carTypeExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(top = 4.dp, bottom = 4.dp)
                )

                ExposedDropdownMenu(
                    expanded = carTypeExpanded,
                    onDismissRequest = { onCarTypeExpandedChange(false) }
                ) {
                    carTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                onCarTypeChange(type)
                                onCarTypeExpandedChange(false)
                            }
                        )
                    }
                }
            }
        }
        Button(
            onClick = onSave,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            enabled = distanceKm.isNotBlank()
        ) {
            Text("Save")
        }
    }
}

@Composable
fun FlightActivityForm(
    departureCode: String,
    onDepartureChange: (String) -> Unit,
    arrivalCode: String,
    onArrivalChange: (String) -> Unit,
    peopleCount: String,
    onPeopleCountChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Log Flight", modifier = Modifier.padding(top = 16.dp), style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = departureCode,
                onValueChange = { if (it.length <= 3) onDepartureChange(it.lowercase()) },
                label = { Text("Departure Airport (IATA Code)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            )

            OutlinedTextField(
                value = arrivalCode,
                onValueChange = { if (it.length <= 3) onArrivalChange(it.lowercase()) },
                label = { Text("Arrival Airport (IATA Code)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
            )

            OutlinedTextField(
                value = peopleCount,
                onValueChange = { if (it.all { c -> c.isDigit() }) onPeopleCountChange(it) },
                label = { Text("Number of People") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
            )
        }
        Button(
            onClick = onSave,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            enabled = departureCode.length == 3 && arrivalCode.length == 3 && peopleCount.isNotBlank()
        ) {
            Text("Save")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodActivityForm(
    weightGram: String,
    onWeightChange: (String) -> Unit,
    selectedFoodType: String,
    onFoodTypeChange: (String) -> Unit,
    foodTypeExpanded: Boolean,
    onFoodTypeExpandedChange: (Boolean) -> Unit,
    onSave: () -> Unit
) {
    val foodTypes = listOf("Chicken", "Beef")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Log Food Consumption",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = weightGram,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() || it == '.' }) onWeightChange(newValue)
                },
                label = { Text("Weight (grams)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 4.dp)
            )

            ExposedDropdownMenuBox(
                expanded = foodTypeExpanded,
                // FIX: Directly pass the new state from the lambda
                onExpandedChange = onFoodTypeExpandedChange
            ) {
                OutlinedTextField(
                    value = selectedFoodType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Food Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = foodTypeExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                )
                ExposedDropdownMenu(
                    expanded = foodTypeExpanded,
                    onDismissRequest = { onFoodTypeExpandedChange(false) }
                ) {
                    foodTypes.forEach { foodType ->
                        DropdownMenuItem(
                            text = { Text(foodType) },
                            onClick = {
                                onFoodTypeChange(foodType)
                                onFoodTypeExpandedChange(false)
                            }
                        )
                    }
                }
            }
        }
        Button(
            onClick = onSave,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            enabled = weightGram.isNotBlank() && selectedFoodType.isNotBlank()
        ) {
            Text("Save")
        }
    }
}

// Previews remain the same as they don't need the ViewModel

@Preview(showBackground = true)
@Composable
fun ActivityTabsPreview() {
    MaterialTheme {
        ActivityFormPage(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Car Activity Form Preview")
@Composable
fun CarActivityFormPreview() {
    MaterialTheme {
        CarActivityForm(
            distanceKm = "12.5",
            onDistanceChange = {},
            selectedCarType = "Electric",
            onCarTypeChange = {},
            carTypeExpanded = false,
            onCarTypeExpandedChange = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true, name = "Flight Activity Form Preview")
@Composable
fun FlightActivityFormPreview() {
    MaterialTheme {
        FlightActivityForm(
            departureCode = "CGK",
            onDepartureChange = {},
            arrivalCode = "DPS",
            onArrivalChange = {},
            peopleCount = "2",
            onPeopleCountChange = {},
            onSave = {}
        )
    }
}

@Preview(showBackground = true, name = "Food Activity Form Preview")
@Composable
fun FoodActivityFormPreview() {
    MaterialTheme {
        FoodActivityForm(
            weightGram = "250",
            onWeightChange = {},
            selectedFoodType = "Chicken",
            onFoodTypeChange = {},
            foodTypeExpanded = false,
            onFoodTypeExpandedChange = {},
            onSave = {}
        )
    }
}
