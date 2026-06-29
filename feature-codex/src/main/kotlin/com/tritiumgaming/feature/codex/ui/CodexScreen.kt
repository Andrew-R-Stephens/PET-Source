package com.tritiumgaming.feature.codex.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.GridIcon
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.admob.BannerAd
import com.tritiumgaming.feature.codex.ui.menu.CodexMenuGhostLabel

@Composable
fun CodexScreen(
    modifier: Modifier = Modifier,
    codexScreenUiState: CodexScreenUiState,
    codexScreenUiActions: CodexScreenUiActions,
    content: @Composable () -> Unit
) {

    CodexScreenContent(
        modifier = modifier
            .fillMaxSize(),
        codexScreenUiState = codexScreenUiState,
        codexScreenUiActions = codexScreenUiActions
    ) {
        content()
    }

}

@Composable
private fun CodexScreenContent(
    modifier: Modifier = Modifier,
    codexScreenUiState: CodexScreenUiState,
    codexScreenUiActions: CodexScreenUiActions,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(LocalPalette.current.codexFamily.onSurface)
        ) {
            if(codexScreenUiState.showBackButton) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable(onClick = { codexScreenUiActions.onBackClicked() })
                        .padding(8.dp),
                    painter = painterResource(android.R.drawable.ic_menu_revert),
                    colorFilter = ColorFilter.tint(LocalPalette.current.codexFamily.surfaceContainer),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(4.dp),
                text = codexScreenUiState.headerTitle.uppercase(),
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Center
                ),
                color = LocalPalette.current.codexFamily.onSurfaceVariant,
                autoSize = TextAutoSize.StepBased(
                    1.sp, 50.sp, 2.sp),
                maxLines = 1
            )

            if(codexScreenUiState.showBackButton) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) { }
            }
        }


        Box(
            modifier = Modifier
                .weight(1f, true)
        ) {
            GridIcon(
                modifier = Modifier
                    .fillMaxSize(),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.secondaryContainer,
                    strokeColor = LocalPalette.current.codexFamily.onSecondaryContainer
                ),
                contentScale = ContentScale.FillBounds
            )

            content()

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalPalette.current.codexFamily.surface),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            CodexMenuGhostLabel(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            )

            BannerAd(
                modifier = Modifier
                    .fillMaxWidth(),
                adId = stringResource(R.string.ad_banner_1)
            )

            // AdmobBanner()
        }

    }

}

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
@Preview
private fun CodexScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            CodexScreenContent(
                codexScreenUiState = CodexScreenUiState(
                    headerTitle = "Codex Header",
                    showBackButton = true
                ),
                codexScreenUiActions = CodexScreenUiActions(
                    onBackClicked = {}
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Codex Content",
                        style = LocalTypography.current.primary.regular,
                        color = LocalPalette.current.onSurface
                    )
                }
            }
        }
    }
}
