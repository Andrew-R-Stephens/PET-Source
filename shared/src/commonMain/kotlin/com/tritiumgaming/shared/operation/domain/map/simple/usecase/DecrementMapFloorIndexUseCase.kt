package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class DecrementMapFloorIndexUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(currentMapId: String, currentFloorIndex: Int): Result<Int> {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Maps could not be obtained", it))
        }

        return result.getOrNull()?.let { map ->
            val floorCount = map.first{ m -> m.mapId == currentMapId }.floorCount
            println("Maps Floor Count: $floorCount")

            var newIndex: Int = currentFloorIndex - 1
            if (newIndex < 0) { newIndex = floorCount - 1 }
            Result.success(newIndex)
        } ?: Result.failure(Exception("Index could not be decremented"))

    }

}