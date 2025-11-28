package com.tritiumgaming.shared.data.account.model

data class AccountPalette(
    val uuid: String,
    val unlocked: Boolean = true
)

fun List<AccountPalette>.toAccountMarketPalette() = map {
    it.toAccountMarketPalette()
}

fun AccountPalette.toAccountMarketPalette(): com.tritiumgaming.shared.data.account.model.AccountMarketPalette {
    return com.tritiumgaming.shared.data.account.model.AccountMarketPalette(
        uuid = uuid,
        unlocked = unlocked
    )
}