package com.tritiumgaming.feature.operation.ui.investigation.journal.popups.common

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.ui.investigation.journal.popups.evidence.EvidenceTypePage

@Composable
fun RowScope.PrimaryPageButton(
    modifier: Modifier = Modifier,
    pageType: EvidenceTypePage,
    activePage: MutableIntState,
    @StringRes textRes: Int = pageType.label,
    onClick: () -> Unit = { }
) {

    val rememberActivePage by remember { activePage }

    val context = LocalContext.current

    Button(
        modifier = modifier
            .padding(8.dp)
            .weight(1f)
            .fillMaxWidth(.35f)
            .heightIn(max = 48.dp)
            .padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = LocalPalette.current.selectedColor2,
            containerColor = LocalPalette.current.surface.color
        ),
        onClick = {
            onClick()
            Toast.makeText(context, "$rememberActivePage", Toast.LENGTH_SHORT).show()
        },
        enabled = rememberActivePage != pageType.pageIndex
    ) {
        BasicText(
            text = stringResource(textRes).uppercase(),
            style = LocalTypography.current.secondary.regular.copy(
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center
            ),
            autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 1.6.sp)
        )
        /*AutoResizedText(
            containerModifier = Modifier
                .fillMaxSize(),
            text = stringResource(textRes),
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.secondary.regular,
            autoResizeStyle = AutoResizedStyleType.SQUEEZE,
            textCase = TextCase.Uppercase,
            textAlign = TextAlign.Center
        )*/
    }
}

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