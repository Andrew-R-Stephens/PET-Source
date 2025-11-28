package com.tritiumgaming.feature.home.ui.account

import com.tritiumgaming.shared.data.account.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
