package com.tritiumgaming.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.ui.screens.PETScreen
import com.tritiumgaming.feature.home.ui.appinfo.AppInfoViewModel

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    mainMenuViewModel: AppInfoViewModel = viewModel(factory = AppInfoViewModel.Factory),
    content: @Composable () -> Unit
) {
    PETScreen(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ) {
        content()
    }
}