package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun CodexScreenPreview() {
    CodexScreen{}
}

@Composable
fun CodexScreen(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController,
        content = { content() }
    )

}
