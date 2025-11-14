package com.tritiumstudios.feature.investigation.ui.journal.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumstudios.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumstudios.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumstudios.feature.investigation.ui.journal.lists.item.EvidenceListItem
import org.jetbrains.annotations.TestOnly


@Composable
@Preview
@TestOnly
private fun EvidenceListPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EvidenceList(
            investigationViewModel = viewModel()
        )
    }
}

@Composable
fun EvidenceList(
    investigationViewModel: InvestigationScreenViewModel,
    onClickItem: (evidence: EvidenceType) -> Unit = {},
) {

    val evidenceState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        items(
            items = evidenceState.value,
            key = { it.evidence.id }
        ) { ruledEvidence ->

            EvidenceListItem(
                state = ruledEvidence.ruling,
                label = stringResource(ruledEvidence.evidence.name.toStringResource()),
                onToggle = { ruling ->
                    investigationViewModel.setEvidenceRuling(ruledEvidence.evidence, ruling)
                },
                onNameClick = {
                    onClickItem(ruledEvidence.evidence)
                }
            )

        }

    }

}