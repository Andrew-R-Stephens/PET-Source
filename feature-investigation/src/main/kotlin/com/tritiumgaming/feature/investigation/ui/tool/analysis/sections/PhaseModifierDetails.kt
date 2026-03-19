package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle

@Composable
internal fun PhaseModifierDetails(
    state: OperationDetailsUiState.PhaseDetails
) {

    CategoryColumn(
        containerColor = LocalPalette.current.surfaceContainer
    ) {
        CategoryRow {
            TextCategoryTitle(
                color = LocalPalette.current.onSurface,
                text = "${ stringResource(R.string.investigation_label_phase) }:"
            )
            TextSubTitle(
                color = LocalPalette.current.onSurfaceVariant,
                text = stringResource(state.type.toPhaseTitle().toStringResource())
            )
        }
    }

}
