package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.runtime.Composable
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
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
                text = "Phase:")
            TextSubTitle(
                color = LocalPalette.current.onSurface,
                text = state.type.name)
        }
    }

}
