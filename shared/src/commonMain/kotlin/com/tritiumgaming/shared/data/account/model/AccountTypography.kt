package com.tritiumgaming.shared.data.account.model

data class AccountTypography(
    internal val uuid: String,
    internal val unlocked: Boolean = true
)

fun List<com.tritiumgaming.shared.data.account.model.AccountTypography>.toAccountMarketTypography() = map {
    it.toAccountMarketTypography()
}

fun com.tritiumgaming.shared.data.account.model.AccountTypography.toAccountMarketTypography(): com.tritiumgaming.shared.data.account.model.AccountMarketTypography {
    return com.tritiumgaming.shared.data.account.model.AccountMarketTypography(
        uuid = uuid,
        unlocked = unlocked
    )
}