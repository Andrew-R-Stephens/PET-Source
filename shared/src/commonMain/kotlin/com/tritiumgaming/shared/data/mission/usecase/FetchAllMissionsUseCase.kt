package com.tritiumgaming.shared.data.mission.usecase

class FetchAllMissionsUseCase(
        private val missionRepository: com.tritiumgaming.shared.data.mission.repository.MissionRepository
    ) {
        operator fun invoke(): Result<List<com.tritiumgaming.shared.data.mission.model.Mission>> {
            
            val result = missionRepository.getMissions()
            
            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get missions", it)) }
            
            return missionRepository.getMissions()
        }
    }
    