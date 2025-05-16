package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.EvidenceListItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly


@Composable
@Preview
@TestOnly
private fun EvidenceListPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EvidenceList()
    }
}

@Composable
fun EvidenceList(
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory),
    onTriggerPopup: (evidence: EvidenceType) -> Unit = {},
) {

    val evidenceState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()
    //val rememberState = remember { evidenceState.value }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(items = evidenceState.value, key = { it.evidence.id }) { ruledEvidence ->

            EvidenceListItem(
                evidence = ruledEvidence.evidence
            ) {
                onTriggerPopup(ruledEvidence.evidence)
            }

        }

    }

}