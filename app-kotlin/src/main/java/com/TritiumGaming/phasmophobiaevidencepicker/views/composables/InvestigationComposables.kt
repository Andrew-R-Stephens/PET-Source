package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.TypedValue
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.TextCase
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

@Composable
@Preview
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
@Preview
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
@Preview
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
@Preview
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
@Preview
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
@Preview
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
@Preview
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
    state: Int = 1
) {
    var selectedIndex = remember {
        mutableStateListOf(evidenceViewModel.radioButtonsChecked[groupIndex])
    }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for(i in 0 .. 2) {
            RulingSelector(
                evidenceViewModel = evidenceViewModel,
                groupIndex = groupIndex,
                rulingType = i,
                rulingState  = i == 1
            )
        }

    }

}

@Composable
@Preview
fun RulingSelector(
    evidenceViewModel: EvidenceViewModel = viewModel(),
    groupIndex: Int = 0,
    rulingType: Int = 1,
    rulingState: Boolean = true,
    onSelection: () -> Unit = {}
) {
    val context = LocalContext.current;
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true)
    val negativeColor = if (typedValue.resourceId != 0)
        colorResource(id = typedValue.resourceId) else Color(typedValue.data)
    context.theme.resolveAttribute(R.attr.neutralSelColor, typedValue, true)
    val neutralColor = if (typedValue.resourceId != 0)
        colorResource(id = typedValue.resourceId) else Color(typedValue.data)
    context.theme.resolveAttribute(R.attr.positiveSelColor, typedValue, true)
    val positiveColor = if (typedValue.resourceId != 0)
        colorResource(id = typedValue.resourceId) else Color(typedValue.data)

    val rulingDrawable = when(rulingType) {
        0 -> Pair(R.drawable.icon_selector_neg_unsel, negativeColor)
        2 -> Pair(R.drawable.icon_selector_pos_unsel, positiveColor)
        else -> Pair(R.drawable.icon_selector_inc_unsel, neutralColor)
    }

    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .size(48.dp)
            .sizeIn(),
        onClick = {
            evidenceViewModel.setRadioButtonChecked(groupIndex, rulingType)
            evidenceViewModel.investigationData.evidenceList.list[groupIndex].ruling =
                Evidence.Ruling.entries.toTypedArray()[
                    evidenceViewModel.getRadioButtonsChecked()[groupIndex]
                ]

            evidenceViewModel.ghostOrderData.updateOrder()
        },
        content = {
            Image(
                painterResource(id = rulingDrawable.first),
                contentDescription = "Ruling Drawable",
                colorFilter = ColorFilter.tint(
                    if (rulingState) rulingDrawable.second
                    else neutralColor)
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
