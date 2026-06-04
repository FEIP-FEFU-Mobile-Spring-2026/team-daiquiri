package fefu.storeProject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import fefu.storeProject.data.formatRubles
import fefu.storeProject.ui.components.CartItemRow
import fefu.storeProject.ui.components.CheckoutSuccessSheet
import fefu.storeProject.ui.theme.BrownPrimary
import fefu.storeProject.viewmodel.CartViewModel
import kotlinx.coroutines.launch

private fun isValidEmail(email: String): Boolean =
    Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$").matches(email.trim())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel, navController: NavHostController) {
    val cartEntries = cartViewModel.items.toList()
    val scope = rememberCoroutineScope()

    var showClearConfirm by remember { mutableStateOf(false) }
    var isSuccessSheetOpen by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    val isFormValid = name.isNotBlank() && isValidEmail(email)

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Очистить корзину?") },
            text = { Text("Все товары будут удалены из корзины") },
            confirmButton = {
                TextButton(onClick = {
                    cartViewModel.clearCart()
                    showClearConfirm = false
                }) { Text("Очистить") }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) { Text("Отмена") }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = { Text("Корзина") },
            actions = {
                IconButton(onClick = { showClearConfirm = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Очистить корзину"
                    )
                }
            }
        )
        HorizontalDivider()

        if (cartEntries.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Корзина пуста", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Добавьте товары для оформления заказа",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartEntries) { (cartItem, count) ->
                    CartItemRow(
                        cartItem = cartItem,
                        count = count,
                        cartViewModel = cartViewModel
                    )
                    HorizontalDivider()
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Имя *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Комментарий") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Итого", fontWeight = FontWeight.Bold)
                    Text(
                        formatRubles(cartViewModel.getTotalPriceInKopecks()),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        cartViewModel.clearCart()
                        isSuccessSheetOpen = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary)
                ) {
                    Text("Оформить")
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }

    if (isSuccessSheetOpen) {
        CheckoutSuccessSheet(
            onDismiss = { isSuccessSheetOpen = false },
            onGoToMainClick = {
                scope.launch {
                    isSuccessSheetOpen = false
                    navController.navigate("main")
                }
            }
        )
    }
}
