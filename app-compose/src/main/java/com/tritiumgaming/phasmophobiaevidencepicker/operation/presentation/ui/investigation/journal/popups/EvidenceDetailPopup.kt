package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.popups

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
fun EvidencePopupPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    )
    EvidencePopup()
}

@Composable
fun EvidencePopup(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory),
    evidence: EvidenceType? = null,
    onDismiss: () -> Unit = {}
) {

    var rememberEvidence by remember { mutableStateOf(evidence) }

    var rememberSection by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .alpha(if(rememberEvidence == null) 0f else 1f)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Button(
            modifier = Modifier
                .height(48.dp),
            onClick = {
                //rememberSection = 0
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (rememberSection == 0) LocalPalette.current.selectedColor2
                    else Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(4.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color.White),
            content = {
                BasicText(
                    text = stringResource(R.string.evidence_section_overview),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )

            }
        )

    }

}