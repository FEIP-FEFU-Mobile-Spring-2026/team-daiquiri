package fefu.storeProject.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fefu.storeProject.data.CartItem
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
        Image(
            painter = painterResource(id = cartItem.product.imageRes),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cartItem.product.title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${cartItem.color.name}, ${cartItem.size.name}",
                fontSize = 12.sp
            )
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${cartItem.product.price} ₽",
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6D4C41)
                )
            }
        }
        CounterButtons(
            count = count,
            onIncrement = { cartViewModel.increment(cartItem) },
            onDecrement = { cartViewModel.decrement(cartItem) }
        )
        IconButton(onClick = { cartViewModel.removeItem(cartItem) }) {
            Icon(Icons.Default.Close, contentDescription = "Remove")
        }
    }
}