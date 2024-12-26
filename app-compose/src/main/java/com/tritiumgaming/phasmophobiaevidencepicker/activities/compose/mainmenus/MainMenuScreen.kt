package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet.FirebaseScreen
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet.PETScreen

@Composable
@Preview
private fun MainMenuScreenPreview() {
    MainMenuScreen {}
}

@Composable
fun MainMenuScreen(
    content: @Composable () -> Unit
) {

    PETScreen (
        content = content
    )

}