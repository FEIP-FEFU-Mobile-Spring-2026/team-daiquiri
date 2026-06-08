package fefu.storeProject.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fefu.storeProject.data.CartItem
import fefu.storeProject.data.Product
import fefu.storeProject.data.ProductSize
import fefu.storeProject.data.db.CartDao
import fefu.storeProject.data.db.CartItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val cartDao: CartDao? = null) : ViewModel() {
    private val _items = mutableStateMapOf<CartItem, Int>()
    val items: Map<CartItem, Int> get() = _items

    private var dbLoaded = false

    fun loadFromDb(products: List<Product>) {
        if (dbLoaded) return
        dbLoaded = true
        cartDao ?: return
        viewModelScope.launch {
            val dbItems = withContext(Dispatchers.IO) { cartDao.getAll() }
            dbItems.forEach { entity ->
                val product = products.find { it.id == entity.productId } ?: return@forEach
                val size = product.sizes.find { it.id == entity.sizeId } ?: return@forEach
                _items[CartItem(product, size)] = entity.quantity
            }
        }
    }

    private fun syncToDb() {
        if (!dbLoaded || cartDao == null) return
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.deleteAll()
            cartDao.insertAll(
                _items.map { (item, qty) ->
                    CartItemEntity(
                        id = "${item.product.id}_${item.size.id}",
                        productId = item.product.id,
                        sizeId = item.size.id,
                        quantity = qty,
                    )
                },
            )
        }
    }

    fun getCount(cartItem: CartItem): Int = _items[cartItem] ?: 0

    fun getProductTotalCount(product: Product): Int = _items.filter { it.key.product.id == product.id }.values.sum()

    fun increment(cartItem: CartItem) {
        _items[cartItem] = getCount(cartItem) + 1
        syncToDb()
    }

    fun decrement(cartItem: CartItem) {
        val current = getCount(cartItem)
        if (current > 1) {
            _items[cartItem] = current - 1
        } else {
            _items.remove(cartItem)
        }
        syncToDb()
    }

    fun addToCart(
        product: Product,
        size: ProductSize,
    ) {
        val cartItem = CartItem(product, size)
        increment(cartItem)
    }

    fun getTotalPrice(): Long = _items.entries.sumOf { it.key.product.priceInKopecks * it.value } / 100

    fun getTotalPriceInKopecks(): Long = _items.entries.sumOf { it.key.product.priceInKopecks * it.value }

    fun clearCart() {
        _items.clear()
        syncToDb()
    }

    fun removeItem(cartItem: CartItem) {
        _items.remove(cartItem)
        syncToDb()
    }

    fun getTotalItemCount(): Int = _items.values.sum()

    fun incrementProduct(product: Product) {
        val firstItem = _items.keys.find { it.product.id == product.id }
        if (firstItem != null) increment(firstItem)
    }

    fun decrementProduct(product: Product) {
        val firstItem = _items.keys.find { it.product.id == product.id }
        if (firstItem != null) decrement(firstItem)
    }

    companion object {
        fun factory(cartDao: CartDao): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T = CartViewModel(cartDao) as T
            }
    }
}
