package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.util.Log
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Negative
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Neutral
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Positive
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    var isRunning by remember{ mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if(isRunning) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioLowBouncy
        ),
        finishedListener = {
            isRunning = false
        },
        label = "",
    )

    val foregroundColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

    Box(
        modifier = modifier
            .size(48.dp)
            .border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
                isRunning = true
            }
    )
    {
        Image(
            painterResource(id = R.drawable.icon_control_reset),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize(.55f)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(
                    if (isRunning) {
                        rotation * -360f
                    } else {
                        0f
                    }
                )
        )
    }
}

@Composable
fun CollapseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isCollapsedState: StateFlow<Boolean>,
    orientation: Int = ORIENTATION_PORTRAIT
) {
    val collapsedState by isCollapsedState.collectAsState()

    val rotation by animateFloatAsState(
        targetValue = if(collapsedState) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioNoBouncy
        ),
        label = "",
    )

    val foregroundColor = ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.textColorBody)

    Box(
        modifier = modifier
            .size(48.dp)
            .border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {
        val orientationRotate = when(orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.icon_arrow_fill_right),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(Color(foregroundColor)),
            modifier = Modifier
                .fillMaxSize(.55f)
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(orientationRotate + (rotation * 180f))
        )
    }
}

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
            Negative -> Pair(R.drawable.icon_selector_neg_unsel, negativeColor)
            Positive -> Pair(R.drawable.icon_selector_pos_unsel, positiveColor)
            else -> Pair(R.drawable.icon_selector_inc_unsel, neutralColor)
    }

    IconButton(
        modifier = modifier,
        onClick = {
            investigationModel.setRadioButtonChecked(groupIndex, rulingType.value)
            investigationModel.evidenceListModel.evidenceList[groupIndex].ruling =
                EvidenceModel.Ruling.entries.toTypedArray()[radioButtons.value[groupIndex]]
            investigationModel.ghostOrderModel.updateOrder()

            Log.d("Updated",
                investigationModel.ghostOrderModel.currOrder
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
                    painterResource(id = R.drawable.icon_selector_selected),
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