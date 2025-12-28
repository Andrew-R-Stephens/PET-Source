package com.tritiumgaming.shared.data.account.model

data class AccountPalette(
    val uuid: String,
    val unlocked: Boolean = true
)

fun List<AccountPalette>.toAccountMarketPalette() = map {
    it.toAccountMarketPalette()
}

fun AccountPalette.toAccountMarketPalette(): AccountMarketPalette {
    return AccountMarketPalette(
        uuid = uuid,
        unlocked = unlocked
    )
}