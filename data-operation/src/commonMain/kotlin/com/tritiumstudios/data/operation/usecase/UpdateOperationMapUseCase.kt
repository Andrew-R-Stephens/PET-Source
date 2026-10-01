package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.MapData


class UpdateOperationMapUseCase(private val repository: OperationRepository) {
    operator fun invoke(map: MapData) = repository.updateMap(map)
}
