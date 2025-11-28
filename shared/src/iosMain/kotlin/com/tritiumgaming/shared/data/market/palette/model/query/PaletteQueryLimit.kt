package com.tritiumgaming.shared.data.market.palette.model.query

actual enum class PaletteQueryLimit(val value: Int) {
    SAFE_LIMIT(50),
    UNLIMITED(Int.MAX_VALUE)
}