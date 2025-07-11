package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexScreen


@Composable
@Preview
private fun CodexItemScreenPreview() {
    CodexItemScreen{}
}

@Composable
fun CodexItemScreen(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {

    CodexScreen(
        navController = navController,
        content = content
    )

}