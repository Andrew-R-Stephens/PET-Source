package com.tritiumgaming.feature.investigation.ui.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

        if(journalUiState.rtlPreference) {
            EvidenceListColumn(
                evidenceListUiState = evidenceListUiState,
                evidenceListUiActions = evidenceListUiActions
            )
            GhostListColumn(
                ghostListUiState = ghostListUiState,
                ghostListUiActions = ghostListUiActions,
                ghostListUiItemActions = ghostListUiItemActions
            )
        } else {
            GhostListColumn(
                ghostListUiState = ghostListUiState,
                ghostListUiActions = ghostListUiActions,
                ghostListUiItemActions = ghostListUiItemActions
            )
            EvidenceListColumn(
                evidenceListUiState = evidenceListUiState,
                evidenceListUiActions = evidenceListUiActions
            )
        }

    }

}

@Composable
private fun RowScope.GhostListColumn(
    ghostListUiState: GhostListUiState,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions
) {
    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    Column(
        modifier = Modifier
            .weight(1f, false)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .fillMaxHeight(),
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
                    text = stringResource(R.string.investigation_section_title_ghosts),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.primary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )
            }
        }

        GhostList(
            modifier = Modifier,
            ghostListUiState = ghostListUiState,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions
        )
    }
}

@Composable
private fun RowScope.EvidenceListColumn(
    evidenceListUiState: EvidenceListUiState,
    evidenceListUiActions: EvidenceListUiActions,
) {

    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .padding(2.dp)
                    .onSizeChanged { size = it },
                contentAlignment = Alignment.Center
            ) {
                key(size) {
                    BasicText(
                        text = stringResource(R.string.investigation_section_title_evidence),
                        style = LocalTypography.current.primary.regular.copy(
                            color = LocalPalette.current.primary,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                    )
                }
            }

            EvidenceList(
                evidenceListUiState = evidenceListUiState,
                evidenceListUiActions = evidenceListUiActions
            )

                /*onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
                onClickItem = {
                    Log.d("EvidenceList", "Setting popup to ${it.name}")
                    onChangePopup(it)
                }
            )*/

        }
    }
}