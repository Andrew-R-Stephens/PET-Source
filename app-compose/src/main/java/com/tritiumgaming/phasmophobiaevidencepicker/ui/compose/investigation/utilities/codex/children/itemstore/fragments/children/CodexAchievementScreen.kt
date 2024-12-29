package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.CodexItemScreen

@Composable
@Preview
private fun CodexAchievementScreenPreview() {
    CodexAchievementScreen()
}

@Composable
fun CodexAchievementScreen(
    //content: @Composable () -> Unit
) {

    CodexItemScreen (
        content = { CodexAchievementContent() }
    )

}

@Composable
private fun CodexAchievementContent() {}