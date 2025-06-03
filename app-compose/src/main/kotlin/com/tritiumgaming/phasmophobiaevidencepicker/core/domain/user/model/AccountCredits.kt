package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountCreditsDto

data class AccountCredits(
    val earnedCredits: Long,
    val spentCredits: Long
)
