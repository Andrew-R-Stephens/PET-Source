package com.tritiumgaming.feature.codex.ui.catalog

data class PaginatorUiActions(
    val onScrollUpdate: (Float, Int) -> Unit = { _, _ -> }
)
