package com.tritiumgaming.data.journal.model

import kotlinx.serialization.Serializable

@Serializable
enum class EvidenceValidationType {
    NEGATIVE,
    NEUTRAL,
    POSITIVE,
}