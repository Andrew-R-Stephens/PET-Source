package com.tritiumgaming.feature.investigation.ui.components.common.operationconfig

import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.carousel.ConfigCarouselUiState
import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.dropdown.ConfigDropdownUiState

internal data class ConfigStateBundle(
    val carouselUiState: ConfigCarouselUiState,
    val dropdownUiState: ConfigDropdownUiState,
)