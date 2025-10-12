package com.tritiumgaming.feature.home.ui.account

import com.tritiumgaming.shared.core.domain.user.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
