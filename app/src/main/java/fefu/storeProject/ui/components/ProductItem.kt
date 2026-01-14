package fefu.storeProject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.Product
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp

@Composable
fun ProductItem(
    product: Product,
    count: Int,
    onPriceClick: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(modifier = Modifier.padding(20.dp)) {

        Image(
            painter = painterResource(product.imageRes),
            contentDescription = null,
            modifier = Modifier.size(170.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(product.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(product.description, color = Color.Gray, fontSize = 12.sp)

            Spacer(modifier = Modifier.weight(1f))

            if (count == 0) {
                PriceButton(product.price, onClick = onPriceClick)
            } else {
                CounterButtons(
                    count = count,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement
                )
            }
        }
    }
}



