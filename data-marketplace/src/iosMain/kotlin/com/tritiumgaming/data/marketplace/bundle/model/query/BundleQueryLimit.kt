package com.tritiumgaming.data.marketplace.bundle.model.query

actual enum class BundleQueryLimit(val value: Int) {
    SAFE_LIMIT(50),
    UNLIMITED(Int.MAX_VALUE)
}