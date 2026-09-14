package com.tritiumgaming.shared.data.account.model

data class AccountTypography(
    val uuid: String,
    val unlocked: Boolean = true
)

fun List<AccountTypography>.toAccountMarketTypography() = map {
    it.toAccountMarketTypography()
}

fun AccountTypography.toAccountMarketTypography(): AccountMarketTypography {
    return AccountMarketTypography(
        uuid = uuid,
        unlocked = unlocked
    )
}