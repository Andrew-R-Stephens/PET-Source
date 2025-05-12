package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.SelectionState.Companion.Negative
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.SelectionState.Companion.Positive
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel

@Deprecated("Unused", level = DeprecationLevel.WARNING)
@Composable
fun RulingGroup(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel,
    groupIndex: Int = 0,
    onClick: () -> Unit = {}
) {
    val radioButtonState by investigationViewModel.evidenceRadioButtonUi.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            //.defaultMinSize(Dp.Unspecified, 48.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Ruling.entries.forEachIndexed { index, _ ->
            RulingSelector(
                investigationViewModel = investigationViewModel,
                groupIndex = groupIndex,
                rulingType = SelectionState(index),
                rulingState = index == radioButtonState[groupIndex],
                onSelection = {
                    onClick()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}

@Deprecated("Unused", level = DeprecationLevel.WARNING)
@Composable
fun RulingSelector(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel,
    groupIndex: Int = 0,
    rulingType: SelectionState = SelectionState.Neutral,
    rulingState: Boolean = true,
    onSelection: () -> Unit = {}
) {
    val radioButtons = investigationViewModel.evidenceRadioButtonUi.collectAsStateWithLifecycle()

    val negativeColor = LocalPalette.current.negativeSelColor
    val neutralColor = LocalPalette.current.neutralSelColor
    val positiveColor = LocalPalette.current.positiveSelColor

    val rulingDrawable =
        when(rulingType) {
            Negative -> Pair(R.drawable.ic_selector_neg_unsel, negativeColor)
            Positive -> Pair(R.drawable.ic_selector_pos_unsel, positiveColor)
            else -> Pair(R.drawable.ic_selector_inc_unsel, neutralColor)
        }

    IconButton(
        modifier = modifier,
        onClick = {
            investigationViewModel.checkEvidenceRadioButtonUi(groupIndex, rulingType.value)

            investigationViewModel.setEvidenceRuling(
                groupIndex, Ruling.entries.toTypedArray()[radioButtons.value[groupIndex]])

            onSelection()
        },
        content = {
            Image(
                painterResource(id = rulingDrawable.first),
                contentDescription = "Ruling Drawable",
                colorFilter = ColorFilter.tint(
                    if (rulingState) rulingDrawable.second
                    else neutralColor
                ),
                modifier = Modifier
                    .fillMaxSize(.8f)
            )

            if (rulingState) {
                Image(
                    painterResource(id = R.drawable.ic_selector_selected),
                    contentDescription = "State Drawable",
                    colorFilter = ColorFilter.tint(rulingDrawable.second)
                )
            }
        }
    )
}

@JvmInline
value class SelectionState(val value: Int) {
    companion object {
        val Negative = SelectionState(0)
        val Neutral = SelectionState(1)
        val Positive = SelectionState(2)

        fun values(): List<SelectionState> = listOf(Negative, Neutral, Positive)
    }
}