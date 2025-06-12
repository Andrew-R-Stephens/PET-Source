package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.EvidenceList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.GhostList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.popups.EvidencePopup
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun JournalListsPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Journal()
    }
}

@Composable
fun Journal(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory)
) {

    val rtlState = globalPreferencesViewModel.rTLPreference.collectAsStateWithLifecycle()
    val rememberRTL by remember { mutableStateOf(rtlState.value) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {

        if(rememberRTL) {
            EvidenceListColumn()
            GhostListColumn()
        } else {
            GhostListColumn()
            EvidenceListColumn()
        }

    }

}

@Composable
private fun RowScope.GhostListColumn() {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize(),
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
                text = stringResource(R.string.investigation_section_title_ghosts),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.tertiary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )
        }

        GhostList()
    }
}

@Composable
private fun RowScope.EvidenceListColumn() {

    var rememberPopupEvidence by remember { mutableStateOf<EvidenceType?>(null) }

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    ) {

        EvidencePopup(
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

            EvidenceList {
                rememberPopupEvidence = it
            }

        }
    }
}