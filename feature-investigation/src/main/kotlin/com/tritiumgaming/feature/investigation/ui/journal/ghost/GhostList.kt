package com.tritiumgaming.feature.investigation.ui.journal.ghost

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostListItem
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostListUiItemActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GhostList(
    modifier: Modifier = Modifier,
    ghostListUiState: GhostListUiState,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()

    val ghostOrder = ghostListUiState.ghostOrder
    val ruledEvidence = ghostListUiState.evidenceState

    LaunchedEffect(ghostOrder) {
        Log.d("GhostList", "LaunchedEffect: ghostOrder")
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
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(
            items = ghostOrder,
            key = { it.ghostEvidence.ghost.id }
        ) { state ->
            GhostListItem(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .animateItem(),
                evidenceState = ruledEvidence,
                ghostState = state,
                ghostListUiItemActions = ghostListUiItemActions.copy (
                    onNameClick = { ghostListUiActions.onNameClick(state.ghostEvidence.ghost.id) }
                )
            )
        }

    }

}