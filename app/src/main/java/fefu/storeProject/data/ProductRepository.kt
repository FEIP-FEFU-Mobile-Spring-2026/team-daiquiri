package fefu.storeProject.data

import android.content.Context
import org.json.JSONObject

class ProductRepository(private val context: Context) {

    data class CatalogData(
        val categories: List<AppCategory>,
        val products: List<Product>
    )

    fun loadCatalog(): CatalogData {
        val json = context.assets.open(CATALOG_FILE).bufferedReader().use { it.readText() }
        val root = JSONObject(json)

        val categoriesArray = root.getJSONArray("categories")
        val jsonCategories = (0 until categoriesArray.length()).map { i ->
            val obj = categoriesArray.getJSONObject(i)
            AppCategory(id = obj.getString("id"), name = obj.getString("name"))
        }
        val categories = listOf(AppCategory(id = NEW_CATEGORY_ID, name = "Новинки")) + jsonCategories

        val itemsArray = root.getJSONArray("items")
        val products = (0 until itemsArray.length()).map { i ->
            val obj = itemsArray.getJSONObject(i)
            val tagsArray = obj.getJSONArray("tags")
            val tags = (0 until tagsArray.length()).map { j -> tagsArray.getString(j) }
            val sizesArray = obj.getJSONArray("sizes")
            val sizes = (0 until sizesArray.length()).map { j ->
                sizesArray.getJSONObject(j).getString("name")
            }
            Product(
                id = obj.getString("id"),
                name = obj.getString("name"),
                shortDescription = obj.getString("shortDescription"),
                longDescription = obj.getString("longDescription"),
                priceInKopecks = obj.getLong("priceInKopecks"),
                imageUrl = obj.getString("imageUrl"),
                tags = tags,
                categoryId = obj.getString("categoryId"),
                sizes = sizes,
                material = obj.getString("material"),
                weight = obj.getString("weight"),
                season = obj.getString("season"),
                countryOfOrigin = obj.getString("countryOfOrigin")
            )
        }

        return CatalogData(categories = categories, products = products)
    }

    companion object {
        const val NEW_CATEGORY_ID = "cat_new"
        private const val CATALOG_FILE = "products.json"
    }
}
