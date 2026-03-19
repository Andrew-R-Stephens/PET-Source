package com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType
import org.jetbrains.annotations.TestOnly

data class EvidenceListItemUiColors(
    val selectedColor: Color = Color.Transparent,
    val unselectedColor: Color = Color.Transparent
)

@Composable
fun EvidenceListItem(
    modifier: Modifier = Modifier,
    state: EvidenceListItemUiState,
    actions: EvidenceListItemUiAction,
    colors: EvidenceListItemUiColors = EvidenceListItemUiColors()
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(2.dp)
            .alpha(if(state.enabled) 1f else .25f),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                EvidenceItem(
                    state = state,
                    actions = actions
                )
            }
        }

    }

}

@Composable
private fun EvidenceItem(
    modifier: Modifier = Modifier,
    state: EvidenceListItemUiState,
    actions: EvidenceListItemUiAction
) {
    FlowColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        itemHorizontalAlignment = Alignment.CenterHorizontally,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.Center,
    ) {

        Box(
            modifier = Modifier
                .height(36.dp)
                .weight(1f)
                .clickable(onClick = {
                    actions.onNameClick()
                })
                .padding(4.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = state.label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 1.sp,
                    //maxFontSize = 48.sp,
                    stepSize = 8.sp
                )
            )
        }

        if(!state.enabled) return@FlowColumn

        Row(
            modifier = Modifier
                .wrapContentHeight()
                //.fillMaxWidth()
                .weight(1f, true),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (ruling in EvidenceValidationType.entries) {

                Button(
                    modifier = Modifier
                        .size(48.dp),
                    onClick = {
                        actions.onToggle(ruling)
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(4.dp),
                    shape = CircleShape,
                    content = {
                        RulingIcon(
                            modifier = Modifier
                                .fillMaxSize(),
                            evidenceValidationType = ruling,
                            isSelected = state.state.ordinal == ruling.ordinal,
                        )
                    }
                )
            }


        }
    }
}

@Composable
private fun RulingIcon(
    modifier: Modifier = Modifier,
    evidenceValidationType: EvidenceValidationType = EvidenceValidationType.NEUTRAL,
    isSelected: Boolean = false
) {

    val onSelectedColor = when (evidenceValidationType) {
        EvidenceValidationType.NEGATIVE -> LocalPalette.current.primary
        EvidenceValidationType.NEUTRAL -> LocalPalette.current.onSurface
        EvidenceValidationType.POSITIVE -> LocalPalette.current.tertiary
    }
    val onUnselectedColor = LocalPalette.current.onSurface

    evidenceValidationType.let { ruling ->
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                painter = painterResource(
                    id = when(ruling) {
                        EvidenceValidationType.NEGATIVE -> R.drawable.ic_selector_neg_unsel
                        EvidenceValidationType.NEUTRAL -> R.drawable.ic_selector_inc_unsel
                        EvidenceValidationType.POSITIVE -> R.drawable.ic_selector_pos_unsel
                    }
                ),
                contentScale = ContentScale.Fit,
                contentDescription = "Evidence Icon",
                colorFilter = ColorFilter.tint(
                    when (isSelected) {
                        true -> onSelectedColor
                        false -> onUnselectedColor
                    }
                )
            )
            if(isSelected) {
                Image(
                    modifier = Modifier
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.ic_selector_selected),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Evidence Icon",
                    colorFilter = ColorFilter.tint(onSelectedColor)
                )
            }
        }
    }

}

@Preview
@Composable
private fun RulingIconPreview() {

    SelectiveTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                evidenceValidationType = EvidenceValidationType.NEGATIVE,
                isSelected = true
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                evidenceValidationType = EvidenceValidationType.NEUTRAL,
                isSelected = false
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                evidenceValidationType = EvidenceValidationType.POSITIVE,
                isSelected = false
            )
        }
    }

}

@Composable
@Preview(device = "spec:width=411dp,height=891dp")
@TestOnly
private fun EvidenceItemPreview() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        LocalPalettesMap.forEach {
            SelectiveTheme(
                palette = it.value,
                typography = ClassicTypography
            ) {
                Surface(
                    color = LocalPalette.current.surface
                ) {
                    Column {
                        EvidenceListItem(
                            state = EvidenceListItemUiState(
                                label = "Test",
                                state = EvidenceValidationType.NEGATIVE,
                                enabled = true
                            ),
                            actions = EvidenceListItemUiAction()
                        )
                        EvidenceListItem(
                            state = EvidenceListItemUiState(
                                label = "Test",
                                state = EvidenceValidationType.NEUTRAL,
                                enabled = true
                            ),
                            actions = EvidenceListItemUiAction()
                        )
                        EvidenceListItem(
                            state = EvidenceListItemUiState(
                                label = "Test",
                                state = EvidenceValidationType.POSITIVE,
                                enabled = false
                            ),
                            actions = EvidenceListItemUiAction()
                        )
                    }
                }
            }
        }

    }
}