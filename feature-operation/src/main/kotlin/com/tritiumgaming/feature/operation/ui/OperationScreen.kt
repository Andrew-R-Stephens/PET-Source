package com.tritiumgaming.feature.operation.ui

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

    OperationNavigationBar(
        navController = navController
    ) {
        content()
    }

}
