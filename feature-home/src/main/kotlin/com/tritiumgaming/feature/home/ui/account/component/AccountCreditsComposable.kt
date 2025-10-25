package com.tritiumgaming.feature.home.ui.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.icon.ShopCostIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography


@Composable
@Preview
private fun AccountCreditsPreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountCredits()
    }

}

@Composable
fun AccountCredits(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {

    Card(
        modifier = modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            contentColor = LocalPalette.current.surface.onColor,
            containerColor = LocalPalette.current.surface.onColor
        )
    ) {

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                alignment = Alignment.CenterHorizontally,
                space = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShopCostIcon(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                colors = IconVectorColors(
                    strokeColor = LocalPalette.current.textFamily.body,
                    fillColor = LocalPalette.current.textFamily.body
                )
            )
            
            Text(
                text = credits.toString(),
                fontSize = 24.sp,
                color = LocalPalette.current.textFamily.emphasis,
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
            )

        }

    }

}