package com.tritiumgaming.shared.data.map.simple.usecase

class IncrementMapFloorIndexUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {
    operator fun invoke(currentMapId: String, currentFloorIndex: Int): Result<Int> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Maps could not be obtained", it))
        }

        return result.getOrNull()?.let { map ->
            val floorCount = map.first{ m -> m.mapId == currentMapId }.floorCount
            println("Maps Floor Count: $floorCount")

            var newIndex: Int = currentFloorIndex + 1
            if (newIndex >= floorCount) { newIndex = 0 }
            Result.success(newIndex)
        } ?: Result.failure(Exception("Index could not be incremented"))

    }

}
