package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.popups

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.PETImageButtonType
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
fun EvidencePopupPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        EvidencePopup()
    }
}

@Composable
@Preview
@TestOnly
fun GhostPopupPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        GhostPopup()
    }
}

@Composable
fun EvidencePopup(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val popupState = investigationViewModel.journalPopupUi.collectAsStateWithLifecycle()
    if(popupState.value !is EvidenceModel) return

    var rememberSection by remember { mutableIntStateOf(0) }

    Surface(
        color = LocalPalette.current.background.color,
    ) {

        Column(
            modifier = Modifier
                .alpha(if (popupState.value == null) 0f else 1f)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            NavigationHeaderComposable(
                params = NavHeaderComposableParams(
                    rightType = PETImageButtonType.CANCEL,
                    rightTitleRes = PETImageButtonType.NONE.labelRes,
                    rightOnClick = {
                        investigationViewModel.unsetPopupUi()
                    }
                )
            )

            Button(
                modifier = Modifier
                    .height(48.dp),
                onClick = {
                    //rememberSection = 0
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

}

@Composable
fun GhostPopup(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val popupState = investigationViewModel.journalPopupUi.collectAsStateWithLifecycle()
    if(popupState.value !is GhostModel) return

    var rememberSection by remember { mutableIntStateOf(0) }

    Surface(
        color = LocalPalette.current.background.color,
    ) {
        Column(
            modifier = Modifier
                .alpha(if (popupState.value == null) 0f else 1f)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            NavigationHeaderComposable(
                params = NavHeaderComposableParams(
                    rightType = PETImageButtonType.CANCEL,
                    rightTitleRes = PETImageButtonType.NONE.labelRes,
                    rightOnClick = {
                        investigationViewModel.unsetPopupUi()
                    }
                )
            )

            Button(
                modifier = Modifier
                    .height(48.dp),
                onClick = {
                    //rememberSection = 0
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

}