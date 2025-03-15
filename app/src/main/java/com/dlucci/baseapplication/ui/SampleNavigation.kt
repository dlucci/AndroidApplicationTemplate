package com.dlucci.baseapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun SampleNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Route.Home.name) {
        composable(Route.Home.name) {
            SampleScreen(navController)
        }
    }



}


sealed class Route(val name: String) {
    object Home : Route("Home")
}