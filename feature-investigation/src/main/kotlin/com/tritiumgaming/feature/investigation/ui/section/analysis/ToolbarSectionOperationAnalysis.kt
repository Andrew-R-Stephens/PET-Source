package com.tritiumgaming.feature.investigation.ui.section.analysis

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState

@Composable
fun ToolbarSectionOperationAnalysis(
    modifier: Modifier = Modifier,
    operationDetailsUiState: OperationDetailsUiState
) {
    Box (
        modifier = modifier
    ) {
        OperationDetails(
            modifier = modifier,
            operationDetailsUiState = operationDetailsUiState
        )
    }
}
