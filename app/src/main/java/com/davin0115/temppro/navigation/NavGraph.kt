package com.davin0115.temppro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davin0115.temppro.screen.HistoryScreen
import com.davin0115.temppro.screen.MainScreen

@Composable
fun SetupNavGraph( navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.History.route) {
            HistoryScreen(navController)
        }
    }
}