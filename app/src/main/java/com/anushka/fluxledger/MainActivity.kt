package com.anushka.fluxledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anushka.fluxledger.presentation.ui.screens.AddTransactionScreen
import com.anushka.fluxledger.presentation.ui.screens.TransactionListScreen
import com.anushka.fluxledger.presentation.ui.theme.FluxLedgerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FluxLedgerTheme {
                FluxLedgerNavGraph()
            }
        }
    }
}

@Composable
fun FluxLedgerNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            TransactionListScreen(
                onAddClick = { navController.navigate("add") },
                onTransactionClick = { id -> navController.navigate("edit/$id") }
            )
        }
        composable("add") {
            AddTransactionScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "edit/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
        ) { backStackEntry ->
            AddTransactionScreen(
                onBack = { navController.popBackStack() },
                transactionId = backStackEntry.arguments?.getString("transactionId")
            )
        }
    }
}
