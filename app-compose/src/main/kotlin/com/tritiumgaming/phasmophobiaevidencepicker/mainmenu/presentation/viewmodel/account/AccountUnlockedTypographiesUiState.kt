package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.account

import com.tritiumgaming.shared.core.domain.user.model.AccountTypography

data class AccountUnlockedTypographiesUiState(
    val unlockedTypographies: List<AccountTypography> = emptyList()
)
