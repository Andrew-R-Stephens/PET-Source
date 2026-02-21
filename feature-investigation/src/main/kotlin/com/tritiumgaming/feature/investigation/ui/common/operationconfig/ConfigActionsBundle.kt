package com.tritiumgaming.feature.investigation.ui.common.operationconfig

import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.CarouselUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.DropdownUiActions

data class ConfigActionsBundle(
    val carouselUiActions: CarouselUiActions,
    val dropdownUiActions: DropdownUiActions
)
