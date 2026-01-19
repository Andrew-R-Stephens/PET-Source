package com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.CategoryRow
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.common.phase.PhaseUiState
import com.tritiumgaming.shared.data.phase.model.Phase
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
    currentPhase: Phase,
    canAlertAudio: Boolean,
    canFlash: Boolean,
    startFlashTime: Long,
    elapsedFlashTime: Long,
    maxFlashTime: Long
) {

    CategoryColumn {
        CategoryRow {
            TextCategoryTitle(text = "Phase:")
            TextSubTitle(text = currentPhase.name)
        }
    }

}
