package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.CodexItemScreen

@Composable
@Preview
private fun CodexAchievementScreenPreview() {
    CodexAchievementScreen()
}

@Composable
fun CodexAchievementScreen(
    navController: NavHostController = rememberNavController(),
    //content: @Composable () -> Unit
) {

    CodexItemScreen(
        navController = navController,
        content = { CodexAchievementContent() }
    )

}

@Composable
private fun CodexAchievementContent() {}