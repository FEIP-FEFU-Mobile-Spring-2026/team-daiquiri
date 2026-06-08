package fefu.storeProject.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import fefu.storeProject.data.api.RetrofitInstance
import fefu.storeProject.data.db.AppDatabase
import fefu.storeProject.data.db.CategoryEntity
import fefu.storeProject.data.db.ProductEntity

class ProductRepository(private val context: Context) {
    private val dao = AppDatabase.getInstance(context).catalogDao()
    private val apiService = RetrofitInstance.service

    data class CatalogData(
        val categories: List<AppCategory>,
        val products: List<Product>,
    )

    suspend fun loadFromCache(): CatalogData? {
        val productEntities = dao.getAllProducts()
        if (productEntities.isEmpty()) return null
        val categoryEntities = dao.getAllCategories()
        return CatalogData(
            categories =
                listOf(AppCategory(NEW_CATEGORY_ID, "Новинки")) +
                    categoryEntities.map { it.toAppCategory() },
            products = productEntities.map { it.toProduct() },
        )
    }

    suspend fun loadFromNetwork(): CatalogData {
        val response = apiService.getCatalog()

        dao.deleteAllProducts()
        dao.deleteAllCategories()
        dao.insertCategories(response.categories.map { CategoryEntity(it.id, it.name) })
        dao.insertProducts(
            response.items.map { dto ->
                ProductEntity(
                    id = dto.id,
                    name = dto.name,
                    shortDescription = dto.shortDescription,
                    longDescription = dto.longDescription,
                    priceInKopecks = dto.priceInKopecks,
                    imageUrl = dto.imageUrl,
                    tags = dto.tags.joinToString("|"),
                    categoryId = dto.categoryId,
                    sizes = dto.sizes.joinToString("|") { "${it.id}:${it.name}" },
                    material = dto.material,
                    weight = dto.weight,
                    season = dto.season,
                    countryOfOrigin = dto.countryOfOrigin,
                )
            },
        )

        return CatalogData(
            categories =
                listOf(AppCategory(NEW_CATEGORY_ID, "Новинки")) +
                    response.categories.map { AppCategory(it.id, it.name) },
            products =
                response.items.map { dto ->
                    Product(
                        id = dto.id,
                        name = dto.name,
                        shortDescription = dto.shortDescription,
                        longDescription = dto.longDescription,
                        priceInKopecks = dto.priceInKopecks,
                        imageUrl = dto.imageUrl,
                        tags = dto.tags,
                        categoryId = dto.categoryId,
                        sizes = dto.sizes.map { ProductSize(it.id, it.name) },
                        material = dto.material,
                        weight = dto.weight,
                        season = dto.season,
                        countryOfOrigin = dto.countryOfOrigin,
                    )
                },
        )
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    companion object {
        const val NEW_CATEGORY_ID = "cat_new"
    }
}
