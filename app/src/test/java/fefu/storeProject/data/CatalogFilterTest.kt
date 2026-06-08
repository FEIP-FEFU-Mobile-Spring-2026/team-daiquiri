package fefu.storeProject.data

import fefu.storeProject.data.db.ProductEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CatalogFilterTest {
    private fun makeProduct(
        id: String,
        categoryId: String,
        tags: List<String> = emptyList(),
    ) = Product(
        id = id,
        name = "Product $id",
        shortDescription = "",
        longDescription = "",
        priceInKopecks = 1000L,
        imageUrl = "",
        tags = tags,
        categoryId = categoryId,
        sizes = emptyList(),
        material = "",
        weight = "",
        season = "",
        countryOfOrigin = "",
    )

    // --- Category filter ---

    @Test
    fun filter_byCategoryId_returnsOnlyMatchingProducts() {
        val products =
            listOf(
                makeProduct("1", "cat_jeans"),
                makeProduct("2", "cat_jeans"),
                makeProduct("3", "cat_tshirts"),
            )
        val result = products.filter { it.categoryId == "cat_jeans" }
        assertEquals(2, result.size)
        assertTrue(result.all { it.categoryId == "cat_jeans" })
    }

    @Test
    fun filter_byCategory_emptyWhenNoMatch() {
        val products =
            listOf(
                makeProduct("1", "cat_jeans"),
                makeProduct("2", "cat_tshirts"),
            )
        val result = products.filter { it.categoryId == "cat_dresses" }
        assertTrue(result.isEmpty())
    }

    @Test
    fun filter_newCategory_returnsByNewTag() {
        val products =
            listOf(
                makeProduct("1", "cat_jeans", tags = listOf("New")),
                makeProduct("2", "cat_tshirts", tags = listOf("Sale")),
                makeProduct("3", "cat_dresses", tags = listOf("New", "Featured")),
            )
        val result = products.filter { it.tags.contains("New") }
        assertEquals(2, result.size)
        assertTrue(result.map { it.id }.containsAll(listOf("1", "3")))
    }

    // --- ProductEntity mapping ---

    @Test
    fun productEntity_toProduct_parsesSizesCorrectly() {
        val entity =
            ProductEntity(
                id = "p1",
                name = "Jeans",
                shortDescription = "",
                longDescription = "",
                priceInKopecks = 5000L,
                imageUrl = "",
                tags = "New|Featured",
                categoryId = "cat_jeans",
                sizes = "size_s:S|size_m:M|size_l:L",
                material = "Cotton",
                weight = "300g",
                season = "All",
                countryOfOrigin = "Russia",
            )
        val product = entity.toProduct()
        assertEquals(3, product.sizes.size)
        assertEquals("size_m", product.sizes[1].id)
        assertEquals("M", product.sizes[1].name)
    }

    @Test
    fun productEntity_toProduct_parsesTagsCorrectly() {
        val entity =
            ProductEntity(
                id = "p2",
                name = "T-Shirt",
                shortDescription = "",
                longDescription = "",
                priceInKopecks = 2000L,
                imageUrl = "",
                tags = "New|Sale",
                categoryId = "cat_tshirts",
                sizes = "",
                material = "Cotton",
                weight = "150g",
                season = "Summer",
                countryOfOrigin = "Russia",
            )
        val product = entity.toProduct()
        assertEquals(listOf("New", "Sale"), product.tags)
    }

    @Test
    fun productEntity_toProduct_emptyTagsAndSizes() {
        val entity =
            ProductEntity(
                id = "p3",
                name = "Hoodie",
                shortDescription = "",
                longDescription = "",
                priceInKopecks = 8000L,
                imageUrl = "",
                tags = "",
                categoryId = "cat_hoodies",
                sizes = "",
                material = "Fleece",
                weight = "500g",
                season = "Winter",
                countryOfOrigin = "Russia",
            )
        val product = entity.toProduct()
        assertTrue(product.tags.isEmpty())
        assertTrue(product.sizes.isEmpty())
    }
}
