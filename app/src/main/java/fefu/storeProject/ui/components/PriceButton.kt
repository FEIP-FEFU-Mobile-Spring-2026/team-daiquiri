package fefu.storeProject.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fefu.storeProject.data.formatRubles
import fefu.storeProject.ui.theme.BrownPrimary

@Composable
fun PriceButton(
    priceInKopecks: Long,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .height(ActionHeight)
                .border(
                    1.dp,
                    Color(0xFFE0D4CC),
                    RoundedCornerShape(12.dp),
                )
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = formatRubles(priceInKopecks),
            fontWeight = FontWeight.Medium,
            color = BrownPrimary,
        )
    }
}
