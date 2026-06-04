package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fefu.storeProject.data.CartItem
import fefu.storeProject.data.formatRubles
import fefu.storeProject.ui.theme.BrownPrimary
import fefu.storeProject.viewmodel.CartViewModel

@Composable
fun CartItemRow(
    cartItem: CartItem,
    count: Int,
    cartViewModel: CartViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.product.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cartItem.product.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = cartItem.size.name,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = formatRubles(cartItem.product.priceInKopecks * count),
                    fontWeight = FontWeight.Medium,
                    color = BrownPrimary
                )
            }
        }
        CounterButtons(
            count = count,
            onIncrement = { cartViewModel.increment(cartItem) },
            onDecrement = { cartViewModel.decrement(cartItem) }
        )
        IconButton(onClick = { cartViewModel.removeItem(cartItem) }) {
            Icon(Icons.Default.Close, contentDescription = "Удалить")
        }
    }
}
