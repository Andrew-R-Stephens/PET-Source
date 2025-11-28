package com.tritiumstudios.feature.investigation.ui.journal.lists

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumstudios.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumstudios.feature.investigation.ui.journal.lists.item.GhostListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly


@Composable
@Preview
@TestOnly
private fun GhostListPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        GhostList(investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory))
    }
}

@Composable
fun GhostList(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    onClickItem: (ghost: GhostType) -> Unit = {},
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    val listState = rememberLazyListState()

    val ghostsScoreState = investigationViewModel.ghostScores.collectAsStateWithLifecycle()
    val ghostsOrderState = investigationViewModel.ghostOrder.collectAsStateWithLifecycle()

    //Log.d("GhostList", "Ghost Scores: ${ghostsScoreState.value.size}")

    val rememberOrderState by remember { ghostsOrderState }

    LaunchedEffect(rememberOrderState) {

        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                listState.animateScrollToItem(0)
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .wrapContentWidth(Alignment.CenterHorizontally),
        state = listState
    ) {

        items(
            items = ghostsOrderState.value,
            key = { it }
        ) {

            investigationViewModel.getGhostById(it)?.let { ghostType ->

                //Log.d("GhostList", "Ghost Found: ${ghostModel.id}")

                GhostListItem(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .animateItem(),
                    investigationViewModel = investigationViewModel,
                    ghostScore = ghostsScoreState.value.find { score ->
                        score.ghostEvidence.ghost.id == ghostType.id }
                ) {
                    onClickItem(ghostType)
                }

            } ?: Log.d("GhostList", "No ghost found for id: $it")


        }

    }

}