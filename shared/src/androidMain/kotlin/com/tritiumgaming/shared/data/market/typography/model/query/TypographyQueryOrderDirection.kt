package com.tritiumgaming.shared.data.market.typography.model.query

import com.google.firebase.firestore.Query

actual enum class TypographyQueryOrderDirection(val value: Query.Direction?) {
    ASCENDING(Query.Direction.ASCENDING),
    DESCENDING(Query.Direction.DESCENDING)
}