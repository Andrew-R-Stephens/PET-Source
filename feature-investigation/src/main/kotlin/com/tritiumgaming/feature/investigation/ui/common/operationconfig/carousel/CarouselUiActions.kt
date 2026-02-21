package com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel

data class CarouselUiActions(
    val onLeftClick: () -> Unit = {},
    val onRightClick: () -> Unit = {}
)
