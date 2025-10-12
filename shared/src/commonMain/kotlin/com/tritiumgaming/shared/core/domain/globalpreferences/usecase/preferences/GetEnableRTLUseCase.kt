package com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences

import com.tritiumgaming.shared.core.domain.globalpreferences.repository.GlobalPreferencesRepository

class GetEnableRTLUseCase(
    private val repository: GlobalPreferencesRepository
) {
    operator fun invoke() = repository.getEnableRTL()
}