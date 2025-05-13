package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.sections.evidence

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

    val listState = rememberLazyListState()

    val evidenceState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()
    val rememberState by remember { evidenceState }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {

        items(
            items = rememberState,
            key = { it.evidence.id }
        ) { ruledEvidence ->

            EvidenceListItem(
                evidence = ruledEvidence.evidence
            )

        }

    }

}