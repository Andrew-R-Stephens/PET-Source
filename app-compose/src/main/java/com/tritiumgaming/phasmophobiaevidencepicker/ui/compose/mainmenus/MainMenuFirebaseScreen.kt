package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.screens.firebase.FirebaseScreen

@Composable
fun MainMenuFirebaseScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = viewModel(),
    content: @Composable () -> Unit
) {

    FirebaseScreen(
        modifier = modifier,
        content = content
    )

}