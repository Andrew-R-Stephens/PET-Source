package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item.EvidenceListItem
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item.EvidenceListItemUiAction
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item.EvidenceListItemUiState

@Composable
fun EvidenceList(
    evidenceListUiState: EvidenceListUiState,
    evidenceListUiActions: EvidenceListUiActions
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        items(
            items = evidenceListUiState.evidenceStateList,
            key = { it.evidence.id }
        ) { ruledEvidence ->

            EvidenceListItem(
                evidenceListItemUiState = EvidenceListItemUiState(
                    state = ruledEvidence.state,
                    label = stringResource(ruledEvidence.evidence.name.toStringResource())
                ),
                evidenceListItemUiAction = EvidenceListItemUiAction(
                    onToggle = { ruling ->
                        evidenceListUiActions.onChangeEvidenceRuling(
                            ruledEvidence.evidence, ruling)
                    },
                    onNameClick = {
                        evidenceListUiActions.onClickItem(ruledEvidence.evidence)
                    }
                )
            )

        }

    }

}
