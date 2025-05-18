package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model

data class BundleEntity (
    val marketBundleEntity: NetworkBundleEntity? = null,
    val uuid: String = marketBundleEntity?.uuid ?: ""
)