package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.account

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountPalette

data class AccountUnlockedPalettesUiState(
    val unlockedPalettes: List<AccountPalette> = emptyList()
)
