package fefu.storeProject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fefu.storeProject.data.Product
import fefu.storeProject.data.ProductSize
import fefu.storeProject.data.formatRubles
import fefu.storeProject.ui.theme.BrownPrimary
import fefu.storeProject.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductBottomSheet(
    product: Product?,
    cartViewModel: CartViewModel,
    onDismiss: () -> Unit,
) {
    if (product == null) return

    var selectedSize by remember(product.id) { mutableStateOf(product.sizes.firstOrNull()) }
    var showInfoDialog by remember { mutableStateOf(false) }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Характеристики") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProductInfoRow("Материал", product.material)
                    ProductInfoRow("Вес", product.weight)
                    ProductInfoRow("Сезон", product.season)
                    ProductInfoRow("Страна производства", product.countryOfOrigin)
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) { Text("ОК") }
            },
        )
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(240.dp),
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                )

                if (product.tags.isNotEmpty()) {
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        product.tags.forEach { tag ->
                            Box(
                                modifier =
                                    Modifier
                                        .background(
                                            color = BrownPrimary.copy(alpha = 0.85f),
                                            shape = RoundedCornerShape(12.dp),
                                        )
                                        .padding(horizontal = 10.dp, vertical = 4.dp),
                            ) {
                                Text(
                                    text = tag,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { showInfoDialog = true },
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Информация о товаре",
                        tint = Color.White,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(product.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.height(2.dp))
            Text(
                text = formatRubles(product.priceInKopecks),
                fontSize = 18.sp,
                color = BrownPrimary,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(6.dp))
            Text(product.longDescription, color = Color.Gray, fontSize = 14.sp, lineHeight = 20.sp)

            if (product.sizes.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text("Размер:", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    product.sizes.forEach { size ->
                        val isSelected = size == selectedSize
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier =
                                Modifier
                                    .background(
                                        color = if (isSelected) BrownPrimary else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp),
                                    )
                                    .clickable { selectedSize = size }
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                        ) {
                            Text(
                                text = size.name,
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary),
                onClick = {
                    val size = selectedSize ?: ProductSize("", "")
                    cartViewModel.addToCart(product, size)
                    onDismiss()
                },
            ) {
                Text("В корзину · ${formatRubles(product.priceInKopecks)}")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProductInfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(label, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.45f), color = Color.Gray)
        Text(value, modifier = Modifier.weight(0.55f))
    }
}
