package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.combined.repository.MapRepository

class DecrementMapFloorIndexUseCase(
    private val mapRepository: MapRepository
) {
    operator fun invoke(currentMapId: String, currentFloorIndex: Int): Result<Int> {
        return Result.success(0)
    }

}