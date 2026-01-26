package com.tritiumgaming.feature.codex.ui.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CodexMenuItemUiState(
    @field:StringRes val title: Int,
    @field:DrawableRes val image: Int? = null,
)
