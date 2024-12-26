package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.newsletter

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuScreen


@Composable
@Preview
private fun NewsMessageScreenPreview() {
    NewsMessageScreen()
}

@Composable
fun NewsMessageScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { NewsMessageContent() }
    )

}


@Composable
private fun NewsMessageContent() {}