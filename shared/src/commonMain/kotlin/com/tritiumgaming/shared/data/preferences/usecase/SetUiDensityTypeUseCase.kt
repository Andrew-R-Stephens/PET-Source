package com.tritiumgaming.shared.data.preferences.usecase

import com.tritiumgaming.shared.data.preferences.DensityType
import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository

class SetUiDensityTypeUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(densityType: DensityType) =
        repository.setUiDensityType(densityType)
}