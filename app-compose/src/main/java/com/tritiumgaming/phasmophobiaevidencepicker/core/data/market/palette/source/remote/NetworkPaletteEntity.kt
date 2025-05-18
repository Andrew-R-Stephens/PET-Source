package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote

data class NetworkPaletteEntity(
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false
)