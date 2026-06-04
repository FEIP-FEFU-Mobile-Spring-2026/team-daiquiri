package fefu.storeProject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fefu.storeProject.data.Product
import fefu.storeProject.ui.components.CategoryRow
import fefu.storeProject.ui.components.ProductBottomSheet
import fefu.storeProject.ui.components.ProductList
import fefu.storeProject.ui.theme.BrownPrimary
import fefu.storeProject.viewmodel.CartViewModel
import fefu.storeProject.viewmodel.CatalogUiState
import fefu.storeProject.viewmodel.CatalogViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    catalogViewModel: CatalogViewModel
) {
    val uiState by catalogViewModel.uiState.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isSheetOpen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is CatalogUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BrownPrimary)
                }
            }

            is CatalogUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { catalogViewModel.loadCatalog() },
                            colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary)
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is CatalogUiState.Success -> {
                if (state.isOffline) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF616161))
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.WifiOff,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Нет сети",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                CategoryRow(
                    categories = state.categories,
                    selectedCategoryId = state.selectedCategoryId,
                    onCategorySelected = { catalogViewModel.selectCategory(it) }
                )
                HorizontalDivider()
                ProductList(
                    products = state.products,
                    onItemClick = {
                        selectedProduct = it
                        isSheetOpen = true
                    },
                    cartViewModel = cartViewModel
                )
            }
        }
    }

    if (isSheetOpen && selectedProduct != null) {
        ProductBottomSheet(
            product = selectedProduct,
            cartViewModel = cartViewModel,
            onDismiss = { isSheetOpen = false }
        )
    }
}
