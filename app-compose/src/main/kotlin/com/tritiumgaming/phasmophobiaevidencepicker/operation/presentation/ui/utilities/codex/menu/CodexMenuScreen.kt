package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexScreen


@Composable
@Preview
private fun CodexMenuScreenPreview() {
    CodexMenuScreen()
}

@Composable
fun CodexMenuScreen(
    navController: NavHostController = rememberNavController(),
    //content: @Composable () -> Unit
) {

    CodexScreen(
        navController = navController,
        content = { CodexMenuContent() }
    )

}


@Composable
private fun CodexMenuContent() {}