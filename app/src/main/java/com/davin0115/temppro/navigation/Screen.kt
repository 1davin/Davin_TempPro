package com.davin0115.temppro.navigation

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object History: Screen("historyScreen")
}