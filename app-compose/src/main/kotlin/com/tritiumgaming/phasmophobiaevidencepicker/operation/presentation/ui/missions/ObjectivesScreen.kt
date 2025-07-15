package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components.MissionsContent


@Composable
@Preview
private fun ObjectivesScreenPreview() {
    ObjectivesScreen(
        objectivesViewModel = viewModel(factory = ObjectivesViewModel.Factory)
    )
}

@Composable
fun ObjectivesScreen(
    navController: NavHostController = rememberNavController(),
    objectivesViewModel: ObjectivesViewModel
) {

    OperationScreen(
        navController = navController,
    ) {
        ObjectivesContent(
            navController = navController,
            objectivesViewModel = objectivesViewModel
        )
    }

}

@Composable
private fun ObjectivesContent(
    navController: NavHostController = rememberNavController(),
    objectivesViewModel: ObjectivesViewModel
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
            objectivesViewModel = objectivesViewModel
        )

    }

}
