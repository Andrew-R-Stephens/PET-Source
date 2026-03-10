package com.tritiumgaming.feature.investigation.ui.journal.evidence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItem
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItemUiAction
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItemUiState

@Composable
fun PrimaryEvidenceList(
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
                modifier = Modifier,
                state = EvidenceListItemUiState(
                    state = ruledEvidence.state,
                    label = stringResource(ruledEvidence.evidence.name.toStringResource()),
                    enabled = ruledEvidence.enabled
                ),
                actions = EvidenceListItemUiAction(
                    onToggle = { ruling ->
                        if (ruledEvidence.enabled) {
                            evidenceListUiActions.onChangeEvidenceRuling(
                                ruledEvidence.evidence, ruling
                            )
                        }
                    },
                    onNameClick = {
                        evidenceListUiActions.onClickItem(ruledEvidence.evidence)
                    }
                )
            )

        }

    }

}

@Composable
fun SecondaryEvidenceList(
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
                state = EvidenceListItemUiState(
                    state = ruledEvidence.state,
                    label = stringResource(ruledEvidence.evidence.name.toStringResource()),
                    enabled = ruledEvidence.enabled
                ),
                actions = EvidenceListItemUiAction(
                    onToggle = { ruling ->
                        evidenceListUiActions.onChangeEvidenceRuling(
                            ruledEvidence.evidence, ruling
                        )
                    },
                    onNameClick = {
                        evidenceListUiActions.onClickItem(ruledEvidence.evidence)
                    }
                )
            )

        }

    }

}
