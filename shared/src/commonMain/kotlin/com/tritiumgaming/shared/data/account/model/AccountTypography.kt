package com.tritiumgaming.shared.data.account.model

data class AccountTypography(
    internal val uuid: String,
    internal val unlocked: Boolean = true
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