package com.tritiumgaming.feature.operation.ui.investigation.journal.lists.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.operation.ui.investigation.InvestigationScreenViewModel
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence.Ruling
import org.jetbrains.annotations.TestOnly


@Composable
fun EvidenceListItem(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    evidence: EvidenceType = EvidenceType(
        id = EvidenceResources.EvidenceIdentifier.DOTS,
        name = EvidenceResources.EvidenceTitle.DOTS,
    icon = EvidenceResources.EvidenceIcon.DOTS
    ),
    label: String = stringResource(evidence.name.toStringResource()),
    onNameClick: () -> Unit = {},
) {

    val rulingState = investigationViewModel.getRuledEvidence(evidence)?.ruling

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(2.dp),
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
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                EvidenceRowTall(
                    modifier = modifier,
                    investigationViewModel = investigationViewModel,
                    label = label,
                    evidence = evidence,
                    rulingState = rulingState
                ) {
                    onNameClick()
                }
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                EvidenceRowFlat(
                    modifier = modifier,
                    investigationViewModel = investigationViewModel,
                    label = label,
                    evidence = evidence,
                    rulingState = rulingState
                ) {
                    onNameClick()
                }
            }
        }

    }

}

@Composable
private fun EvidenceRowFlat(
    modifier: Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    label: String,
    evidence: EvidenceType,
    rulingState: Ruling?,
    onNameClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .height(48.dp)
                .weight(1f)
                .clickable(onClick = {
                    onNameClick()
                })
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

        }

        Row(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (rulingType in Ruling.entries) {

                Button(
                    modifier = Modifier
                        .weight(1f, false)
                        .sizeIn(maxWidth = 48.dp, maxHeight = 48.dp)
                        .aspectRatio(1f)
                        .wrapContentSize(),
                    onClick = {
                        //rememberRuling = rulingType
                        investigationViewModel.setEvidenceRuling(evidence, rulingType)
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
                    shape = CircleShape,
                    content = {
                        RulingIcon(
                            ruling = rulingType,
                            isSelected = rulingState?.ordinal == rulingType.ordinal
                        )
                    }
                )
            }


        }
    }
}

@Composable
private fun EvidenceRowTall(
    modifier: Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    label: String,
    evidence: EvidenceType,
    rulingState: Ruling?,
    onNameClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(onClick = {
                    onNameClick()
                }),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(
                    minFontSize = 1.sp,
                    maxFontSize = 36.sp,
                    stepSize = 5.sp
                )
            )

        }

        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            for (rulingType in Ruling.entries) {

                Button(
                    modifier = Modifier
                        .weight(1f, false)
                        .aspectRatio(1f)
                        .wrapContentSize(),
                    onClick = {
                        //rememberRuling = rulingType
                        investigationViewModel.setEvidenceRuling(evidence, rulingType)
                    },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues(2.dp),
                    shape = CircleShape,
                    content = {
                        RulingIcon(
                            modifier = Modifier
                                .size(48.dp),
                            ruling = rulingType,
                            isSelected = rulingState?.ordinal == rulingType.ordinal,
                            //isSelected = rememberRuling?.ordinal == rulingType.ordinal
                        )
                    }
                )
            }


        }
    }
}

@Composable
private fun RowScope.RulingIcon(
    modifier: Modifier = Modifier,
    ruling: Ruling? = Ruling.NEUTRAL,
    isSelected: Boolean = false
) {

    val onSelectedColor = when (ruling) {
        Ruling.NEGATIVE -> LocalPalette.current.negativeSelColor
        Ruling.POSITIVE -> LocalPalette.current.positiveSelColor
        else -> LocalPalette.current.neutralSelColor
    }
    val onUnselectedColor = LocalPalette.current.neutralSelColor

    ruling?.let { ruling ->
        Box(
            modifier = modifier
                .align(Alignment.CenterVertically)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                painter = painterResource(
                    id = when(ruling) {
                        Ruling.NEGATIVE -> R.drawable.ic_selector_neg_unsel
                        Ruling.POSITIVE -> R.drawable.ic_selector_pos_unsel
                        else -> R.drawable.ic_selector_inc_unsel
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
                ruling = Ruling.NEGATIVE,
                isSelected = true
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                ruling = Ruling.NEUTRAL,
                isSelected = false
            )
            RulingIcon(
                modifier = Modifier
                    .weight(1f),
                ruling = Ruling.POSITIVE,
                isSelected = false
            )
        }
    }

}

@Composable
@Preview
@TestOnly
private fun EvidenceItemPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EvidenceListItem(
            label = "Test",
            investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory)
        )
    }
}