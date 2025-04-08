package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.ghost

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.GhostScoreHandler
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel

@Composable
fun GhostListItem(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory),
    ghostScore: GhostScoreHandler.GhostScore? = null,
    label: String = ghostScore?.let { stringResource(ghostScore.ghostModel.name) } ?: "Test"
) {

    val scoreState = ghostScore?.score?.collectAsStateWithLifecycle()
    val rememberScore by remember { mutableStateOf(scoreState) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, maxFontSize = 36.sp, stepSize = 5.sp)
            )

            rememberScore?.value?.let {
                if(it >= 3) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_selector_selected),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Evidence Icon",
                        colorFilter = ColorFilter.tint(LocalPalette.current.positiveSelColor)
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            val evidenceList = ghostScore?.ghostModel?.evidence?.normalEvidenceList

            if (evidenceList != null) {
                for (evidenceItem in evidenceList) {

                    EvidenceIcon(evidence = evidenceItem)

                }
            }

        }
    }
}

@Composable
private fun RowScope.EvidenceIcon(
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory),
    evidence: EvidenceModel
) {

    val rulingState = investigationViewModel.getRuledEvidence(evidence)?.ruling?.collectAsStateWithLifecycle()
    val rememberRuling by remember {
        mutableStateOf(rulingState)
    }

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .weight(.25f)
            .padding(2.dp),
        contentScale = ContentScale.Fit,
        painter = painterResource(id = evidence.icon),
        contentDescription = "Evidence Icon",
        colorFilter = ColorFilter.tint(
            when (rememberRuling?.value) {
                Ruling.NEGATIVE -> LocalPalette.current.negativeSelColor
                Ruling.POSITIVE -> LocalPalette.current.positiveSelColor
                else -> LocalPalette.current.neutralSelColor
            }
        )
    )
}