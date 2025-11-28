package com.tritiumgaming.shared.data.market.typography.model

import com.tritiumgaming.shared.data.market.typography.model.query.TypographyQueryFilterField
import com.tritiumgaming.shared.data.market.typography.model.query.TypographyQueryFilterValue
import com.tritiumgaming.shared.data.market.typography.model.query.TypographyQueryLimit
import com.tritiumgaming.shared.data.market.typography.model.query.TypographyQueryOrderDirection
import com.tritiumgaming.shared.data.market.typography.model.query.TypographyQueryOrderField

actual class TypographyQueryOptions {

    actual constructor() {
        this.filterField = TypographyQueryFilterField.NONE
        this.filterValue = TypographyQueryFilterValue.NONE
        this.orderField = TypographyQueryOrderField.NONE
        this.orderDirection = TypographyQueryOrderDirection.DESCENDING
        this.limit = TypographyQueryLimit.UNLIMITED
    }

    actual constructor(
        filterField: TypographyQueryFilterField?,
        filterValue: TypographyQueryFilterValue?,
        orderField: TypographyQueryOrderField?,
        orderDirection: TypographyQueryOrderDirection?,
        limit: TypographyQueryLimit?
    ) {
        this.filterField = filterField ?: TypographyQueryFilterField.GROUP
        this.filterValue = filterValue ?: TypographyQueryFilterValue.NONE
        this.orderField = orderField ?: TypographyQueryOrderField.NONE
        this.orderDirection = orderDirection ?: TypographyQueryOrderDirection.DESCENDING
        this.limit = limit ?: TypographyQueryLimit.SAFE_LIMIT
    }

    actual val filterField: TypographyQueryFilterField
    actual val filterValue: TypographyQueryFilterValue
    actual val orderField: TypographyQueryOrderField
    actual val orderDirection: TypographyQueryOrderDirection
    actual val limit: TypographyQueryLimit


}