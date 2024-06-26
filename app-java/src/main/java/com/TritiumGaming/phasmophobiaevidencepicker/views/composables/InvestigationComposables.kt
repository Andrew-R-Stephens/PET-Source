package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.utils.TextCase
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Negative
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Neutral
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.SelectionState.Companion.Positive

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
        val list = evidenceViewModel?.investigationModel?.ghostListModel?.list
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

        ghostTypes?.forEach { ghostType: GhostModel? ->
            GhostView(
                ghostType = ghostType,
                onUpdateTitle = { it:TextUnit -> resizeText(it) }
            )
        }
    }
}

@Composable
fun GhostView(
    ghostType: GhostModel? = GhostModel(),
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
    evidenceGroup: Array<EvidenceModel?>? = arrayOf(
        EvidenceModel(),
        EvidenceModel(),
        EvidenceModel()
    )
) {
    val evidenceTypes = remember { evidenceGroup }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        evidenceTypes?.forEach { evidenceType ->
            evidenceType?.let {
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
}

@Composable
fun EvidenceRulingList(
    evidenceViewModel: EvidenceViewModel? = viewModel()
) {
    val evidenceTypes = remember {
        evidenceViewModel?.investigationModel?.evidenceListModel?.evidenceList
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        evidenceTypes?.forEach { evidenceType: EvidenceModel ->
            EvidenceRulingView(evidenceType)
        }
    }

}

@Composable
fun EvidenceRulingView(
    evidenceType: EvidenceModel = EvidenceModel()
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
    evidenceViewModel: EvidenceViewModel = viewModel<EvidenceViewModel>(),
    groupIndex: Int = 0,
    onClick: () -> Unit = {}
) {
    val radioButtonState by evidenceViewModel.radioButtonsChecked.collectAsState()

    Row(
        modifier = modifier
            //.defaultMinSize(Dp.Unspecified, 48.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        EvidenceModel.Ruling.entries.forEachIndexed { index, _ ->
            RulingSelector(
                evidenceViewModel = evidenceViewModel,
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
@Preview
fun RulingSelector(
    modifier: Modifier = Modifier,
    evidenceViewModel: EvidenceViewModel = viewModel<EvidenceViewModel>(),
    groupIndex: Int = 0,
    rulingType: SelectionState = Neutral,
    rulingState: Boolean = true,
    onSelection: () -> Unit = {}
) {
    val radioButtons = evidenceViewModel.radioButtonsChecked.collectAsState()

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
            evidenceViewModel.setRadioButtonChecked(groupIndex, rulingType.value)
            evidenceViewModel.investigationModel?.evidenceListModel?.evidenceList?.get(groupIndex)?.ruling =
                EvidenceModel.Ruling.entries.toTypedArray()[radioButtons.value[groupIndex]]
            evidenceViewModel.ghostOrderModel?.updateOrder()

            Log.d("Updated", evidenceViewModel.ghostOrderModel?.currOrder.contentToString())

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
