package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiColors
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType


@Composable
internal fun DifficultyConfigControl(
    modifier: Modifier = Modifier,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    colors: OperationConfigUiColors,
    onDropdownSelect: (Int) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular

    val icon: @Composable (Modifier) -> Unit = { modifier ->

        CommonTooltip(
            modifier = Modifier,
            tooltipText = stringResource(R.string.investigation_timer_difficulty_label)
        ) {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(R.drawable.ic_puzzle),
                colorFilter = ColorFilter.tint(colors.onColor),
                contentDescription = ""
            )
        }
    }

    OperationConfigDropdown(
        modifier = modifier,
        options = dropdownOptions,
        enabled = isDropdownEnabled,
        label = dropdownLabel,
        onSelect = onDropdownSelect,
        icon = { icon(it) },
        textStyle = textStyle,
        expandedColor = colors.color,
        onColor = colors.onColor,
    )
}

@Composable
internal fun DifficultyChallengeLabel(
    modifier: Modifier = Modifier,
    label: Int,
    colors: OperationConfigUiColors,
) {
    val textStyle = LocalTypography.current.quaternary.regular

    Text(
        modifier = modifier
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                animationMode = MarqueeAnimationMode.Immediately,
                initialDelayMillis = 1000,
                repeatDelayMillis = 1000
            ),
        text = stringResource(label),
        style = textStyle,
        color = colors.onColor,
        fontSize = 14.sp,
        maxLines = 1
    )
}

internal data class DifficultyConfigUiState(
    internal val type: DifficultyType = DifficultyType.AMATEUR,
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    internal val challengeTitle: ChallengeResources.ChallengeTitle? = null,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)