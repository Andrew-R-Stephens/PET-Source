package com.tritiumgaming.data.marketplace.bundle.model

import com.tritiumgaming.data.marketplace.bundle.model.query.BundleQueryFilterField
import com.tritiumgaming.data.marketplace.bundle.model.query.BundleQueryFilterValue
import com.tritiumgaming.data.marketplace.bundle.model.query.BundleQueryLimit
import com.tritiumgaming.data.marketplace.bundle.model.query.BundleQueryOrderDirection
import com.tritiumgaming.data.marketplace.bundle.model.query.BundleQueryOrderField

expect class BundleQueryOptions(
    filterField: BundleQueryFilterField?,
    filterValue: BundleQueryFilterValue?,
    orderField: BundleQueryOrderField?,
    orderDirection: BundleQueryOrderDirection?,
    limit: BundleQueryLimit?
) {

    val filterField: BundleQueryFilterField
    val filterValue: BundleQueryFilterValue
    val orderField: BundleQueryOrderField
    val orderDirection: BundleQueryOrderDirection
    val limit: BundleQueryLimit

    constructor()

}
