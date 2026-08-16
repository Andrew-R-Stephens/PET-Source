package com.tritiumgaming.feature.marketplace.ui.common

import com.tritiumgaming.shared.data.account.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
