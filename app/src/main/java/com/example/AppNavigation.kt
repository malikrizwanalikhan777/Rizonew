package com.example

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = "home") {
    composable("home") {
      HomeScreen { category ->
        navController.navigate("links/$category")
      }
    }
    composable("links/{category}") { backStackEntry ->
      val category = backStackEntry.arguments?.getString("category") ?: ""
      LinksScreen(category) { link ->
        navController.navigate("player/$link")
      }
    }
    composable("player/{link}") { backStackEntry ->
      val link = backStackEntry.arguments?.getString("link") ?: ""
      PlayerScreen(link)
    }
  }
}
