package com.tritiumgaming.feature.investigation.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalUiConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.PrimaryEvidenceList
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostList
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType


@Composable
internal fun JournalComponent(
    modifier: Modifier = Modifier,
    evidenceStateList: List<EvidenceState>,
    ghostOrder: List<GhostState>,
    ghostEvidenceState: List<EvidenceState>,
    onGhostNameClick: (GhostResources.GhostIdentifier) -> Unit,
    onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit,
    onEvidenceClick: (evidence: EvidenceType) -> Unit,
    onToggleNegateGhost: (Ghost) -> Unit,
    onRequestToolTip: () -> Unit,
) {

    val uiIsRtl = LocalUiConfiguration.current.isRtl

    val evidenceListComponent: @Composable (Modifier) -> Unit = { modifier ->
        EvidenceListColumn(
            modifier = modifier,
            evidenceStateList = evidenceStateList,
            onChangeEvidenceRuling = onChangeEvidenceRuling,
            onEvidenceClick = onEvidenceClick
        )
    }

    val ghostListComponent: @Composable (Modifier) -> Unit = { modifier ->
        GhostListColumn(
            modifier = modifier,
            ghostOrder = ghostOrder,
            ghostEvidenceState = ghostEvidenceState,
            onGhostNameClick = onGhostNameClick,
            onToggleNegateGhost = onToggleNegateGhost,
            onRequestToolTip = onRequestToolTip
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {
        val listModifier = Modifier
            .weight(1f)
            .fillMaxHeight()
        if(uiIsRtl) {
            evidenceListComponent(listModifier)
            ghostListComponent(listModifier)
        } else {
            ghostListComponent(listModifier)
            evidenceListComponent(listModifier)
        }
    }

}


@Composable
private fun GhostListColumn(
    modifier: Modifier = Modifier,
    ghostOrder: List<GhostState>,
    ghostEvidenceState: List<EvidenceState>,
    onGhostNameClick: (GhostResources.GhostIdentifier) -> Unit,
    onToggleNegateGhost: (Ghost) -> Unit,
    onRequestToolTip: () -> Unit,
) {
    ListColumn(
        modifier = modifier,
        title = stringResource(R.string.investigation_section_title_ghosts)
    ) {
        GhostList(
            modifier = modifier,
            ghostOrder = ghostOrder,
            ghostEvidenceState = ghostEvidenceState,
            onGhostNameClick = onGhostNameClick,
            onToggleNegateGhost = onToggleNegateGhost,
            onRequestToolTip = onRequestToolTip
        )
    }
}

@Composable
private fun EvidenceListColumn(
    modifier: Modifier,
    evidenceStateList: List<EvidenceState>,
    onChangeEvidenceRuling: (evidence: EvidenceType, evidenceValidationType: EvidenceValidationType) -> Unit,
    onEvidenceClick: (evidence: EvidenceType) -> Unit,
) {
    ListColumn(
        modifier = modifier,
        title = stringResource(R.string.investigation_section_title_evidence),
    ) {
        PrimaryEvidenceList(
            modifier = modifier,
            evidenceStateList = evidenceStateList,
            onChangeEvidenceRuling = onChangeEvidenceRuling,
            onEvidenceClick = onEvidenceClick
        )
    }
}

@Composable
private fun ListColumn(
    modifier: Modifier = Modifier,
    title: String,
    listComponent: @Composable (Modifier) -> Unit
) {
    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(2.dp)
                .onSizeChanged { size = it },
            contentAlignment = Alignment.Center
        ) {
            key(size) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = title,
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.primary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )
            }
        }

        listComponent(
            Modifier
                .fillMaxWidth()
        )
    }
}

internal data class JournalStateBundle(
    val journalUiState: JournalUiState,
    val evidenceListUiState: EvidenceListUiState,
    val ghostListUiState: GhostListUiState
)

class JournalUiState(
    val rtlPreference: Boolean
)