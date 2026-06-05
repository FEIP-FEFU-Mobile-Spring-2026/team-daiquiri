package fefu.storeProject.data.api

import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("categories") val categories: List<CategoryDto>,
    @SerializedName("items") val items: List<ProductDto>,
)

data class CategoryDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("longDescription") val longDescription: String,
    @SerializedName("priceInKopecks") val priceInKopecks: Long,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("sizes") val sizes: List<SizeDto>,
    @SerializedName("material") val material: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("season") val season: String,
    @SerializedName("countryOfOrigin") val countryOfOrigin: String,
)

data class SizeDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
)
