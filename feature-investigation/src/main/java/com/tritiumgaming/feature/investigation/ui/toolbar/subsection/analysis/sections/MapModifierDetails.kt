package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.map.MapUiState
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@Composable
fun MapModifierDetails(
    state: StateFlow<MapUiState>,
) {

    val collectState = state.collectAsStateWithLifecycle()
    val rememberState by remember { mutableStateOf(collectState.value) }

    val mapName = rememberState.name.toStringResource()
    val mapSize = rememberState.size.toStringResource()
    val setupMapModifier = rememberState.setupModifier
    val normalMapModifier = rememberState.normalModifier

    ExpandableCategoryColumn(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = modifier,
                ) {
                    TextCategoryTitle(text = "Map: ")
                    TextSubTitle(text = stringResource(mapName))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(text = "Size: ")
                TextSubTitle(text = stringResource(mapSize))
            }
            SubRow {
                TextSubTitle(text = "Setup Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        setupMapModifier
                    )
                )
            }
            SubRow {
                TextSubTitle(text = "Action Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        normalMapModifier
                    )
                )
            }
        }
    }

}
