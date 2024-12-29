package com.tritiumgaming.phasmophobiaevidencepicker.views.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette


@Composable
@Preview
fun AccountCreditsComposable(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {
    val rememberCredits = remember {
        mutableIntStateOf(credits)
    }

    val backgroundColorOnBackground = LocalPalette.current.surface.onColor
    val textColor = LocalPalette.current.textFamily.emphasis

    Card(
        modifier = modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            disabledContentColor = backgroundColorOnBackground,
            contentColor = backgroundColorOnBackground,
            containerColor = backgroundColorOnBackground,
            disabledContainerColor = backgroundColorOnBackground
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
            Image(
                painter = painterResource(id = R.drawable.ic_shop_cost),
                contentDescription = "P.E.T. Currency Icon",
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp)
            )

            Text(
                text = rememberCredits.intValue.toString(),
                fontSize = 24.sp,
                color = textColor,
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
            )

        }

    }

}