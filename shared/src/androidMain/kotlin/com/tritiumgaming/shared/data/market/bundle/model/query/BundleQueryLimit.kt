package com.tritiumgaming.shared.data.market.bundle.model.query

actual enum class BundleQueryLimit(val value: Int) {
    SAFE_LIMIT(50),
    UNLIMITED(Int.MAX_VALUE)
}