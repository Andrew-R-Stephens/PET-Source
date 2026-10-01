package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetEnableRTLUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(enable: Boolean) =
        repository.setEnableRTL(enable)
}