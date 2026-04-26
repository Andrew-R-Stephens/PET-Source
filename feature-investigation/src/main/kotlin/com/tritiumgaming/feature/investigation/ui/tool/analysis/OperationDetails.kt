package com.tritiumgaming.feature.investigation.ui.tool.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.ActiveGhostModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.DifficultyModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.MapModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.PhaseModifierDetails
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DEFAULT
import com.tritiumgaming.shared.data.investigation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.shared.data.phase.model.PhaseResources.PhaseIdentifier

@Composable
internal fun OperationDetails(
    modifier: Modifier = Modifier,
    operationDetailsUiState: OperationDetailsUiState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = stringResource(R.string.investigation_label_tool_operation_details).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }


        PhaseModifierDetails(
            state = operationDetailsUiState.phaseDetails
        )
        MapModifierDetails(
            state = operationDetailsUiState.mapDetails
        )
        DifficultyModifierDetails(
            operationDetails = operationDetailsUiState
        )
        ActiveGhostModifierDetails(
            state = operationDetailsUiState.ghostDetails
        )
    }
}

@Composable
internal fun CategoryColumn(
    containerColor: Color = Color.Unspecified,
    content: @Composable () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        content()
    }
}

@Composable
internal fun ExpandableCategoryColumn(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    expanded: Boolean = false,
    defaultContent: @Composable (modifier: Modifier, expanded: Boolean) -> Unit = { _, _ -> },
    expandedContent: @Composable (modifier: Modifier) -> Unit = {},
) {
    var rememberExpanded by remember { mutableStateOf(expanded) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    rememberExpanded = !rememberExpanded
                })
        ) {
            defaultContent(Modifier, rememberExpanded)
        }

        if(rememberExpanded) {
            expandedContent(Modifier)
        }
    }
}

@Composable
internal fun CategoryRow(
    containerColor: Color = Color.Unspecified,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
internal fun ExpandableCategoryRow(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    isExpanded: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalArrangement: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.(modifier: Modifier) -> Unit = {}
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalArrangement,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content(
            Modifier
                .weight(1f)
        )

        CollapseButton(
            modifier = Modifier
                .size(18.dp),
            isCollapsed = isExpanded,
            icon = R.drawable.ic_arrow_chevron_right,
            disabledRotationVertical = -90,
            disabledRotationHorizontal = -90,
            enabledRotationAddition = 180
        )
    }
}

@Composable
internal fun SubRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        content()
    }
}

@Composable
internal fun TextCategoryTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}

@Composable
internal fun TextSubTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}

internal data class OperationDetailsUiState(
    internal val mapDetails: MapDetails = MapDetails(),
    internal val difficultyDetails: DifficultyDetails = DifficultyDetails(),
    internal val phaseDetails: PhaseDetails = PhaseDetails(),
    internal val ghostDetails: GhostDetails = GhostDetails(),
    internal val weatherDetails: WeatherDetails = WeatherDetails()
) {

    internal data class WeatherDetails(
        internal val weather: Weather = Weather.RANDOM
    )

    internal data class DifficultyDetails(
        internal val type: DifficultyType = DifficultyType.AMATEUR,
        internal val difficultyTitle: DifficultyTitle = DifficultyTitle.AMATEUR,
        internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN,
        internal val challengeTitle: ChallengeResources.ChallengeTitle? = null,
        internal val settings: DifficultySettingsModel = DifficultySettingsModel()
    )

    internal data class MapDetails(
        internal val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
        internal val size: MapSize = MapSize.SMALL,
        internal val modifiers: MapModifiers = MapModifiers()
    ) {
        internal data class MapModifiers(
            internal val action: MapSizePhaseModifier = MapSizePhaseModifier.ACTION_SMALL,
            internal val setup: MapSizePhaseModifier = MapSizePhaseModifier.SETUP_SMALL,
        )
    }

    internal data class PhaseDetails(
        internal val type: PhaseIdentifier = PhaseIdentifier.SETUP,
        internal val canAlertAudio: Boolean = false,
        internal val canFlash: Boolean = true,
        internal val startFlashTime: Long = DEFAULT,
        internal val elapsedFlashTime: Long = DEFAULT,
        internal val maxFlashTime: Long = DURATION_30_SECONDS,
    )

    internal data class GhostDetails(
        internal val activeGhosts: List<GhostDetail> = emptyList(),
    ) {
        internal data class GhostDetail(
            internal val state: GhostState
        )
    }
}
