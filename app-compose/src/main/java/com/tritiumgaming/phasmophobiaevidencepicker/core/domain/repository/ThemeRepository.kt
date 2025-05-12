package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface ThemeRepository {
    fun findNextAvailable(uuid: StateFlow<String>,direction: IncrementDirection): String
    enum class IncrementDirection(val value: Int) {
        FORWARD(1),
        BACKWARD(-1)
    }
}