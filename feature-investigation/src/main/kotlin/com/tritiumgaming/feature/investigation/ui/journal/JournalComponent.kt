package com.tritiumgaming.feature.investigation.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
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
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceList
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostList
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostListUiItemActions

@Composable
fun Journal(
    modifier: Modifier = Modifier,
    journalStateBundle: JournalStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions
) {
    val journalUiState = journalStateBundle.journalUiState
    val evidenceListUiState = journalStateBundle.evidenceListUiState
    val ghostListUiState = journalStateBundle.ghostListUiState

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {
        val evidenceListComponent: @Composable (Modifier) -> Unit = { modifier ->
            EvidenceListColumn(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize(),
                evidenceListUiState = evidenceListUiState,
                evidenceListUiActions = evidenceListUiActions
            )
        }

        val ghostListComponent: @Composable (Modifier) -> Unit = { modifier ->
            GhostListColumn(
                modifier = modifier
                    .weight(1f, false)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .fillMaxHeight(),
                ghostListUiState = ghostListUiState,
                ghostListUiActions = ghostListUiActions,
                ghostListUiItemActions = ghostListUiItemActions
            )
        }

        if(journalUiState.rtlPreference) {
            evidenceListComponent(Modifier)
            ghostListComponent(Modifier)
        } else {
            ghostListComponent(Modifier)
            evidenceListComponent(Modifier)
        }

    }

}

@Composable
private fun GhostListColumn(
    modifier: Modifier = Modifier,
    ghostListUiState: GhostListUiState,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions
) {
    ListColumn(
        modifier = modifier,
        title = stringResource(R.string.investigation_section_title_ghosts)
    ) {
        GhostList(
            modifier = Modifier,
            ghostListUiState = ghostListUiState,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions
        )
    }
}

@Composable
private fun EvidenceListColumn(
    modifier: Modifier,
    evidenceListUiState: EvidenceListUiState,
    evidenceListUiActions: EvidenceListUiActions,
) {
    ListColumn(
        modifier = modifier,
        title = stringResource(R.string.investigation_section_title_evidence)
    ) {
        EvidenceList(
            evidenceListUiState = evidenceListUiState,
            evidenceListUiActions = evidenceListUiActions
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
        Row(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(2.dp)
                .onSizeChanged { size = it },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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

        listComponent(Modifier)
    }
}
