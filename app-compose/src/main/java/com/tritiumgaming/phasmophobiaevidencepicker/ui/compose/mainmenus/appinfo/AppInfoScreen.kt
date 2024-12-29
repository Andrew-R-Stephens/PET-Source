package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.appinfo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen

@Composable
@Preview
private fun InfoScreenPreview() {
    InfoScreen()
}

@Composable
fun InfoScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { InfoContent() }
    )

}


@Composable
private fun InfoContent() {}