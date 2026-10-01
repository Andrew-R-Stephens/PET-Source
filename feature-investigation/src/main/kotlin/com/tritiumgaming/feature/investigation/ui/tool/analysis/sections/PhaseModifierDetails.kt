package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextDataRow
import com.tritiumstudios.data.phase.mappers.toPhaseTitle
import com.tritiumstudios.data.phase.mappers.toStringResource

@Composable
internal fun PhaseModifierDetails(
    state: OperationDetailsUiState.PhaseDetails
) {
    CategoryColumn(
        containerColor = LocalPalette.current.surfaceContainer
    ) {
        TextDataRow(
            title = "${stringResource(R.string.investigation_label_phase)}:",
            data = stringResource(state.type.toPhaseTitle().toStringResource())
        )
    }
}
