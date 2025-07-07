package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.CodexItemScreen


@Composable
@Preview
private fun CodexPossessionsScreenPreview() {
    CodexPossessionsScreen()
}

@Composable
fun CodexPossessionsScreen(
    navController: NavHostController = rememberNavController(),
    //content: @Composable () -> Unit
) {

    CodexItemScreen(
        navController = navController,
        content = { CodexPossessionsContent() }
    )

}

@Composable
private fun CodexPossessionsContent() {}