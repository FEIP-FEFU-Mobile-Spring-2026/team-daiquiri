package fefu.storeProject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fefu.storeProject.data.db.AppDatabase
import fefu.storeProject.ui.components.BottomBar
import fefu.storeProject.ui.screens.CartScreen
import fefu.storeProject.ui.screens.MainScreen
import fefu.storeProject.ui.theme.StoreProjectTheme
import fefu.storeProject.viewmodel.CartViewModel
import fefu.storeProject.viewmodel.CatalogViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoreProjectTheme {
                val navController = rememberNavController()
                val cartDao = AppDatabase.getInstance(applicationContext).cartDao()
                val cartViewModel: CartViewModel = viewModel(
                    factory = CartViewModel.factory(cartDao)
                )
                val catalogViewModel: CatalogViewModel = viewModel()

                val allProducts by catalogViewModel.allProducts.collectAsState()
                LaunchedEffect(allProducts) {
                    if (allProducts.isNotEmpty()) {
                        cartViewModel.loadFromDb(allProducts)
                    }
                }

                Scaffold(
                    bottomBar = { BottomBar(navController = navController, cartViewModel = cartViewModel) }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(padding),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                    ) {
                        composable("main") { MainScreen(navController, cartViewModel, catalogViewModel) }
                        composable("cart") { CartScreen(cartViewModel, navController) }
                    }
                }
            }
        }
    }
}
