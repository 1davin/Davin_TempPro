package com.davin0115.temppro.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davin0115.temppro.screen.HistoryScreen
import com.davin0115.temppro.screen.InfoScreen
import com.davin0115.temppro.screen.MainScreen
import com.davin0115.temppro.viewmodel.TemperatureViewModel

@Composable
fun SetupNavGraph( navController: NavHostController = rememberNavController()) {
    val viewModel: TemperatureViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController, viewModel)
        }
        composable(route = Screen.History.route) {
            HistoryScreen(navController, viewModel)
        }
        composable(route = Screen.Info.route) {
            InfoScreen(navController)
        }
    }
}