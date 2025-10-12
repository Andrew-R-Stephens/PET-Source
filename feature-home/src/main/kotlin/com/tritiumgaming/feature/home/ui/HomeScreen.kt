package com.tritiumgaming.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.ui.screens.PETScreen
import com.tritiumgaming.feature.home.ui.appinfo.AppInfoViewModel

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: AppInfoViewModel = viewModel(factory = AppInfoViewModel.Factory),
    content: @Composable () -> Unit
) {

    PETScreen (
        modifier = modifier,
        content = content
    )

}