package fefu.storeProject.viewmodel

import androidx.compose.ui.graphics.Color
import fefu.storeProject.data.CartItem
import fefu.storeProject.data.Product
import fefu.storeProject.data.ProductColor
import fefu.storeProject.data.Size
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    private val product1 = Product(
        id = 1,
        title = "Product 1",
        description = "Description",
        price = 100,
        imageRes = 0,
        categories = emptySet()
    )

    private val product2 = Product(
        id = 2,
        title = "Product 2",
        description = "Description",
        price = 200,
        imageRes = 0,
        categories = emptySet()
    )

    private val color = ProductColor("Black", Color.Black)
    private val size = Size.M

    @Before
    fun setUp() {
        viewModel = CartViewModel()
    }

    // getTotalPrice

    @Test
    fun getTotalPrice_emptyCart_returnsZero() {
        assertEquals(0, viewModel.getTotalPrice())
    }

    @Test
    fun getTotalPrice_singleItem_returnsItemPrice() {
        viewModel.addToCart(product1, size, color)
        assertEquals(100, viewModel.getTotalPrice())
    }

    @Test
    fun getTotalPrice_sameItemAddedTwice_returnsDoubledPrice() {
        viewModel.addToCart(product1, size, color)
        viewModel.addToCart(product1, size, color)
        assertEquals(200, viewModel.getTotalPrice())
    }

    @Test
    fun getTotalPrice_differentProducts_returnsSumOfPrices() {
        viewModel.addToCart(product1, size, color)
        viewModel.addToCart(product2, size, color)
        assertEquals(300, viewModel.getTotalPrice())
    }

    // addToCart

    @Test
    fun addToCart_newItem_itemAppearsInCart() {
        viewModel.addToCart(product1, size, color)
        val cartItem = CartItem(product1, size, color)
        assertEquals(1, viewModel.getCount(cartItem))
    }

    @Test
    fun addToCart_sameItemTwice_countIsTwo() {
        viewModel.addToCart(product1, size, color)
        viewModel.addToCart(product1, size, color)
        val cartItem = CartItem(product1, size, color)
        assertEquals(2, viewModel.getCount(cartItem))
    }

    @Test
    fun addToCart_differentItems_bothPresentInCart() {
        viewModel.addToCart(product1, size, color)
        viewModel.addToCart(product2, size, color)
        assertEquals(2, viewModel.items.size)
    }

    // removeFromCart

    @Test
    fun removeFromCart_existingItem_itemRemovedFromCart() {
        viewModel.addToCart(product1, size, color)
        val cartItem = CartItem(product1, size, color)
        viewModel.removeItem(cartItem)
        assertEquals(0, viewModel.getCount(cartItem))
    }

    @Test
    fun removeFromCart_onlyItem_cartBecomesEmpty() {
        viewModel.addToCart(product1, size, color)
        val cartItem = CartItem(product1, size, color)
        viewModel.removeItem(cartItem)
        assertTrue(viewModel.items.isEmpty())
    }

    @Test
    fun removeFromCart_oneOfMultipleItems_otherItemsRemain() {
        viewModel.addToCart(product1, size, color)
        viewModel.addToCart(product2, size, color)
        val cartItem1 = CartItem(product1, size, color)
        viewModel.removeItem(cartItem1)
        assertEquals(1, viewModel.items.size)
    }
}
