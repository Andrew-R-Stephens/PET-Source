package com.tritiumgaming.feature.codex.ui.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class CodexMenuItemUiState(
    @field:StringRes val title: Int,
    @field:DrawableRes val image: Int? = null,
    val content: @Composable (Modifier) -> Unit = {},
)
