package com.tritiumgaming.shared.core.domain.market.palette.model

import com.google.firebase.firestore.Query

actual class PaletteQueryOptions {

    actual constructor() {
        this.filterField = FilterField.NONE
        this.filterValue = FilterValue.NONE
        this.orderField = OrderField.NONE
        this.orderDirection = OrderDirection.DESCENDING
        this.limit = Limit.SAFE_LIMIT
    }

    actual constructor(
        filterField: FilterField?,
        filterValue: FilterValue?,
        orderField: OrderField?,
        orderDirection: OrderDirection?,
        limit: Limit?
    ) {
        this.filterField = filterField ?: FilterField.GROUP
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
        GROUP("group"),
        NONE(null)
    }
    actual enum class FilterValue(val value: String?) {
        NONE(null)
    }
    actual enum class OrderField(val value: String?) {
        NONE(null)
    }
    actual enum class OrderDirection(val value: Query.Direction?) {
        ASCENDING(Query.Direction.ASCENDING),
        DESCENDING(Query.Direction.DESCENDING)
    }
    actual enum class Limit(val value: Int) {
        SAFE_LIMIT(50),
        UNLIMITED(Int.MAX_VALUE)
    }
}
