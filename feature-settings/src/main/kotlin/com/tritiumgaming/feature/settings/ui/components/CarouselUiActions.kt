package com.tritiumgaming.feature.settings.ui.components

data class CarouselUiActions(
    val onPrevious: () -> Unit = {},
    val onNext: () -> Unit = {},
)