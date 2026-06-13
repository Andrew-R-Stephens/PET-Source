package com.tritiumgaming.feature.marketplace.ui.store.typographies


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.white_M100


@Composable
fun TypographyCard(
    modifier: Modifier = Modifier,
    typography: ExtendedTypography,
    title: String,
    buyCredits: Long,
    onBuyClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = white_M100)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = typography.primary.bold,
                color = LocalPalette.current.onSurface
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_shop_cost),
                        contentDescription = null,
                        modifier = Modifier.height(20.dp).aspectRatio(1f),
                        colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                    )
                    Text(
                        text = buyCredits.toString(),
                        modifier = Modifier.padding(start = 4.dp),
                        style = typography.quaternary.bold,
                        color = LocalPalette.current.primary
                    )
                }
                Button(
                    onClick = onBuyClick,
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary)
                ) {
                    Text(text = stringResource(R.string.marketplace_button_item_get).uppercase())
                }
            }
        }
    }
}
