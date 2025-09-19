package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.screens.PETScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu.MainMenuViewModel

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: MainMenuViewModel = viewModel(factory = MainMenuViewModel.Factory),
    content: @Composable () -> Unit
) {

    PETScreen (
        modifier = modifier,
        content = content
    )

}