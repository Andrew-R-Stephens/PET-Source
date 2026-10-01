package com.tritiumgaming.feature.account.ui

import com.tritiumgaming.data.account.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
