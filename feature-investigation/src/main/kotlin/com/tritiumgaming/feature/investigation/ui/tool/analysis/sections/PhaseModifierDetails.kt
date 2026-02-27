package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.runtime.Composable
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle

@Composable
internal fun PhaseModifierDetails(
    state: OperationDetailsUiState.PhaseDetails
) {

    CategoryColumn {
        CategoryRow {
            TextCategoryTitle(text = "Phase:")
            TextSubTitle(text = state.type.name)
        }
    }

}
