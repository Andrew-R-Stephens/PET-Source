package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.evidence

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun EvidenceItemPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EvidenceListItem(label = "Test")
    }
}

@Composable
fun EvidenceListItem(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory),
    evidence: EvidenceModel = EvidenceModel(),
    label: String = stringResource(evidence.name)
) {

    val ruledEvidence = investigationViewModel.getRuledEvidence(evidence)
    val rulingState = investigationViewModel.getRuledEvidence(evidence)?.ruling
    var rememberRuling by remember { mutableStateOf(rulingState?.value) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
            .padding(8.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)
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
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
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
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                for (rulingType in Ruling.entries) {

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            rememberRuling = rulingType
                            investigationViewModel.setEvidenceRuling(evidence, rulingType)
                        },
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = Color.Transparent,
                            contentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                        ),
                        contentPadding = PaddingValues(8.dp),
                        shape = CircleShape,
                        content = {
                            RulingIcon(
                                ruling = rulingType,
                                isSelected = rememberRuling?.ordinal == rulingType.ordinal
                            )
                        }
                    )
                }


            }
        }
    }

}

@Composable
private fun RowScope.RulingIcon(
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
            modifier = Modifier
                .weight(.3f)
                .aspectRatio(1f)
        ) {
            Image(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = ruling.icon),
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
                    painter = painterResource(id = R.drawable.ic_selector_selected),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Evidence Icon",
                    colorFilter = ColorFilter.tint(onSelectedColor)
                )
            }
        }
    }

}