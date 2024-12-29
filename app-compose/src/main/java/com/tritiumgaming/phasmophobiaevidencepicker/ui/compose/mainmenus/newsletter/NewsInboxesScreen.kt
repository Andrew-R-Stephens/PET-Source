package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen

@Composable
@Preview
private fun NewsInboxesScreenPreview() {
    NewsInboxesScreen()
}

@Composable
fun NewsInboxesScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { NewsInboxesContent() }
    )

}


@Composable
private fun NewsInboxesContent() {}