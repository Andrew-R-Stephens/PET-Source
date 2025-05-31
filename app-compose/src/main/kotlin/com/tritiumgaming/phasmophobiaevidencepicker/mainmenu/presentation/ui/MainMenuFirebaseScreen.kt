package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.screens.firebase.FirebaseScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu.MainMenuViewModel

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