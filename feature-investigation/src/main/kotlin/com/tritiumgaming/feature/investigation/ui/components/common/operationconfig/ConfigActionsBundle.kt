package com.tritiumgaming.feature.investigation.ui.components.common.operationconfig

import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.carousel.CarouselUiActions
import com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.dropdown.DropdownUiActions

data class ConfigActionsBundle(
    val carouselUiActions: CarouselUiActions,
    val dropdownUiActions: DropdownUiActions
)
