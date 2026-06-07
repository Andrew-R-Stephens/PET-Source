package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextDataRow
import com.tritiumgaming.shared.data.map.modifier.mappers.toFloat

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
