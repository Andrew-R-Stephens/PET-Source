package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.MissionData

class UpdateOperationMissionDataUseCase(private val repository: OperationRepository) {
    operator fun invoke(missionData: MissionData) = repository.updateMissionData(missionData)
}
