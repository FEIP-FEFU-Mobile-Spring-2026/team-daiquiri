package fefu.storeProject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import fefu.storeProject.data.Category
import fefu.storeProject.data.Product
import fefu.storeProject.data.products
import fefu.storeProject.ui.components.CategoryRow
import fefu.storeProject.ui.components.ProductBottomSheet
import fefu.storeProject.ui.components.ProductList
import fefu.storeProject.viewmodel.CartViewModel

@Composable
fun MainScreen(navController: NavHostController, cartViewModel: CartViewModel) {
    var selectedCategory by remember { mutableStateOf(Category.NEW) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isSheetOpen by remember { mutableStateOf(false) }

    val filteredProducts = products.filter {
        it.categories.contains(selectedCategory)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CategoryRow(selectedCategory) { selectedCategory = it }
        HorizontalDivider()
        ProductList(
            products = filteredProducts,
            onPriceClick = {
                selectedProduct = it
                isSheetOpen = true
            },
            cartViewModel = cartViewModel
        )
    }

    if (isSheetOpen && selectedProduct != null) {
        ProductBottomSheet(
            product = selectedProduct,
            cartViewModel = cartViewModel,
            onAddToCart = { isSheetOpen = false }
        )
    }
}