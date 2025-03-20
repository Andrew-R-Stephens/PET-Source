package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.screens.firebase.FirebaseScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel

@Composable
fun MainMenuFirebaseScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = viewModel(factory = MainMenuViewModel.Factory),
    content: @Composable () -> Unit
) {

    FirebaseScreen(
        modifier = modifier,
        content = content
    )

}