package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.startscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet.PETScreen

@Composable
@Preview
private fun StartScreenPreview() {
    StartScreen()
}

@Composable
fun StartScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen (
        content = { StartContent() }
    )

}


@Composable
private fun StartContent() {}