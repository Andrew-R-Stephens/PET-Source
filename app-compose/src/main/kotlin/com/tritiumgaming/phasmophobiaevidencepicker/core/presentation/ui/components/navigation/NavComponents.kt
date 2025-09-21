package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import org.jetbrains.annotations.TestOnly
import com.tritiumgaming.core.resources.R as CoreRDrawables

@Composable
fun NavigationHeaderComposable(
    modifier: Modifier = Modifier,
    params: NavHeaderComposableParams = NavHeaderComposableParams(),
) {

    val centerTitle = params.centerTitleRes?.let { stringResource(it) } ?: ""

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SideButton(
            modifier = Modifier.weight(1f, fill = false),
            type = params.leftType,
            titleRes = params.leftTitleRes,
            onClick = { params.leftOnClick() }
        )

        if(params.centerTitleRes != 0) {

            BasicText(
                modifier = Modifier
                    .weight(2f, fill = true),
                text = centerTitle,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
            )
        }

        SideButton(
            modifier = Modifier.weight(1f, fill = false),
            type = params.rightType,
            titleRes = params.rightTitleRes,
            onClick = { params.rightOnClick() }
        )

    }

}

@Composable
private fun RowScope.SideButton(
    modifier: Modifier = Modifier,
    type: PETImageButtonType = PETImageButtonType.NONE,
    @StringRes titleRes: Int = type.labelRes,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .weight(1f, fill = false)
            .clickable(enabled = true, onClick = { onClick() }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PETImageButton(
            type = type
        )
        if (titleRes != 0) {
            Text(
                modifier = Modifier
                    .wrapContentHeight(unbounded = false)
                    .heightIn(0.dp, 36.dp)
                    .padding(4.dp),
                text = stringResource(titleRes),
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.primary.regular,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun PETImageButton(
    modifier: Modifier = Modifier,
    type: PETImageButtonType = PETImageButtonType.NONE,
    @DrawableRes imageRes: Int = type.imageRes,
) {

    Box(
        modifier = modifier
            .heightIn(0.dp, 48.dp)
            .padding(8.dp)
    ) {

        if(imageRes != 0) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
                painter = painterResource(imageRes),
                colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                contentDescription = ""
            )
        } else {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
@TestOnly
@Preview
private fun TestPreview() {
    PETImageButton(
        type = PETImageButtonType.BACK
    )
}

data class NavHeaderComposableParams(
    var leftType: PETImageButtonType = PETImageButtonType.NONE,
    var rightType: PETImageButtonType = PETImageButtonType.NONE,
    @param:StringRes var centerTitleRes: Int? = null,
    @param:StringRes var leftTitleRes: Int = leftType.labelRes,
    @param:StringRes var rightTitleRes: Int = rightType.labelRes,
    var leftOnClick: () -> Unit = {},
    var rightOnClick: () -> Unit = {},
)

enum class PETImageButtonType(
    @param:DrawableRes val imageRes: Int = 0,
    @param:StringRes val labelRes: Int = 0
) {
    NONE(0, 0),
    BACK(CoreRDrawables.drawable.ic_arrow_60_left),
    FORWARD(CoreRDrawables.drawable.ic_arrow_60_right),
    CONFIRM(
        CoreRDrawables.drawable.ic_selector_pos_unsel,
        R.string.settings_confirm),
    CANCEL(
        CoreRDrawables.drawable.ic_selector_neg_unsel,
        R.string.settings_cancel),
    PLAY(CoreRDrawables.drawable.ic_control_play),
    PAUSE(CoreRDrawables.drawable.ic_control_pause)
}
