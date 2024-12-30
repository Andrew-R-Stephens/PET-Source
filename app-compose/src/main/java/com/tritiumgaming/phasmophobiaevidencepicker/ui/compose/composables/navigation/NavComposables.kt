package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import org.jetbrains.annotations.TestOnly

@Composable
fun TabPanel (
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black, RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for(i in 0..<3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(48.dp)
                ) {
                    ToggleButton(
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.size(48.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel = viewModel<InvestigationViewModel>(),
    state: Boolean = false
) {
    Image(
        painterResource(id = R.drawable.icon_sanityhead_top),
        contentDescription = "",
        modifier = modifier
            .border(2.dp, Color.White, RoundedCornerShape(4.dp))
    )
}

@Composable
fun NavigationHeaderComposable(
    params: NavHeaderComposableParams = NavHeaderComposableParams()
) {

    val centerTitle =
        if(params.centerTitleRes != 0) stringResource(params.centerTitleRes)
        else ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        SideButton(
            type = params.leftType,
            titleRes = params.leftTitleRes,
            onClick = { params.leftOnClick() }
        )

        if(params.centerTitleRes != 0) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(4.dp),
                text = centerTitle,
                color = LocalPalette.current.textFamily.primary,
                style = LocalTypography.current.primary.regular,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }

        SideButton(
            type = params.rightType,
            titleRes = params.rightTitleRes,
            onClick = { params.rightOnClick() }
        )

    }

}

data class NavHeaderComposableParams(
    var leftType: PETImageButtonType = PETImageButtonType.NONE,
    var rightType: PETImageButtonType = PETImageButtonType.NONE,
    @StringRes var centerTitleRes: Int = 0,
    @StringRes var leftTitleRes: Int = leftType.labelRes,
    @StringRes var rightTitleRes: Int = rightType.labelRes,
    var leftOnClick: () -> Unit = {},
    var rightOnClick: () -> Unit = {},
)

@Composable
fun RowScope.SideButton(
    type: PETImageButtonType = PETImageButtonType.NONE,
    @StringRes titleRes: Int = type.labelRes,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .weight(2f, fill = false)
            .clickable(enabled = true, onClick = { onClick() }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PETImageButton(type)
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
@TestOnly
@Preview
private fun TestPreview() {
    PETImageButton(PETImageButtonType.BACK)
}

@Composable
fun PETImageButton(
    type: PETImageButtonType = PETImageButtonType.NONE,
    @DrawableRes imageRes: Int = type.imageRes,
    modifier: Modifier = Modifier,
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

enum class PETImageButtonType(
    @DrawableRes val imageRes: Int = 0,
    @StringRes val labelRes: Int = 0
) {
    NONE(0, 0),
    BACK(R.drawable.ic_arrow_60_left),
    FORWARD(R.drawable.ic_arrow_60_right),
    CONFIRM(
        R.drawable.ic_selector_pos_unsel,
        R.string.settings_confirm),
    CANCEL(
        R.drawable.ic_selector_neg_unsel,
        R.string.settings_cancel),
    PLAY(R.drawable.ic_control_play),
    PAUSE(R.drawable.ic_control_pause)
}
