package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography

data class AccountTypographyDto(
    internal val uuid: String,
    internal val unlocked: Boolean = true
)

fun List<AccountTypographyDto>.toDomain() = map { it.toDomain() }

fun AccountTypographyDto.toDomain() = AccountTypography(
    uuid = uuid,
    unlocked = unlocked
)