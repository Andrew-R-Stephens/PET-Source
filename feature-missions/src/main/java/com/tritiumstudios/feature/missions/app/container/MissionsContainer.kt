package com.tritiumstudios.feature.missions.app.container

import android.content.Context
import com.tritiumgaming.data.ghostname.repository.GhostNameRepositoryImpl
import com.tritiumgaming.data.ghostname.source.GhostNameDataSource
import com.tritiumgaming.data.ghostname.source.local.GhostNameLocalDataSource
import com.tritiumgaming.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.data.mission.source.MissionDataSource
import com.tritiumgaming.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.shared.data.ghostname.repository.GhostNameRepository
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.data.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.data.mission.repository.MissionRepository
import com.tritiumgaming.shared.data.mission.usecase.FetchAllMissionsUseCase

class MissionsContainer(
    applicationContext: Context
) {
    // Mission
    internal val missionRepository: MissionRepository by lazy {
        val missionLocalDataSource: MissionDataSource = MissionLocalDataSource(
            applicationContext = applicationContext,
        )
        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )
    }
    internal val fetchAllMissionsUseCase = FetchAllMissionsUseCase(
        missionRepository = missionRepository
    )

    // Ghost Name
    internal val ghostNameRepository: GhostNameRepository by lazy {
        val ghostNameLocalDataSource: GhostNameDataSource = GhostNameLocalDataSource(
            applicationContext = applicationContext
        )
        GhostNameRepositoryImpl(
            localSource = ghostNameLocalDataSource
        )
    }
    internal val fetchAllFirstNamesUseCase = FetchAllFirstNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllMaleNamesUseCase = FetchAllMaleNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllFemaleNamesUseCase = FetchAllFemaleNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllSurnamesUseCase = FetchAllSurnamesUseCase(
        repository = ghostNameRepository
    )

}
