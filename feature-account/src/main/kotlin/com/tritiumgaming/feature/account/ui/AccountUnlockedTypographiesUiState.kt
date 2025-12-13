package com.tritiumgaming.feature.account.ui

import com.tritiumgaming.shared.data.account.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
