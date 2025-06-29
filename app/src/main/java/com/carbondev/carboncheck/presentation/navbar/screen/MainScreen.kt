package com.carbondev.carboncheck.presentation.navbar.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.carbondev.carboncheck.presentation.auth.viewmodel.LoginViewModel
import com.carbondev.carboncheck.presentation.content.screen.HomePage
import com.carbondev.carboncheck.presentation.content.screen.ProfilePage
import com.carbondev.carboncheck.presentation.content.screen.SettingsPage
import com.carbondev.carboncheck.presentation.content.screen.SettingsPageContent
import com.carbondev.carboncheck.presentation.content.screen.StatsPage
import com.carbondev.carboncheck.presentation.content.screen.VoucherPage
import com.carbondev.carboncheck.presentation.navbar.NavItem

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Stats", Icons.Default.DateRange),
        NavItem("Profile", Icons.Default.Person),
        NavItem("Voucher", Icons.Default.ShoppingCart),
        NavItem("Settings", Icons.Default.Settings),

    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                navItemList.forEachIndexed {index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = "Icon",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        label = {
                            Text(
                                text = navItem.label,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController // now passed correctly
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavHostController
) {
    when(selectedIndex){
        0 -> HomePage(navController = navController, modifier = modifier)
        1 -> StatsPage()
        2 -> ProfilePage()
        3 -> VoucherPage()
        4 -> SettingsPage(navController = navController)
    }
}