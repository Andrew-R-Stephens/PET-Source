package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.utilities.codex.children.itemstore.fragments

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroups
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
fun ItemStorePreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EquipmentItemStore()
    }

}

@Composable
fun ItemStore(
    navHostController: NavHostController = rememberNavController(),
    @StringRes title: Int,
    data: ItemStoreGroups
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(LocalPalette.current.codexFamily.codex3_navHeaderBackground)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable(onClick = {
                        navHostController.popBackStack()
                    })
                    .padding(8.dp),
                painter = painterResource(android.R.drawable.ic_menu_revert),
                contentDescription = "Back Button",
                colorFilter = ColorFilter.tint(LocalPalette.current.codexFamily.codex2_navBackIcon)
            )

            BasicText(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                text = stringResource(title).uppercase(),
                autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 48.sp, stepSize = 2.sp),
                style = LocalTypography.current.quaternary.bold.copy(
                    color = LocalPalette.current.codexFamily.codex5_navHeaderText,
                    textAlign = TextAlign.Center
                )
            )
        }

        if(LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ConsoleWindow(data)
            }
        } else {
            Column {
                ConsoleWindow(data)
            }
        }

    }

}

@Composable
fun RowScope.ConsoleWindow(
    data: ItemStoreGroups
) {
    val columnState = rememberLazyListState()

    var paginatorColumnSize by remember { mutableStateOf(IntSize.Zero) }
    var itemsColumnSize by remember { mutableStateOf(IntSize.Zero) }

    var relativeVerticalTarget by remember { mutableFloatStateOf(0f) }
    var paginationItemTarget = (relativeVerticalTarget * data.groups.size).toInt()

    Log.d("ItemStore", "Console ${data.groups.size}")

    LaunchedEffect(relativeVerticalTarget) {
        columnState.scrollToItem(paginationItemTarget)
    }

    /*LaunchedEffect(columnState) {
        snapshotFlow { columnState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .take(2) // the 2nd one is user scroll
            .drop(1)
            .collect {
                relativeVerticalTarget = it.toFloat()
            }
    }*/

    Column(
        modifier = Modifier
            .width(48.dp)
            .fillMaxHeight()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp)
            .onSizeChanged { paginatorColumnSize = it }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        relativeVerticalTarget = it.y / size.height
                    },
                )
            }
            .pointerInput(Unit) {
                detectDragGestures (
                    onDragStart = {
                        relativeVerticalTarget = it.y / size.height
                    },
                    onDrag = { change, dragAmount ->
                        relativeVerticalTarget = change.position.y / size.height
                    }
                )
            }
    ) {
        for(i in 0 until data.groups.size) {
            val group = data.groups[i]

            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f, fill = true)
                    .padding(2.dp),
                painter = painterResource(group.paginationIcon),
                contentDescription = "${group.nameData}",
                tint = if(i == paginationItemTarget) {
                    LocalPalette.current.codexFamily.codex4_unsel
                } else {
                    LocalPalette.current.codexFamily.codex5_sel
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { itemsColumnSize = it },
        state = columnState
    ) {
        items(
            count = data.groups.size
        ) { index ->
            val group = data.groups[index]

            Text(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                text = stringResource(group.nameData),
                color = LocalPalette.current.textFamily.body
            )
        }
    }
}

@Composable
fun ColumnScope.ConsoleWindow(
    data: ItemStoreGroups
) {
    val rowState = rememberLazyListState()
    val horizontalScroll = rememberScrollState()

    LazyRow(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
        state = rowState
    ) {
        items(
            items = data.groups
        ) { item ->
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f, fill = true),
                text = stringResource(item.nameData)[0].toString(),
                color = LocalPalette.current.textFamily.body
            )
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(horizontalScroll),
        state = rowState
    ) {
        items(
            items = data.groups
        ) {
            Text(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp),
                text = stringResource(it.nameData)[0].toString(),
                color = LocalPalette.current.textFamily.body
            )
        }
    }

}

@Composable
fun EquipmentItemStore(
    investigationViewModel: InvestigationViewModel =
        viewModel( factory = InvestigationViewModel.Factory),
) {

    val data = investigationViewModel.equipmentStoreModel

    ItemStore(
        title = R.string.store_title_equipment,
        data = data
    )

}

@Composable
fun PossessionsItemStore(
    investigationViewModel: InvestigationViewModel =
        viewModel( factory = InvestigationViewModel.Factory),
) {

    val data = investigationViewModel.possessionsStoreModel

    ItemStore(
        title = R.string.store_title_cursedpossessions,
        data = data
    )

}

@Composable
fun AchievementsItemStore(
    investigationViewModel: InvestigationViewModel =
        viewModel( factory = InvestigationViewModel.Factory),
) {

    val data = investigationViewModel.achievementsStoreModel

    ItemStore(
        title = R.string.store_title_achievements,
        data = data
    )

}