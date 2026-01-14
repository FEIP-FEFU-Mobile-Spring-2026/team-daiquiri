package fefu.storeProject.data

import fefu.storeProject.R
import androidx.compose.ui.graphics.Color

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val imageRes: Int,
    val categories: Set<Category>
)

enum class Category{
    NEW,
    JEANS,
    TSHIRTS
}

enum class Size {
    XXS, XS, S, M, L, XL
}

data class ProductColor(
    val name: String,
    val color: Color
)

val availableColors = listOf(
    ProductColor("Черный", Color.Black),
    ProductColor("Белый", Color.White),
    ProductColor("Серый", Color.Gray),
    ProductColor("Коричневый", Color(0xFF6D4C41))
)

val products = listOf(
    Product(1,"Блейзер прямого кроя", "Двубортный блейзер", 2970, R.drawable.blazer, setOf(Category.TSHIRTS, Category.NEW)),
    Product(2,"Брюки из лиоцелла", "Прямой крой", 3990, R.drawable.pants, setOf(Category.JEANS, Category.NEW)),
    Product(3,"Кардиган из хлопка", "Короткие рукава", 14999, R.drawable.cardigan, setOf(Category.TSHIRTS)),
    Product(4,"Кардиган из хлопка2", "Короткие рукава", 122, R.drawable.cardigan, setOf(Category.TSHIRTS,Category.NEW)),
    Product(5,"Кардиган из хлопка3", "Короткие рукава", 11, R.drawable.cardigan, setOf(Category.TSHIRTS,Category.NEW)),
)
