package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.EvidenceListItem
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
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
    investigationViewModel: InvestigationViewModel,
    onTriggerPopup: (evidence: EvidenceType) -> Unit = {},
) {

    val evidenceState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()
    //val rememberState = remember { evidenceState.value }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        items(items = evidenceState.value, key = { it.evidence.id }) { ruledEvidence ->

            EvidenceListItem(
                investigationViewModel = investigationViewModel,
                evidence = ruledEvidence.evidence
            ) {
                onTriggerPopup(ruledEvidence.evidence)
            }

        }

    }

}