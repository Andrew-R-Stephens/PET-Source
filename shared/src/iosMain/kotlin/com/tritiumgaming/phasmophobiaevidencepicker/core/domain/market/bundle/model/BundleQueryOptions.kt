package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.bundle.model

actual class BundleQueryOptions {

    actual constructor() {
        this.filterField = FilterField.NONE
        this.filterValue = FilterValue.NONE
        this.orderField = OrderField.NONE
        this.orderDirection = OrderDirection.DESCENDING
        this.limit = Limit.UNLIMITED
    }

    actual constructor(
        filterField: FilterField?,
        filterValue: FilterValue?,
        orderField: OrderField?,
        orderDirection: OrderDirection?,
        limit: Limit?
    ) {
        this.filterField = filterField ?: FilterField.NONE
        this.filterValue = filterValue ?: FilterValue.NONE
        this.orderField = orderField ?: OrderField.NONE
        this.orderDirection = orderDirection ?: OrderDirection.DESCENDING
        this.limit = limit ?: Limit.SAFE_LIMIT
    }

    actual val filterField: FilterField
    actual val filterValue: FilterValue
    actual val orderField: OrderField
    actual val orderDirection: OrderDirection
    actual val limit: Limit


    actual enum class FilterField(val value: String?) {
        NONE(null)
    }
    actual enum class FilterValue(val value: String?) {
        NONE(null)
    }
    actual enum class OrderField(val value: String?) {
        NONE(null)
    }

    // TODO: Change to iOS firebase firestore ordering
    // possible library import will be com.google.firebase.firestore.Query
    actual enum class OrderDirection(val value: Int) {
        ASCENDING(1),
        DESCENDING(2)
    }
    actual enum class Limit(val value: Int) {
        SAFE_LIMIT(50),
        UNLIMITED(Int.MAX_VALUE)
    }
}