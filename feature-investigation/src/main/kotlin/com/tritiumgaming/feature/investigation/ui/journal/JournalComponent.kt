package com.tritiumgaming.feature.investigation.ui.journal

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumgaming.feature.investigation.ui.journal.lists.EvidenceList
import com.tritiumgaming.feature.investigation.ui.journal.lists.GhostList
import com.tritiumgaming.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType

@Composable
fun Journal(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    ruledEvidenceList: List<RuledEvidence>,
    ghostsScore: List<GhostScore>,
    ghostsOrder: List<GhostResources.GhostIdentifier>,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangeEvidencePopup: (EvidenceType) -> Unit,
    onChangeGhostPopup: (GhostType) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {

        if(investigationViewModel.rTLPreference) {
            EvidenceListColumn(
                ruledEvidenceList = ruledEvidenceList,
                onChangeEvidenceRuling = { e, r ->
                    onChangeEvidenceRuling(e, r)
                },
                onChangePopup = { evidenceType ->
                    onChangeEvidencePopup(evidenceType)
                }
            )
            GhostListColumn(
                investigationScreenViewModel = investigationViewModel,
                ghostScoreState = ghostsScore,
                ghostsOrderState = ghostsOrder,
                onChangePopup = { ghostType ->
                    onChangeGhostPopup(ghostType)
                }
            )
        } else {
            GhostListColumn(
                investigationScreenViewModel = investigationViewModel,
                ghostScoreState = ghostsScore,
                ghostsOrderState = ghostsOrder,
                onChangePopup = { ghostType ->
                    onChangeGhostPopup(ghostType)
                }
            )
            EvidenceListColumn(
                ruledEvidenceList = ruledEvidenceList,
                onChangeEvidenceRuling = { e, r ->
                    onChangeEvidenceRuling(e, r)
                },
                onChangePopup = { evidenceType ->
                    onChangeEvidencePopup(evidenceType)
                }
            )
        }

    }

}

@Composable
private fun RowScope.GhostListColumn(
    investigationScreenViewModel: InvestigationScreenViewModel,
    ghostScoreState: List<GhostScore>,
    ghostsOrderState: List<GhostResources.GhostIdentifier>,
    onChangePopup: (GhostType) -> Unit
) {
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
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

        GhostList(
            investigationViewModel = investigationScreenViewModel,
            ghostsScoreState = ghostScoreState,
            ghostsOrderState = ghostsOrderState
        ) {
            Log.d("GhostList", "Setting popup to ${it.name}")
            onChangePopup(it)
        }
    }
}

@Composable
private fun RowScope.EvidenceListColumn(
    ruledEvidenceList: List<RuledEvidence>,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangePopup: (EvidenceType) -> Unit = {}
) {

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
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
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

            EvidenceList(
                ruledEvidenceList = ruledEvidenceList,
                onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
                onClickItem = {
                    Log.d("EvidenceList", "Setting popup to ${it.name}")
                    onChangePopup(it)
                }
            )

        }
    }
}