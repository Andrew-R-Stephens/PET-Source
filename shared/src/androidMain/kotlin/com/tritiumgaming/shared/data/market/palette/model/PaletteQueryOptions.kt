package com.tritiumgaming.shared.data.market.palette.model

import com.tritiumgaming.shared.data.market.palette.model.query.PaletteQueryFilterField
import com.tritiumgaming.shared.data.market.palette.model.query.PaletteQueryFilterValue
import com.tritiumgaming.shared.data.market.palette.model.query.PaletteQueryLimit
import com.tritiumgaming.shared.data.market.palette.model.query.PaletteQueryOrderDirection
import com.tritiumgaming.shared.data.market.palette.model.query.PaletteQueryOrderField

actual class PaletteQueryOptions {

    actual val filterField: PaletteQueryFilterField
    actual val filterValue: PaletteQueryFilterValue
    actual val orderField: PaletteQueryOrderField
    actual val orderDirection: PaletteQueryOrderDirection
    actual val limit: PaletteQueryLimit

    actual constructor() {
        this.filterField = PaletteQueryFilterField.NONE
        this.filterValue = PaletteQueryFilterValue.NONE
        this.orderField = PaletteQueryOrderField.NONE
        this.orderDirection = PaletteQueryOrderDirection.DESCENDING
        this.limit = PaletteQueryLimit.SAFE_LIMIT
    }

    actual constructor(
        filterField: PaletteQueryFilterField?,
        filterValue: PaletteQueryFilterValue?,
        orderField: PaletteQueryOrderField?,
        orderDirection: PaletteQueryOrderDirection?,
        limit: PaletteQueryLimit?
    ) {
        this.filterField = filterField ?: PaletteQueryFilterField.GROUP
        this.filterValue = filterValue ?: PaletteQueryFilterValue.NONE
        this.orderField = orderField ?: PaletteQueryOrderField.NONE
        this.orderDirection = orderDirection ?: PaletteQueryOrderDirection.DESCENDING
        this.limit = limit ?: PaletteQueryLimit.SAFE_LIMIT
    }

}
