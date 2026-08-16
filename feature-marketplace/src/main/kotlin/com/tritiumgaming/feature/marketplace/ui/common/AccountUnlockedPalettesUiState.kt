package com.tritiumgaming.feature.marketplace.ui.common

import com.tritiumgaming.shared.data.account.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
