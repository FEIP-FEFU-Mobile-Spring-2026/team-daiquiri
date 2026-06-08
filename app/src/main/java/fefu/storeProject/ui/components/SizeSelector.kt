package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.ProductSize
import fefu.storeProject.ui.theme.BrownPrimary

@Composable
fun SizeSelector(
    sizes: List<ProductSize>,
    selected: ProductSize?,
    onSelect: (ProductSize) -> Unit,
) {
    Row {
        sizes.forEach { size ->
            Text(
                text = size.name,
                modifier =
                    Modifier
                        .padding(6.dp)
                        .background(
                            if (size == selected) BrownPrimary else Color.LightGray,
                            RoundedCornerShape(12.dp),
                        )
                        .clickable { onSelect(size) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                color = if (size == selected) Color.White else Color.Black,
            )
        }
    }
}
