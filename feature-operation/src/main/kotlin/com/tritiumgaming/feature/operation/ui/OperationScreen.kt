package com.tritiumgaming.feature.operation.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    windowInsets: WindowInsets = WindowInsets(),
    content: @Composable (modifier: Modifier) -> Unit
) {

    OperationNavigationBar(
        navController = navController,
        windowInsets = windowInsets,
    ) {
        content(modifier)
    }

}
