package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.TritiumGaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun AccountDetails(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AccountCredits()

        AccountIcon()
    }

}

@Composable
@Preview
fun AccountCredits(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {
    val rememberCredits = remember {
        mutableIntStateOf(credits)
    }

    val localContext = LocalContext.current
    val typedValue = TypedValue()
    localContext.theme.resolveAttribute(
        R.attr.backgroundColorOnBackground,
        typedValue,
        true)
    val backgroundColorOnBackground = Color(typedValue.data)
    localContext.theme.resolveAttribute(
        R.attr.textColorBodyEmphasis,
        typedValue,
        true)
    val textColor = Color(typedValue.data)

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
                text = rememberCredits.value.toString(),
                fontSize = 24.sp,
                color = textColor,
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
            )

        }

    }

}