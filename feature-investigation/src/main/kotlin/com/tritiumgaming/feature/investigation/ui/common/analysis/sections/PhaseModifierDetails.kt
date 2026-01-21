package com.tritiumgaming.feature.investigation.ui.common.analysis.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.feature.investigation.ui.common.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.common.analysis.CategoryRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.common.phase.PhaseUiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PhaseModifierDetails(
    state: StateFlow<PhaseUiState>,
) {

    val collectState = state.collectAsStateWithLifecycle()
    val rememberState by remember { mutableStateOf(collectState.value) }

    CategoryColumn {
        CategoryRow {
            TextCategoryTitle(text = "Phase:")
            TextSubTitle(text = rememberState.currentPhase.name)
        }
    }

}

@Composable
fun PhaseModifierDetails(
    phaseUiState: PhaseUiState
) {

    CategoryColumn {
        CategoryRow {
            TextCategoryTitle(text = "Phase:")
            TextSubTitle(text = phaseUiState.currentPhase.name)
        }
    }

}
