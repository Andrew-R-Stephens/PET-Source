package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote

data class NetworkTypographyEntity(
    val uuid: String,
    val name: String? = "",
    val group: String? = "",
    val buyCredits: Long = 0L,
    val priority: Long? = 0L,
    val unlocked: Boolean = false
)