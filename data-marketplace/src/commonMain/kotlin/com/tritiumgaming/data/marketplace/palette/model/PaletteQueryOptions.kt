package com.tritiumgaming.data.marketplace.palette.model

import com.tritiumgaming.data.marketplace.palette.model.query.PaletteQueryFilterField
import com.tritiumgaming.data.marketplace.palette.model.query.PaletteQueryFilterValue
import com.tritiumgaming.data.marketplace.palette.model.query.PaletteQueryLimit
import com.tritiumgaming.data.marketplace.palette.model.query.PaletteQueryOrderDirection
import com.tritiumgaming.data.marketplace.palette.model.query.PaletteQueryOrderField

expect class PaletteQueryOptions(
    filterField: PaletteQueryFilterField?,
    filterValue: PaletteQueryFilterValue?,
    orderField: PaletteQueryOrderField?,
    orderDirection: PaletteQueryOrderDirection?,
    limit: PaletteQueryLimit?
) {

    val filterField: PaletteQueryFilterField
    val filterValue: PaletteQueryFilterValue
    val orderField: PaletteQueryOrderField
    val orderDirection: PaletteQueryOrderDirection
    val limit: PaletteQueryLimit

    constructor()

}
