package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.evidence

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
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
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory)
) {

    val evidenceState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()
    val rememberState = remember { evidenceState.value }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(items = rememberState, key = { it.evidence.id }) { ruledEvidence ->

            EvidenceListItem(
                evidence = ruledEvidence.evidence
            )

        }

    }

}