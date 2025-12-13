package com.tritiumgaming.feature.marketplace.ui.store

import com.tritiumgaming.shared.data.account.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
