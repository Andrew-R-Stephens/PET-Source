package com.tritiumgaming.feature.operation.ui.investigation.journal

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.feature.operation.ui.investigation.journal.lists.EvidenceList
import com.tritiumgaming.feature.operation.ui.investigation.journal.lists.GhostList
import com.tritiumgaming.feature.operation.ui.investigation.journal.popups.EvidencePopup
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun JournalListsPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Journal(investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))
    }
}

@Composable
fun Journal(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {

        if(investigationViewModel.rTLPreference) {
            EvidenceListColumn(investigationViewModel = investigationViewModel)
            GhostListColumn(investigationViewModel = investigationViewModel)
        } else {
            GhostListColumn(investigationViewModel = investigationViewModel)
            EvidenceListColumn(investigationViewModel = investigationViewModel)
        }

    }

}

@Composable
private fun RowScope.GhostListColumn(
    investigationViewModel: InvestigationViewModel
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
                .height(48.dp)
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
                    color = LocalPalette.current.textFamily.tertiary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )
        }

        GhostList(
            investigationViewModel = investigationViewModel
        )
    }
}

@Composable
private fun RowScope.EvidenceListColumn(
    investigationViewModel: InvestigationViewModel
) {

    var rememberPopupEvidence by remember { mutableStateOf<EvidenceType?>(null) }

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    ) {

        EvidencePopup(
            investigationViewModel = investigationViewModel,
            evidence = rememberPopupEvidence
        )

        Column(
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = stringResource(R.string.investigation_section_title_evidence),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.tertiary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )
            }

            EvidenceList(
                investigationViewModel = investigationViewModel
            ) {
                rememberPopupEvidence = it
            }

        }
    }
}