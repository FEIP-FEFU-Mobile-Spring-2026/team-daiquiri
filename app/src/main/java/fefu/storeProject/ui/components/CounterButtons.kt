package fefu.storeProject.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val ActionHeight = 40.dp

@Composable
fun CounterButtons(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .height(ActionHeight)
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(12.dp),
                )
                .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .clickable { onDecrement() },
            contentAlignment = Alignment.Center,
        ) {
            Text("-", fontSize = 20.sp)
        }

        Text(
            text = count.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Medium,
        )

        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .clickable { onIncrement() },
            contentAlignment = Alignment.Center,
        ) {
            Text("+", fontSize = 20.sp)
        }
    }
}
