package com.tritiumgaming.feature.codex.ui.catalog

data class PaginatorUiState(
    val scrollUiState: ScrollUiState,
    val images: List<Int> = emptyList()
)
