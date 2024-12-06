package com.example.neurontask.presentation.nav

import android.app.Activity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neurontask.presentation.main.StartScreen
import com.example.neurontask.presentation.purchase.PurchaseScreen
import com.example.neurontask.presentation.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen{

    @Serializable
    data object Start: Screen()

    @Serializable
    data object Registration: Screen()

    @Serializable
    data object Purchases: Screen()
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Start) {
        composable<Screen.Start> {
            StartScreen(
                viewModel = hiltViewModel(),
                onNavigateToRegistration = { navController.navigate(Screen.Registration) },
                onNavigateToPurchases = { navController.navigate(Screen.Purchases) },
            )
        }
        composable<Screen.Registration> {
            RegistrationScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Purchases> {
            PurchaseScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
