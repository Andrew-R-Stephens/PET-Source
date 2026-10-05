package com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.carousel

data class CarouselUiActions(
    val onLeftClick: () -> Unit = {},
    val onRightClick: () -> Unit = {}
)
