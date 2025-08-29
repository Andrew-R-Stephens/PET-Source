package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.menu.CodexMenuGhostLabel

@Composable
fun CodexScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    headerTitle: Int = R.string.general_codex_button,
    showBackButton: Boolean = false,
    onBackClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController
    ) {
        CodexScreenContent(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            headerTitle = headerTitle,
            showBackButton = showBackButton,
            onBackClicked = onBackClicked
        ) {
            content()
        }
    }

}

@Composable
private fun CodexScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    headerTitle: Int,
    showBackButton: Boolean = false,
    onBackClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(LocalPalette.current.codexFamily.codex3_navHeaderBackground)
        ) {
            if(showBackButton) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable(onClick = { onBackClicked() })
                        .padding(8.dp),
                    painter = painterResource(android.R.drawable.ic_menu_revert),
                    colorFilter = ColorFilter.tint(LocalPalette.current.codexFamily.codex2_navBackIcon),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(4.dp),
                text = stringResource(headerTitle).uppercase(),
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Center
                ),
                color = LocalPalette.current.codexFamily.codex5_navHeaderText,
                autoSize = TextAutoSize.StepBased(
                    1.sp, 50.sp, 2.sp),
                maxLines = 1
            )

            if(showBackButton) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) {}
            }
        }


        Box(
            modifier = Modifier
                .weight(1f, true)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(R.drawable.itemstore_grid),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(
                    blendMode = BlendMode.Darken,
                    color = LocalPalette.current.background.color.copy(alpha=.75f)
                )
            )

            content()

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalPalette.current.codexFamily.codex1_gh0stBackground),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            CodexMenuGhostLabel(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp)
            )

            AdmobBanner()
        }

    }

}
