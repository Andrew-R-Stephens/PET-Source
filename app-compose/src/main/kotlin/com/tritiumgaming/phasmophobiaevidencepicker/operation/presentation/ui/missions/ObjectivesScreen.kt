package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config.DeviceConfiguration
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components.GhostNameContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components.GhostResponseContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components.MissionsContent


@Composable
@Preview
private fun ObjectivesScreenPreview() {
    ObjectivesScreen(
        objectivesViewModel = viewModel(factory = ObjectivesViewModel.Factory),
        investigationViewModel = viewModel(factory = InvestigationViewModel.Factory)
    )
}

@Composable
fun ObjectivesScreen(
    navController: NavHostController = rememberNavController(),
    objectivesViewModel: ObjectivesViewModel,
    investigationViewModel: InvestigationViewModel
) {

    OperationScreen(
        navController = navController,
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                ObjectivesContentPortrait(
                    objectivesViewModel = objectivesViewModel,
                    investigationViewModel = investigationViewModel
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                ObjectivesContentLandscape(
                    objectivesViewModel = objectivesViewModel,
                    investigationViewModel = investigationViewModel
                )
            }
        }

    }

}

@Composable
private fun ObjectivesContentPortrait(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
    investigationViewModel: InvestigationViewModel
) {
    val rememberScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .wrapContentHeight()
            .verticalScroll(state = rememberScrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_optional_objectives),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            MissionsContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                objectivesViewModel = objectivesViewModel
            )

        }

        Column(
            modifier = Modifier
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_investigation_debrief),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            GhostNameContent(
                objectivesViewModel = objectivesViewModel
            )

            GhostResponseContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                objectivesViewModel = objectivesViewModel,
                investigationViewModel = investigationViewModel
            )

        }
    }

}

@Composable
private fun ObjectivesContentLandscape(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
    investigationViewModel: InvestigationViewModel
) {
    val rememberScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {

        Column(
            modifier = Modifier
                .weight(1f, false)
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_optional_objectives),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            MissionsContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                objectivesViewModel = objectivesViewModel
            )

        }

        Column(
            modifier = Modifier
                .weight(1f, false)
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(4.dp),
                text = stringResource(R.string.objectives_title_investigation_debrief),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.primary,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, stepSize = 5.sp)
            )

            GhostNameContent(
                objectivesViewModel = objectivesViewModel
            )

            GhostResponseContent(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top),
                objectivesViewModel = objectivesViewModel,
                investigationViewModel = investigationViewModel
            )

        }
    }

}
