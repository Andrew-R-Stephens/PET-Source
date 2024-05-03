package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.Evidence
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.Ghost
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.TextCase
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Negative
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Neutral
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Positive
import com.google.android.play.integrity.internal.i
import com.google.common.collect.Multimaps.index
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Arrays

@Composable
fun Test() {
    val evidenceViewModel : EvidenceViewModel = viewModel {
        EvidenceViewModel()
    }
    evidenceViewModel.init(LocalContext.current)

    InvestigationView(
        evidenceViewModel = evidenceViewModel
    )
}

@Composable
fun InvestigationView(
    evidenceViewModel: EvidenceViewModel? = viewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(.5f)
        ){
            GhostList(evidenceViewModel)
        }
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            EvidenceRulingList(evidenceViewModel)
        }
    }
}

@Composable
fun GhostList(
    evidenceViewModel: EvidenceViewModel? = viewModel(),
    maxFontSize: TextUnit = 96.sp
) {
    val ghostTypes = remember {
        val list = evidenceViewModel?.investigationData?.ghostList?.list
        list
    }

    var resizedTextSize by remember {
        mutableStateOf(maxFontSize)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        fun resizeText(it: TextUnit) {
             resizedTextSize = it
        }

        ghostTypes?.forEach { ghostType: Ghost? ->
            GhostView(
                ghostType = ghostType,
                onUpdateTitle = { it:TextUnit -> resizeText(it) }
            )
        }
    }
}

@Composable
fun GhostView(
    ghostType: Ghost? = Ghost(),
    maxFontSize: TextUnit = 48.sp,
    onUpdateTitle: (TextUnit) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .size(Dp.Unspecified, 48.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.5f)
        ) {
            AutoResizedText(
                text = ghostType?.name ?: "Error",
                textCase = TextCase.Uppercase,
                textAlign = TextAlign.Center,
                maxFontSize = maxFontSize,
                onUpdateMaxFontSize = { onUpdateTitle(maxFontSize) }
            )
        }

        GhostEvidenceGroup(
            evidenceGroup = ghostType?.evidence
        )
    }
}

@Composable
fun GhostEvidenceGroup(
    evidenceGroup: Array<Evidence>? = arrayOf(Evidence(), Evidence(), Evidence())
) {
    val evidenceTypes = remember { evidenceGroup }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        evidenceTypes?.forEach {
            Image(
                painterResource(id = it.icon),
                contentDescription = "Evidence 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(48.dp, 48.dp, Dp.Unspecified, Dp.Unspecified)
                    .weight(1f)
                    .padding(4.dp, 0.dp)
                    .aspectRatio(1f)
            )
        }

    }
}

@Composable
fun EvidenceRulingList(
    evidenceViewModel: EvidenceViewModel? = viewModel()
) {
    val evidenceTypes = remember {
        val list = evidenceViewModel?.investigationData?.evidenceList?.list
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        evidenceTypes?.forEach { evidenceType: Evidence ->
            EvidenceRulingView(evidenceType)
        }
    }

}

@Composable
fun EvidenceRulingView(
    evidenceType: Evidence = Evidence()
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.5f)
        ) {
            AutoResizedText(
                text = evidenceType.name?: "Error",
                textCase = TextCase.Uppercase,
                textAlign = TextAlign.Center,
                maxFontSize = 48.sp,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        RulingGroup(
            evidenceViewModel = EvidenceViewModel(),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
@Preview
fun RulingGroup(
    modifier: Modifier = Modifier,
    evidenceViewModel: EvidenceViewModel = viewModel(),
    groupIndex: Int = 0,
    state: Int = 1,
    onClick: () -> Unit = {}
) {
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(evidenceViewModel.radioButtonsChecked?.get(groupIndex) ?: state)
    }

    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Evidence.Ruling.entries.forEachIndexed { index, _ ->
            Box {
                RulingSelector(
                    evidenceViewModel = evidenceViewModel,
                    groupIndex = groupIndex,
                    rulingType = SelectionState(index),
                    rulingState = index == selectedIndex,
                    onSelection = {
                        selectedIndex =
                            evidenceViewModel.radioButtonsChecked?.get(groupIndex) ?: state
                        onClick()
                    }
                )
            }
        }

    }

    Text(text = "${evidenceViewModel.radioButtonsChecked?.get(groupIndex)}", color = Color.Red)

}

@Composable
@Preview
fun RulingSelector(
    evidenceViewModel: EvidenceViewModel = viewModel(),
    groupIndex: Int = 0,
    rulingType: SelectionState = Neutral,
    rulingState: Boolean = true,
    onSelection: () -> Unit = {}
) {
    val negativeColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.negativeSelColor))
    val neutralColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.neutralSelColor))
    val positiveColor = Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.positiveSelColor))

    val rulingDrawable = when(rulingType) {
        Negative -> Pair(R.drawable.icon_selector_neg_unsel, negativeColor)
        Positive -> Pair(R.drawable.icon_selector_pos_unsel, positiveColor)
        else -> Pair(R.drawable.icon_selector_inc_unsel, neutralColor)
    }

    IconButton(
        modifier = Modifier
            .padding(2.dp),
        onClick = {
            evidenceViewModel.setRadioButtonChecked(groupIndex, rulingType.value)
            evidenceViewModel.investigationData.evidenceList.list[groupIndex].ruling =
                Evidence.Ruling.entries
                    .toTypedArray()[evidenceViewModel.getRadioButtonsChecked()[groupIndex]]
            evidenceViewModel.ghostOrderData.updateOrder()

            Log.d("Updated", evidenceViewModel.ghostOrderData.currOrder.contentToString())

            onSelection()
        },
        content = {
            Image(
                painterResource(id = rulingDrawable.first),
                contentDescription = "Ruling Drawable",
                colorFilter = ColorFilter.tint(
                    if (rulingState) rulingDrawable.second
                    else neutralColor
                )
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

fun setRulingGroup(
    composeView: ComposeView?,
    evidenceViewModel: EvidenceViewModel = EvidenceViewModel(),
    groupIndex: Int = 0,
    onChange: () -> Unit = {}
) {
    composeView?.setContent {
        RulingGroup(
            evidenceViewModel = evidenceViewModel,
            groupIndex = groupIndex,
            onClick = onChange
        )
    }
}
