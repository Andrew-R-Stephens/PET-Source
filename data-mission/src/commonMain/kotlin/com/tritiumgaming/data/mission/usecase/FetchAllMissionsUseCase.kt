package com.tritiumgaming.data.mission.usecase

import com.tritiumgaming.data.mission.model.Mission
import com.tritiumgaming.data.mission.repository.MissionRepository

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
    