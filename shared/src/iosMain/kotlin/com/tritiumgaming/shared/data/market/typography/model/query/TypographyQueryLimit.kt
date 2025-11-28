package com.tritiumgaming.shared.data.market.typography.model.query

actual enum class TypographyQueryLimit(val value: Int) {
    SAFE_LIMIT(50),
    UNLIMITED(Int.MAX_VALUE)
}