package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.CategoryColumn
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.CategoryRow
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.phase.PhaseUiState
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
