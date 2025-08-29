package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexAchievementDisplay
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexAchievementListComponent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexEquipmentDisplay
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexEquipmentListComponent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexPossessionsDisplay
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages.CodexPossessionsListComponent

@Composable
fun CodexItemstoreScreen(
    navController: NavHostController = rememberNavController(),
    codexViewModel: CodexViewModel,
    category: CodexResources.Category
) {

    val categoryTitle = when(category) {
        CodexResources.Category.EQUIPMENT -> R.string.store_title_equipment
        CodexResources.Category.POSSESSIONS -> R.string.store_title_cursedpossessions
        CodexResources.Category.ACHIEVEMENTS -> R.string.store_title_achievements
    }

    codexViewModel.cacheCategory(category)

    CodexScreen(
        navController = navController,
        headerTitle = categoryTitle,
        showBackButton = true,
        onBackClicked = {
            codexViewModel.flushCategory(category)
            navController.popBackStack()
        }
    ) {
        CodexItemScreenContent(
            codexViewModel = codexViewModel,
            category = category
        )
    }

}

@Composable
private fun CodexItemScreenContent(
    codexViewModel: CodexViewModel,
    category: CodexResources.Category
) {

    Row {
        VerticalPaginator(
            modifier = Modifier
                .fillMaxHeight()
                .width(48.dp),
            images = codexViewModel.getCategoryIcons(category),
        )

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            when(category) {
                CodexResources.Category.EQUIPMENT -> CodexEquipmentListComponent(
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsListComponent(
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.ACHIEVEMENTS -> CodexAchievementListComponent(
                    codexViewModel = codexViewModel
                )
            }

            when(category) {
                CodexResources.Category.EQUIPMENT -> CodexEquipmentDisplay(
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsDisplay(
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.ACHIEVEMENTS -> CodexAchievementDisplay(
                    codexViewModel = codexViewModel
                )
            }
        }
    }


}

@Composable
private fun VerticalPaginator(
    modifier: Modifier = Modifier,
    @DrawableRes images: List<Int>,
    offset: Float = 0.0f,
    onTouch: (offset: Float) -> Unit = {}
) {
    var rememberTouchOffset by remember {
        mutableFloatStateOf(offset)
    }

    var paginatorHeight by remember {
        mutableFloatStateOf(0f)
    }

    val offsetPercent = (rememberTouchOffset / paginatorHeight).coerceIn(0f, 1f)
    val selectedItem = (images.size * offsetPercent).toInt()

    Column(
        modifier = modifier
            .background(LocalPalette.current.background.color)
            .padding(8.dp)
            .onSizeChanged { paginatorHeight = it.height.toFloat() }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    rememberTouchOffset = change.position.y.coerceIn(0f, paginatorHeight-1)
                    onTouch(offsetPercent)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    rememberTouchOffset = it.y.coerceIn(0f, paginatorHeight-1)
                    onTouch(offsetPercent)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        images.forEachIndexed { index, image ->
            Image(
                modifier = Modifier
                    .weight(1f, true)
                    .aspectRatio(1f)
                    .padding(vertical = 2.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    if (index == selectedItem)
                        LocalPalette.current.codexFamily.codex4
                    else {
                        LocalPalette.current.codexFamily.codex5
                    }
                )
            )
        }
    }
}

@Composable
@Preview
private fun PreviewVerticalPaginator() {
    SelectiveTheme {
        VerticalPaginator(
            modifier = Modifier
                .fillMaxHeight()
                .width(48.dp),
            images = listOf(
                R.drawable.icon_sh_headgear,
                R.drawable.icon_sh_headgear,
                R.drawable.icon_sh_headgear,
                R.drawable.icon_sh_headgear,
            )
        )
    }
}