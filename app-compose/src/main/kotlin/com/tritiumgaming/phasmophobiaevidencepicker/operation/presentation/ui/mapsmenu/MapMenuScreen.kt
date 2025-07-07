package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MapMenuScreenPreview() {
    MapMenuScreen()
}

@Composable
fun MapMenuScreen(
    navController: NavHostController = rememberNavController(),
    //content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController,
        content = { MapMenuContent() }
    )

}

@Composable
private fun MapMenuContent() {

}