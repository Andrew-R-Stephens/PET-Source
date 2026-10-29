package com.tritiumgaming.data.map.usecase

import com.tritiumgaming.data.map.repository.map.MapRepository

class GetMapsFlowUseCase(
    private val repository: MapRepository
) {
    operator fun invoke() = repository.mapFlow
}