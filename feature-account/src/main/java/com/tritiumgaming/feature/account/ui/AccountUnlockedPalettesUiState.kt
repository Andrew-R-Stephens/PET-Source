package com.tritiumgaming.feature.account.ui

import com.tritiumgaming.shared.data.account.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
