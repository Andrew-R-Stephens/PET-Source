package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model

expect class PaletteQueryOptions(
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
