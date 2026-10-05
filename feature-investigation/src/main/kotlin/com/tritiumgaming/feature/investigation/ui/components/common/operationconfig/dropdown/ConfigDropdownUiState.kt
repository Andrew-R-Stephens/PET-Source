package com.tritiumgaming.feature.investigation.ui.components.common.operationconfig.dropdown

import androidx.annotation.StringRes

data class ConfigDropdownUiState(
    @field:StringRes val options: List<Int> = emptyList(),
    @field:StringRes val label: Int = 0,
    val enabled: Boolean = true
)
