package com.tritiumgaming.shared.core.domain.market.billable.model

expect class BillableQueryOptions(
    filterField: FilterField?,
    filterValue: FilterValue?,
    orderField: OrderField?,
    orderDirection: OrderDirection?,
    limit: Limit?
) {

    constructor()

    val filterField: FilterField
    val filterValue: FilterValue
    val orderField: OrderField
    val orderDirection: OrderDirection
    val limit: Limit

    enum class FilterField
    enum class FilterValue
    enum class OrderField
    enum class OrderDirection
    enum class Limit
}
