package fefu.storeProject.data

import androidx.compose.ui.graphics.Color
import fefu.storeProject.ui.theme.BrownPrimary
import java.text.NumberFormat
import java.util.Locale

data class ProductSize(
    val id: String,
    val name: String
)

data class Product(
    val id: String,
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val priceInKopecks: Long,
    val imageUrl: String,
    val tags: List<String>,
    val categoryId: String,
    val sizes: List<ProductSize>,
    val material: String,
    val weight: String,
    val season: String,
    val countryOfOrigin: String
)

data class AppCategory(
    val id: String,
    val name: String
)

data class ProductColor(
    val name: String,
    val color: Color
)

val availableColors = listOf(
    ProductColor("Черный", Color.Black),
    ProductColor("Белый", Color.White),
    ProductColor("Серый", Color.Gray),
    ProductColor("Коричневый", BrownPrimary),
    ProductColor("Бежевый", Color(0xFFD7CCC8)),
    ProductColor("Темно-синий", Color(0xFF1A237E))
)

fun formatRubles(priceInKopecks: Long): String {
    val rubles = priceInKopecks / 100
    val nf = NumberFormat.getNumberInstance(Locale("ru"))
    return "${nf.format(rubles)} ₽"
}
