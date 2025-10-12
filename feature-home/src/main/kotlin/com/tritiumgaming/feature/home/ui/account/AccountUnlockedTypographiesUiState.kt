package com.tritiumgaming.feature.home.ui.account

import com.tritiumgaming.shared.core.domain.user.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
