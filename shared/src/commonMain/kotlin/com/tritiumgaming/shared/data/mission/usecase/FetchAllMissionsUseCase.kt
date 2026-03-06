package com.tritiumgaming.shared.data.mission.usecase

import com.tritiumgaming.shared.data.mission.model.Mission
import com.tritiumgaming.shared.data.mission.repository.MissionRepository

class FetchAllMissionsUseCase(
        private val missionRepository: MissionRepository
    ) {
        operator fun invoke(): Result<List<Mission>> {
            
            val result = missionRepository.getMissions()
            
            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get missions", it)) }
            
            return missionRepository.getMissions()
        }
    }
    