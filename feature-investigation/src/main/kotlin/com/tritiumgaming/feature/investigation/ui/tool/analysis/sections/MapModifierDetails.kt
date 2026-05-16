package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import com.tritiumgaming.shared.data.map.modifier.mappers.toFloat
import java.util.Locale

@Composable
internal fun MapModifierDetails(
    state: OperationDetailsUiState.MapDetails
) {

    ExpandableCategoryColumn(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = LocalPalette.current.surfaceContainer,
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    TextCategoryTitle(
                        color = LocalPalette.current.onSurface,
                        text = "${stringResource(R.string.investigation_timer_maplabel)}: ")
                    TextSubTitle(
                        color = LocalPalette.current.onSurfaceVariant,
                        text = stringResource(state.name.toStringResource()))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "${stringResource(R.string.map_setting_label_size)}: ")
                TextSubTitle(
                    color = LocalPalette.current.onSurfaceVariant,
                    text = stringResource(state.size.toStringResource()))
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "${stringResource(R.string.investigation_phase_label_setup)} " +
                            "${stringResource(R.string.map_setting_label_drainrate)}: ")
                TextSubTitle(
                    color = LocalPalette.current.onSurfaceVariant,
                    text = "${String.format(Locale.getDefault(), 
                        "%.2f", state.modifiers.setup.toFloat())}%/s"
                )
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "${stringResource(R.string.investigation_phase_label_action)} " +
                            "${stringResource(R.string.map_setting_label_drainrate)}: ")
                TextSubTitle(
                    color = LocalPalette.current.onSurfaceVariant,
                    text = "${String.format(Locale.getDefault(), 
                        "%.2f", state.modifiers.action.toFloat())}%/s"
                )
            }
        }
    }

}
