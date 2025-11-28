package com.tritiumgaming.feature.home.ui.account

import com.tritiumgaming.shared.data.account.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
