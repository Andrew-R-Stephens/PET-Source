package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet.FirebaseScreen

@Composable
@Preview
private fun MainMenuFirebaseScreenPreview() {
    MainMenuFirebaseScreen {}
}

@Composable
fun MainMenuFirebaseScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    FirebaseScreen(
        modifier = modifier,
        content = content
    )

}