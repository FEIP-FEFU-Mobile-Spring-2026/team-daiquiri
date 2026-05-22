package fefu.storeProject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fefu.storeProject.ui.components.CartItemRow
import fefu.storeProject.ui.components.CheckoutSuccessSheet
import fefu.storeProject.ui.components.EmptyCartErrorSheet
import fefu.storeProject.ui.theme.BrownPrimary
import fefu.storeProject.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel(), navController: NavHostController) {
    val items = cartViewModel.items.toList()
    val successSheetState = rememberModalBottomSheetState()
    var isSuccessSheetOpen by remember { mutableStateOf(false) }
    val errorSheetState = rememberModalBottomSheetState()
    var isErrorSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = { Text("Корзина") },
            actions = {
                IconButton(onClick = { cartViewModel.clearCart() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Очистить корзину"
                    )
                }
            }
        )
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(items) { (cartItem, count) ->
                CartItemRow(
                    cartItem = cartItem,
                    count = count,
                    cartViewModel = cartViewModel
                )
                HorizontalDivider()
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Итого")
                Text(text = "${cartViewModel.getTotalPrice()} ₽")
            }
            Button(
                onClick = {
                    if (cartViewModel.items.isEmpty()) {
                        isErrorSheetOpen = true
                    } else {
                        isSuccessSheetOpen = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BrownPrimary)
            ) {
                Text(text = "Перейти к оформлению")
            }
        }
    }

    if (isSuccessSheetOpen) {
        CheckoutSuccessSheet(
            onDismiss = { isSuccessSheetOpen = false },
            onGoToMainClick = {
                scope.launch {
                    successSheetState.hide()
                    isSuccessSheetOpen = false
                    cartViewModel.clearCart()
                    navController.navigate("main")
                }
            }
        )
    }

    if (isErrorSheetOpen) {
        EmptyCartErrorSheet(
            onDismiss = { isErrorSheetOpen = false },
            onBlastContractClick = {
                scope.launch {
                    errorSheetState.hide()
                    isErrorSheetOpen = false
                }
            }
        )
    }
}
