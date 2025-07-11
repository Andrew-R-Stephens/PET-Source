package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
    MissionsContent(
        objectivesViewModel = objectivesViewModel
    )
}
