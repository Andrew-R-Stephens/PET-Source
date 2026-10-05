package com.tritiumgaming.feature.investigation.ui.components.journal.evidence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumgaming.data.journal.model.EvidenceValidationType
import com.tritiumgaming.data.mappers.toStringResource
import com.tritiumgaming.feature.investigation.ui.components.journal.evidence.item.EvidenceListItem
import com.tritiumgaming.feature.investigation.ui.components.journal.evidence.item.EvidenceListItemUiAction
import com.tritiumgaming.feature.investigation.ui.components.journal.evidence.item.EvidenceListItemUiState


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
                modifier = Modifier
                    .fillMaxWidth(),
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
