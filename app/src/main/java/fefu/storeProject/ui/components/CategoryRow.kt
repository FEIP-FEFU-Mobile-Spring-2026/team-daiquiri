package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.Category


@Composable
fun CategoryRow(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryChip(
            text = "Новинки",
            isSelected = selectedCategory == Category.NEW,
            onClick = { onCategorySelected(Category.NEW) }
        )

        CategoryChip(
            text = "Джинсы",
            isSelected = selectedCategory == Category.JEANS,
            onClick = { onCategorySelected(Category.JEANS) }
        )

        CategoryChip(
            text = "Футболки",
            isSelected = selectedCategory == Category.TSHIRTS,
            onClick = { onCategorySelected(Category.TSHIRTS) }
        )
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
                color = if (isSelected) Color(0xFF6D4C41) else Color(0xFFF5F5F5),
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
