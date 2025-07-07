package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MissionsScreenPreview() {
    MissionsScreen()
}

@Composable
fun MissionsScreen(
    navController: NavHostController = rememberNavController()
    //content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController,
        content = { MissionsContent() }
    )

}

@Composable
private fun MissionsContent() {

}
