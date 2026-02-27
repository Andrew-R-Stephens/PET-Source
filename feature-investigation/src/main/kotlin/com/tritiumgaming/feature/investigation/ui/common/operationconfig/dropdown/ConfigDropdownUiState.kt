package com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown

import androidx.annotation.StringRes

data class ConfigDropdownUiState(
    @field:StringRes val options: List<Int> = emptyList(),
    @field:StringRes val label: Int = 0
)
