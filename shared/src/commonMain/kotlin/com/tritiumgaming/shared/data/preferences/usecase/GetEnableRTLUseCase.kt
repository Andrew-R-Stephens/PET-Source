package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class GetEnableRTLUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.getEnableRTL()
}