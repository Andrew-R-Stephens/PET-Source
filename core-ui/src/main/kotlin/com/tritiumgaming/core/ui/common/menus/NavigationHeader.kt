package com.tritiumgaming.core.ui.common.menus

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography

@Composable
fun NavigationHeaderComposable(
    modifier: Modifier = Modifier,
    leftContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    rightContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    centerContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        leftContent?.let { content ->
            content(
                Modifier
            )
        }

        centerContent?.let { content ->
            content(
                Modifier
                    .weight(1f)
            )
        }

        rightContent?.let { content ->
            content(
                Modifier
            )
        }

    }

}

@Composable
fun RowScope.NavigationHeaderCenter(
    modifier: Modifier = Modifier,
    textContent: @Composable (modifier: Modifier) -> Unit,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        textContent(
            Modifier
                .wrapContentHeight(unbounded = false)
                .heightIn(0.dp, 36.dp)
                .padding(4.dp)
        )
    }
}

@Composable
fun RowScope.NavigationHeaderSideButton(
    modifier: Modifier = Modifier,
    iconContent: @Composable ((modifier: Modifier) -> Unit)? = null,
    labelContent: @Composable ((modifier: Modifier) -> Unit)? = null,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable(enabled = true, onClick = { onClick() }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .padding(8.dp)
        ) {
            iconContent?.let { content ->
                content(
                    Modifier
                        .aspectRatio(1f)
                        .fillMaxSize()
                    )
            }
        }

        labelContent?.let { content ->
            content(
                Modifier
                    .wrapContentHeight(unbounded = false)
                    .heightIn(0.dp, 36.dp)
                    .padding(4.dp),
            )
        }
    }
}

@Composable
@Preview
fun TestNavHeader() {
    SelectiveTheme {
        NavigationHeaderComposable(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 64.dp),
            leftContent = {
                NavigationHeaderSideButton(
                    iconContent = {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxSize()
                                .widthIn(max = 48.dp),
                            painter = painterResource(R.drawable.ic_arrow_60_left),
                            colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                            contentDescription = ""
                        )
                    }
                )
            },
            rightContent = {
                NavigationHeaderSideButton(
                    iconContent = {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxSize()
                                .widthIn(max = 48.dp),
                            painter = painterResource(R.drawable.ic_arrow_60_right),
                            colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                            contentDescription = ""
                        )
                    }
                )
            },
            centerContent = {
                NavigationHeaderCenter(
                    modifier = Modifier
                        .fillMaxSize(),
                    textContent = {
                        BasicText(
                            modifier = Modifier
                                .weight(2f)
                                .wrapContentHeight(),
                            text = stringResource(R.string.objectives_title_optional_objective),
                            style = LocalTypography.current.primary.regular.copy(
                                color = LocalPalette.current.primary,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                        )
                    }
                )
            }
        )
    }
}

/*
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

        NavigationHeaderSideButton(
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
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
            )
        }

        NavigationHeaderSideButton(
            modifier = Modifier.weight(1f, fill = false),
            type = params.rightType,
            titleRes = params.rightTitleRes,
            onClick = { params.rightOnClick() }
        )

    }

}

@Composable
private fun RowScope.NavigationHeaderSideButton(
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

data class NavHeaderComposableParams(
    var leftType: PETImageButtonType = PETImageButtonType.NONE,
    var rightType: PETImageButtonType = PETImageButtonType.NONE,
    @param:StringRes var centerTitleRes: Int? = null,
    @param:StringRes var leftTitleRes: Int = leftType.labelRes,
    @param:StringRes var rightTitleRes: Int = rightType.labelRes,
    var leftOnClick: () -> Unit = {},
    var rightOnClick: () -> Unit = {},
)
*/

