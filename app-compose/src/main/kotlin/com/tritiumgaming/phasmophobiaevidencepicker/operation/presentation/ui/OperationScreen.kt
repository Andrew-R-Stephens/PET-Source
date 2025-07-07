package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
@Preview
private fun OperationScreenPreview() {
    OperationScreen {}
}

@Composable
fun OperationScreen(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {

    OperationBottomNavBar(
        navController = navController
    ) {
        content()
    }

}
