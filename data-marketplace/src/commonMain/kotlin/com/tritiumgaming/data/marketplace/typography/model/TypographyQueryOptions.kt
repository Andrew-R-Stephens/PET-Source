package com.tritiumgaming.data.marketplace.typography.model

import com.tritiumgaming.data.marketplace.typography.model.query.TypographyQueryFilterField
import com.tritiumgaming.data.marketplace.typography.model.query.TypographyQueryFilterValue
import com.tritiumgaming.data.marketplace.typography.model.query.TypographyQueryLimit
import com.tritiumgaming.data.marketplace.typography.model.query.TypographyQueryOrderDirection
import com.tritiumgaming.data.marketplace.typography.model.query.TypographyQueryOrderField

expect class TypographyQueryOptions(
    filterField: TypographyQueryFilterField?,
    filterValue: TypographyQueryFilterValue?,
    orderField: TypographyQueryOrderField?,
    orderDirection: TypographyQueryOrderDirection?,
    limit: TypographyQueryLimit?
) {

    val filterField: TypographyQueryFilterField
    val filterValue: TypographyQueryFilterValue
    val orderField: TypographyQueryOrderField
    val orderDirection: TypographyQueryOrderDirection
    val limit: TypographyQueryLimit

    constructor()

}
