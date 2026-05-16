package com.tritiumgaming.feature.investigation.ui.common.operationconfig

import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.ConfigCarouselUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.ConfigDropdownUiState

internal data class ConfigStateBundle(
    val carouselUiState: ConfigCarouselUiState,
    val dropdownUiState: ConfigDropdownUiState,
)