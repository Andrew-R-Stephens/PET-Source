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
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType

@Composable
internal fun GhostList(
    modifier: Modifier = Modifier,
    ghostOrder: List<GhostState>,
    ghostEvidenceState: List<EvidenceState>,
    onGhostNameClick: (GhostResources.GhostIdentifier) -> Unit,
    onToggleNegateGhost: (Ghost) -> Unit,
    onRequestToolTip: () -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()

    LaunchedEffect(ghostOrder) {
        Log.d("GhostList", "LaunchedEffect: ghostOrder")
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                listState.animateScrollToItem(0)
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                evidenceState = ghostEvidenceState,
                ghostState = state,
                ghostListUiItemActions = GhostListUiItemActions(
                    onToggleNegateGhost = { onToggleNegateGhost(it) },
                    onRequestToolTip = { onRequestToolTip() }
                ).copy (
                    onNameClick = { onGhostNameClick(state.ghostEvidence.ghost.id) }
                )
            )
        }

    }

}

internal data class GhostListUiActions(
    val onNameClick: (GhostResources.GhostIdentifier) -> Unit = {}
)

internal data class GhostListUiState(
    val ghostOrder: List<GhostState>,
    val evidenceState: List<EvidenceState>
)
