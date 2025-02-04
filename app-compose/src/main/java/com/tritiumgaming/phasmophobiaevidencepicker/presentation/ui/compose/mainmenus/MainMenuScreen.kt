package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.screens.PETScreen

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    PETScreen (
        modifier = modifier,
        content = content
    )

}