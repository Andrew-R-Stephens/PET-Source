package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto

import com.tritiumgaming.shared.core.domain.user.model.AccountPalette


data class AccountPaletteDto(
    internal val uuid: String,
    internal val unlocked: Boolean = true
)

fun List<AccountPaletteDto>.toDomain() = map { it.toDomain() }

fun AccountPaletteDto.toDomain() = AccountPalette(
    uuid = uuid,
    unlocked = unlocked
)