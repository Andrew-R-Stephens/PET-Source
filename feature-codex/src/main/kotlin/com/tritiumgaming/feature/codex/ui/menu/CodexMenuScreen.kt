package com.tritiumgaming.feature.codex.ui.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.black
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.JetBrainsMonoTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.theme.white
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.feature.codex.ui.CodexScreen

@Composable
fun CodexMenuScreen(
    navController: NavHostController = rememberNavController()
) {

    CodexScreen(
        navController = navController
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                CodexMenuContentPortrait(
                    navController = navController
                )
            }

            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                CodexMenuContentLandscape(
                    navController = navController
                )
            }

            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                CodexMenuContentLandscape(
                    navController = navController
                )
            }

            DeviceConfiguration.DESKTOP -> {
                CodexMenuContentLandscape(
                    navController = navController
                )
            }
        }

    }

}

@Composable
private fun CodexMenuContentPortrait(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CodexMenuItem(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                title = R.string.codex_section_equipment,
                image = R.drawable.thumbnail_shop
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.EQUIPMENT.id}")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            )
        ) {

            CodexMenuItem(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                title = R.string.store_title_cursedpossessions,
                image = R.drawable.thumbnail_possessions
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.POSSESSIONS.id}")
            }

            CodexMenuItem(
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                title = R.string.codex_section_more,
                image = null
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.ACHIEVEMENTS.id}")
            }
        }
    }

}

@Composable
private fun CodexMenuContentLandscape(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterHorizontally
            )
        ) {
            CodexMenuItem(
                modifier = Modifier
                    .weight(.4f)
                    .height(64.dp),
                title = R.string.codex_section_equipment,
                image = R.drawable.thumbnail_shop
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.EQUIPMENT.id}")
            }

            CodexMenuItem(
                modifier = Modifier
                    .weight(.3f)
                    .height(64.dp),
                title = R.string.store_title_cursedpossessions,
                image = R.drawable.thumbnail_possessions
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.POSSESSIONS.id}")
            }

            CodexMenuItem(
                modifier = Modifier
                    .weight(.3f)
                    .height(64.dp),
                title = R.string.codex_section_more,
                image = null
            ) {
                navController.navigate(
                    route = "${NavRoute.SCREEN_CODEX_ITEM_SCREEN.route}/" +
                            "${CodexResources.Category.ACHIEVEMENTS.id}")
            }

        }

    }

}

@Composable
private fun CodexMenuItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes image: Int? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = LocalPalette.current.codexFamily.codex3
        ),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(LocalPalette.current.scrim)
        ) {

            GridIcon(
                modifier = Modifier
                    .fillMaxSize(),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6,
                    strokeColor = LocalPalette.current.codexFamily.codex7
                ),
                contentScale = ContentScale.FillBounds
            )
            /*Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(R.drawable.itemstore_grid),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )*/

            image?.let { background ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(background),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    alpha = .5f
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        text = stringResource(title).uppercase(),
                        style = LocalTypography.current.primary.regular.copy(
                            textAlign = TextAlign.Center,
                            drawStyle = Stroke(
                                miter = 10f,
                                width = 7f,
                                join = StrokeJoin.Round
                            )
                        ),
                        color = black,
                        autoSize = TextAutoSize.StepBased(
                            1.sp, 50.sp, 2.sp
                        ),
                        maxLines = 1
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        text = stringResource(title).uppercase(),
                        style = LocalTypography.current.primary.regular.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = white,
                        autoSize = TextAutoSize.StepBased(
                            1.sp, 50.sp, 2.sp
                        ),
                        maxLines = 1
                    )

                }

                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(
                            LocalPalette.current.codexFamily.codex3
                        )
                        .padding(4.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .width(12.dp)
                            .aspectRatio(1f),
                        painter = painterResource(R.drawable.ic_arrow_topright),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = LocalPalette.current.codexFamily.codex1
                        )
                    )
                }

            }
        }
    }
}

@Composable
fun CodexMenuGhostLabel(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "GH",
            style = JetBrainsMonoTypography.primary.bold,
            color = LocalPalette.current.codexFamily.codex3,
            fontSize = 12.sp,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = "//OS.T",
            style = JetBrainsMonoTypography.primary.bold,
            color = LocalPalette.current.codexFamily.codex4,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun CodexMenuGhostLabelPreview() {

    SelectiveTheme {
        CodexMenuGhostLabel()
    }
}

@Composable
@Preview
private fun CodexMenuContentPreview() {

    SelectiveTheme(

    ) {
        CodexMenuScreen()
    }

}