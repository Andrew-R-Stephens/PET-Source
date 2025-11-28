package com.tritiumgaming.shared.data.market.bundle.model

import com.tritiumgaming.shared.data.market.bundle.model.query.BundleQueryFilterField
import com.tritiumgaming.shared.data.market.bundle.model.query.BundleQueryFilterValue
import com.tritiumgaming.shared.data.market.bundle.model.query.BundleQueryLimit
import com.tritiumgaming.shared.data.market.bundle.model.query.BundleQueryOrderDirection
import com.tritiumgaming.shared.data.market.bundle.model.query.BundleQueryOrderField

actual class BundleQueryOptions {

    actual val filterField: BundleQueryFilterField
    actual val filterValue: BundleQueryFilterValue
    actual val orderField: BundleQueryOrderField
    actual val orderDirection: BundleQueryOrderDirection
    actual val limit: BundleQueryLimit

    actual constructor() {
        this.filterField = BundleQueryFilterField.NONE
        this.filterValue = BundleQueryFilterValue.NONE
        this.orderField = BundleQueryOrderField.NONE
        this.orderDirection = BundleQueryOrderDirection.DESCENDING
        this.limit = BundleQueryLimit.UNLIMITED
    }

    actual constructor(
        filterField: BundleQueryFilterField?,
        filterValue: BundleQueryFilterValue?,
        orderField: BundleQueryOrderField?,
        orderDirection: BundleQueryOrderDirection?,
        limit: BundleQueryLimit?
    ) {
        this.filterField = filterField ?: BundleQueryFilterField.NONE
        this.filterValue = filterValue ?: BundleQueryFilterValue.NONE
        this.orderField = orderField ?: BundleQueryOrderField.NONE
        this.orderDirection = orderDirection ?: BundleQueryOrderDirection.DESCENDING
        this.limit = limit ?: BundleQueryLimit.SAFE_LIMIT
    }


}