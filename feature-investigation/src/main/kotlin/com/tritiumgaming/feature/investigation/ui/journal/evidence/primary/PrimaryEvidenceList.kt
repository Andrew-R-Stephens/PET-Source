package com.tritiumgaming.feature.investigation.ui.journal.evidence.primary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItem
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItemUiAction
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item.EvidenceListItemUiState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType


@Composable
internal fun PrimaryEvidenceList(
    modifier: Modifier = Modifier,
    evidenceStateList: List<EvidenceState>,
    onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit,
    onEvidenceClick: (evidence: EvidenceType) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(
            items = evidenceStateList,
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
                            onChangeEvidenceRuling(
                                ruledEvidence.evidence, ruling
                            )
                        }
                    },
                    onNameClick = {
                        onEvidenceClick(ruledEvidence.evidence)
                    }
                )
            )

        }

    }

}

internal data class EvidenceListUiActions(
    val onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit = { _, _ -> },
    val onClickItem: (evidence: EvidenceType) -> Unit = {}
)
internal data class EvidenceListUiState(
    val evidenceStateList: List<EvidenceState>
)