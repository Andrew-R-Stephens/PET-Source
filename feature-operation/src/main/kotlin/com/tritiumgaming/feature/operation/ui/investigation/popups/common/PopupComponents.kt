package com.tritiumgaming.feature.operation.ui.investigation.popups.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography

@Composable
fun AnimatedGif(
    modifier: Modifier = Modifier,
    @DrawableRes animatedGifRes: Int,
    contentScale: ContentScale = ContentScale.FillWidth
) {

    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        model = animatedGifRes,
        contentDescription = "Gradient background",
        contentScale = contentScale
    )

}

@Composable
fun PopupDataRow(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    data: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(1.dp),
            painter = painterResource(id = icon),
            contentDescription = "Cost Icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = LocalPalette.current.codexFamily.codex3,
            )
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 2.dp),
            text = data,
            style = LocalTypography.current.quaternary.regular.copy(
                textAlign = TextAlign.Start
            ),
            color = LocalPalette.current.codexFamily.codex3,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 1.sp)
        )

    }
}

@Composable fun PageButton(
    isSelected: Boolean,
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    if(isSelected) {
        PageButtonSelected(icon) { onClick() }
    } else {
        PageButtonUnselected(icon) { onClick() }
    }
}

@Composable fun PageButton(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit = {}
) {
    if(isSelected) {
        PageButtonSelected(text) { onClick() }
    } else {
        PageButtonUnselected(text) { onClick() }
    }
}

@Composable
private fun PageButtonUnselected(
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = Modifier
            .height(48.dp)
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center,
            ),
            color = LocalPalette.current.codexFamily.codex3
        )
    }
}

@Composable
private fun PageButtonSelected(
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = Modifier
            .height(48.dp)
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.textButtonColors(
            containerColor = LocalPalette.current.codexFamily.codex4
        )
    ) {
        Text(
            text = text,
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center,
            ),
            color = LocalPalette.current.codexFamily.codex2
        )
    }

}

@Composable
private fun PageButtonUnselected(
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    OutlinedIconButton(
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        border = BorderStroke(
            width = 2.dp,
            color = LocalPalette.current.codexFamily.codex3
        )
    ) {
        icon(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(2.dp),
            IconVectorColors(
                fillColor = LocalPalette.current.codexFamily.codex3,
                strokeColor = LocalPalette.current.codexFamily.codex3
            )
        )
    }
}

@Composable
private fun PageButtonSelected(
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = LocalPalette.current.codexFamily.codex4
        )
    ) {
        icon(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(2.dp),
            IconVectorColors(
                fillColor = LocalPalette.current.codexFamily.codex2,
                strokeColor = LocalPalette.current.codexFamily.codex2
            )
        )
    }
}