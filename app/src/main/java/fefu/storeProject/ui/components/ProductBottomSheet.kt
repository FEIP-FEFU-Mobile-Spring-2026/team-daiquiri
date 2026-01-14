// ProductBottomSheet.kt
package fefu.storeProject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fefu.storeProject.data.Product
import fefu.storeProject.data.Size
import fefu.storeProject.data.availableColors
import fefu.storeProject.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductBottomSheet(
    product: Product?,
    cartViewModel: CartViewModel,
    onAddToCart: () -> Unit
) {
    if (product == null) return

    var selectedSize by remember { mutableStateOf(Size.M) }
    var selectedColor by remember { mutableStateOf(availableColors[0]) }

    ModalBottomSheet(
        onDismissRequest = onAddToCart
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Text(product.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(product.description, color = Color.Gray)

            Spacer(Modifier.height(16.dp))

            Text("Размер:", fontWeight = FontWeight.Bold)
            SizeSelector(
                sizes = Size.values().toList(),
                selected = selectedSize,
                onSelect = { selectedSize = it }
            )

            Spacer(Modifier.height(16.dp))

            Text("Цвет:", fontWeight = FontWeight.Bold)
            ColorSelector(
                colors = availableColors,
                selected = selectedColor,
                onSelect = { selectedColor = it }
            )

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41)),
                onClick = {
                    cartViewModel.addToCart(product, selectedSize, selectedColor)
                    onAddToCart()
                }
            ) {
                Text("В корзину · ${product.price} ₽")
            }
        }
    }
}

