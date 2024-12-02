package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Negative
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Neutral
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Positive

@Composable
fun RulingGroup(
    modifier: Modifier = Modifier,
    investigationModel: InvestigationModel,
    groupIndex: Int = 0,
    onClick: () -> Unit = {}
) {
    val radioButtonState by investigationModel.radioButtonsChecked.collectAsState()

    Row(
        modifier = modifier
            //.defaultMinSize(Dp.Unspecified, 48.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        EvidenceModel.Ruling.entries.forEachIndexed { index, _ ->
            RulingSelector(
                investigationModel = investigationModel,
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

@Composable
fun RulingSelector(
    modifier: Modifier = Modifier,
    investigationModel: InvestigationModel,
    groupIndex: Int = 0,
    rulingType: SelectionState = Neutral,
    rulingState: Boolean = true,
    onSelection: () -> Unit = {}
) {
    val radioButtons = investigationModel.radioButtonsChecked.collectAsState()

    val negativeColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.negativeSelColor))
    val neutralColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.neutralSelColor))
    val positiveColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.positiveSelColor))

    val rulingDrawable =
        when(rulingType) {
            Negative -> Pair(R.drawable.ic_selector_neg_unsel, negativeColor)
            Positive -> Pair(R.drawable.ic_selector_pos_unsel, positiveColor)
            else -> Pair(R.drawable.ic_selector_inc_unsel, neutralColor)
    }

    IconButton(
        modifier = modifier,
        onClick = {
            investigationModel.setRadioButtonChecked(groupIndex, rulingType.value)
            investigationModel.evidenceRepository.evidenceList[groupIndex].ruling =
                EvidenceModel.Ruling.entries.toTypedArray()[radioButtons.value[groupIndex]]
            investigationModel.ghostScoreModel.updateOrder()

            Log.d("Updated",
                investigationModel.ghostScoreModel.currOrder
                    .contentToString())

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