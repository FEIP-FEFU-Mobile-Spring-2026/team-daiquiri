package fefu.storeProject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fefu.storeProject.data.Product

@Composable
fun ProductItem(
    product: Product,
    count: Int,
    onPriceClick: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .height(IntrinsicSize.Min)
    ) {

        Image(
            painter = painterResource(product.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(170.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.description,
                color = Color.Gray,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

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