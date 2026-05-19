package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.ProductColor

@Composable
fun ColorSelector(
    colors: List<ProductColor>,
    selected: ProductColor,
    onSelect: (ProductColor) -> Unit
) {
    Row {
        colors.forEach { productColor ->
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(productColor.color)
                    .border(
                        width = if (selected == productColor) 2.dp else 1.dp,
                        color = if (selected == productColor) Color.Black else Color.LightGray,
                        shape = CircleShape
                    )
                    .clickable { onSelect(productColor) }
            )
        }
    }
}
