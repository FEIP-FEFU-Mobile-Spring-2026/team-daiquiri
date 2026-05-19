package fefu.storeProject.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import fefu.storeProject.data.CartItem
import fefu.storeProject.data.Product
import fefu.storeProject.data.ProductColor
import fefu.storeProject.data.Size

class CartViewModel : ViewModel() {

    private val _items = mutableStateMapOf<CartItem, Int>()
    val items: Map<CartItem, Int> get() = _items

    fun getCount(cartItem: CartItem): Int {
        return _items[cartItem] ?: 0
    }

    fun getProductTotalCount(product: Product): Int {
        return _items.filter { it.key.product.id == product.id }.values.sum()
    }

    fun increment(cartItem: CartItem) {
        _items[cartItem] = getCount(cartItem) + 1
    }

    fun decrement(cartItem: CartItem) {
        val current = getCount(cartItem)
        if (current > 1) {
            _items[cartItem] = current - 1
        } else {
            _items.remove(cartItem)
        }
    }

    fun addToCart(product: Product, size: Size, color: ProductColor) {
        val cartItem = CartItem(product, size, color)
        increment(cartItem)
    }

    fun getTotalPrice(): Long {
        return _items.entries.sumOf { it.key.product.priceInKopecks * it.value } / 100
    }

    fun clearCart() {
        _items.clear()
    }

    fun removeItem(cartItem: CartItem) {
        _items.remove(cartItem)
    }

    fun getTotalItemCount(): Int {
        return _items.values.sum()
    }

    fun incrementProduct(product: Product) {
        val firstItem = _items.keys.find { it.product.id == product.id }
        if (firstItem != null) {
            increment(firstItem)
        }
    }

    fun decrementProduct(product: Product) {
        val firstItem = _items.keys.find { it.product.id == product.id }
        if (firstItem != null) {
            decrement(firstItem)
        }
    }
}
