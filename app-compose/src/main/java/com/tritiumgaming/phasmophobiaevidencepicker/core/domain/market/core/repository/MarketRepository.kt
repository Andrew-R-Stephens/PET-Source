package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.core.repository

import kotlinx.coroutines.flow.StateFlow

interface MarketRepository {
    fun findNextAvailable(uuid: StateFlow<String>, direction: IncrementDirection): String
    enum class IncrementDirection(val value: Int) {
        FORWARD(1),
        BACKWARD(-1)
    }
}