package com.tritiumgaming.shared.data.market.palette.model.query

import com.google.firebase.firestore.Query

actual enum class PaletteQueryOrderDirection(val value: Query.Direction?) {
    ASCENDING(Query.Direction.ASCENDING),
    DESCENDING(Query.Direction.DESCENDING)
}