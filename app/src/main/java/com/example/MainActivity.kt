package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.PlaceholderToolScreen
import com.example.ui.screens.RecentFilesScreen
import com.example.ui.screens.ScannerScreen
import com.example.ui.theme.Theme
import com.example.ui.viewmodels.DocumentViewModel
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                PrintSahyogiApp()
            }
        }
    }
}

@Composable
fun PrintSahyogiApp() {
    val navController = rememberNavController()
    val documentViewModel: DocumentViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") {
                DashboardScreen(
                    viewModel = documentViewModel,
                    onNavigateToTool = { route ->
                        navController.navigate(route)
                    }
                )
            }
            composable("recent_docs") {
                RecentFilesScreen(
                    viewModel = documentViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("scanner") {
                ScannerScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("aadhaar_crop") {
                PlaceholderToolScreen("Aadhaar Card Crop Tool", onNavigateBack = { navController.popBackStack() })
            }
            composable("pan_crop") {
                PlaceholderToolScreen("PAN Card Print Tool", onNavigateBack = { navController.popBackStack() })
            }
            composable("passport_photo") {
                PlaceholderToolScreen("Passport Photo Maker", onNavigateBack = { navController.popBackStack() })
            }
            composable("bg_removal") {
                PlaceholderToolScreen("AI Background Remover (LocalML)", onNavigateBack = { navController.popBackStack() })
            }
            composable("bulk_process") {
                PlaceholderToolScreen("Bulk Batch Processor", onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Only show bottom bar on main screens
    val isMainScreen = currentRoute == "dashboard" || currentRoute == "recent_docs"
    
    if (isMainScreen) {
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text(stringResource(R.string.tab_home)) },
                selected = currentRoute == "dashboard",
                onClick = {
                    if (currentRoute != "dashboard") {
                        navController.navigate("dashboard") {
                            popUpTo(0)
                        }
                    }
                }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.History, contentDescription = "Recent Docs") },
                label = { Text(stringResource(R.string.tab_recent)) },
                selected = currentRoute == "recent_docs",
                onClick = {
                    if (currentRoute != "recent_docs") {
                        navController.navigate("recent_docs") {
                            popUpTo(0)
                        }
                    }
                }
            )
        }
    }
}
