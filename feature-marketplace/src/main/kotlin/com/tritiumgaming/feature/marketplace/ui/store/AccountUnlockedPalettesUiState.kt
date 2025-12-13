package com.tritiumgaming.feature.marketplace.ui.store

import com.tritiumgaming.shared.data.account.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
