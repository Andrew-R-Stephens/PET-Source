package com.tritiumgaming.shared.data.market.typography.model.query

// TODO: Change to iOS firebase firestore ordering
// possible library import will be com.google.firebase.firestore.Query
actual enum class TypographyQueryOrderDirection(val value: Int) {
    ASCENDING(1),
    DESCENDING(2)
}