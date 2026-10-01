package com.tritiumgaming.feature.marketplace.ui.common

import com.tritiumgaming.data.account.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
