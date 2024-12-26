package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.newsletter

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuScreen


@Composable
@Preview
private fun NewsMessagesScreenPreview() {
    NewsMessagesScreen()
}

@Composable
fun NewsMessagesScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { NewsMessagesContent() }
    )

}


@Composable
private fun NewsMessagesContent() {}