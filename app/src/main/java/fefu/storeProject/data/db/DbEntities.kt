package fefu.storeProject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import fefu.storeProject.data.AppCategory
import fefu.storeProject.data.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val shortDescription: String,
    val longDescription: String,
    val priceInKopecks: Long,
    val imageUrl: String,
    val tags: String,
    val categoryId: String,
    val sizes: String,
    val material: String,
    val weight: String,
    val season: String,
    val countryOfOrigin: String
) {
    fun toProduct() = Product(
        id = id,
        name = name,
        shortDescription = shortDescription,
        longDescription = longDescription,
        priceInKopecks = priceInKopecks,
        imageUrl = imageUrl,
        tags = tags.split("|").filter { it.isNotEmpty() },
        categoryId = categoryId,
        sizes = sizes.split("|").filter { it.isNotEmpty() },
        material = material,
        weight = weight,
        season = season,
        countryOfOrigin = countryOfOrigin
    )
}

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String
) {
    fun toAppCategory() = AppCategory(id = id, name = name)
}
