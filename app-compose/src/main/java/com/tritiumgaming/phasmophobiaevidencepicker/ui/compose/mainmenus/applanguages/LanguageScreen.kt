package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.applanguages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen


@Composable
@Preview
private fun LanguageScreenPreview() {
    LanguageScreen()
}

@Composable
fun LanguageScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { LanguageContent() }
    )

}


@Composable
private fun LanguageContent() {}