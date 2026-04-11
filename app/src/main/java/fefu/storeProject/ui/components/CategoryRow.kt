package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fefu.storeProject.ui.theme.BrownPrimary
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.Category

@Composable
fun CategoryRow(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(Category.entries) { category ->
            CategoryChip(
                text = getCategoryName(category),
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) BrownPrimary else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

fun getCategoryName(category: Category): String {
    return when (category) {
        Category.NEW -> "Новинки"
        Category.JEANS -> "Джинсы"
        Category.TSHIRTS -> "Футболки"
        Category.DRESSES -> "Платья"
        Category.OUTERWEAR -> "Верхняя одежда"
        Category.HOODIES -> "Худи"
    }
}