package com.tritiumgaming.data.usecase

import com.tritiumgaming.data.model.properties.DensityType
import com.tritiumgaming.data.repository.GlobalPreferencesRepository

class SetUiDensityTypeUseCase(
    private val repository: GlobalPreferencesRepository
) {
    suspend operator fun invoke(densityType: DensityType) =
        repository.setUiDensityType(densityType)
}