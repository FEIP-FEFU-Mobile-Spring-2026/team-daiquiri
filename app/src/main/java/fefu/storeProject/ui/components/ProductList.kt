package fefu.storeProject.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import fefu.storeProject.data.Product
import fefu.storeProject.viewmodel.CartViewModel

@Composable
fun ProductList(
    products: List<Product>,
    cartViewModel: CartViewModel,
    onItemClick: (Product) -> Unit
) {
    LazyColumn {
        items(products) { product ->
            val count = cartViewModel.getProductTotalCount(product)

            ProductItem(
                product = product,
                count = count,
                onItemClick = { onItemClick(product) },
                onIncrement = { cartViewModel.incrementProduct(product) },
                onDecrement = { cartViewModel.decrementProduct(product) }
            )
        }
    }
}
