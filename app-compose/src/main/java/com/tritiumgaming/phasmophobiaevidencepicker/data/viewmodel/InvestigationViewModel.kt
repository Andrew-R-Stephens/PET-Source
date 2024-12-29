package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.achievevments.AchievementsStoreModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.equipment.EquipmentStoreModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.possessions.PossessionsStoreModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.InvestigationModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels.MapCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.warning.PhaseWarningModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.MapRepository

class InvestigationViewModel(
    evidenceRepository: EvidenceRepository,
    ghostRepository: GhostRepository,
    difficultyRepository: DifficultyRepository,
    mapRepository: MapRepository,
    private val codexRepository: CodexRepository
): ViewModel() {

    var mapCarouselModel: MapCarouselModel
    var difficultyCarouselModel: DifficultyCarouselModel
    var investigationModel: InvestigationModel
    var sanityModel: SanityModel

    var timerModel: PhaseTimerModel? = null
    var phaseWarnModel: PhaseWarningModel? = null

    val equipmentStoreModel: EquipmentStoreModel
        get() = codexRepository.equipmentStore
    val possessionsStoreModel: PossessionsStoreModel
        get() = codexRepository.possessionsStore
    val achievementsStoreModel: AchievementsStoreModel
        get() = codexRepository.achievementsStore

    var sanityRunnable: SanityRunnable? = null

    fun reset() {
        timerModel?.reset()
        investigationModel.reset()
        sanityModel.reset()
        phaseWarnModel?.reset()
    }

    init {
        mapCarouselModel = MapCarouselModel(mapRepository, timerModel)
        difficultyCarouselModel = DifficultyCarouselModel(difficultyRepository, timerModel, phaseWarnModel)

        investigationModel = InvestigationModel(
            ghostRepository, evidenceRepository, mapCarouselModel, difficultyCarouselModel)

        sanityModel = SanityModel(difficultyCarouselModel, mapCarouselModel, timerModel)

        timerModel = PhaseTimerModel(sanityModel, difficultyCarouselModel)
        phaseWarnModel = PhaseWarningModel(sanityModel)
    }

    class InvestigationFactory(
        private val evidenceRepository: EvidenceRepository,
        private val ghostRepository: GhostRepository,
        private val difficultyRepository: DifficultyRepository,
        private val mapRepository: MapRepository,
        private val codexRepository: CodexRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InvestigationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InvestigationViewModel(
                    evidenceRepository,
                    ghostRepository,
                    difficultyRepository,
                    mapRepository,
                    codexRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

                val evidenceRepository: EvidenceRepository = appKeyContainer.evidenceRepository
                val ghostRepository: GhostRepository = appKeyContainer.ghostRepository
                val difficultyRepository: DifficultyRepository = appKeyContainer.difficultyRepository
                val mapRepository: MapRepository = appKeyContainer.mapRepository
                val codexRepository: CodexRepository = appKeyContainer.codexRepository

                InvestigationViewModel(
                    evidenceRepository = evidenceRepository,
                    ghostRepository = ghostRepository,
                    difficultyRepository = difficultyRepository,
                    mapRepository = mapRepository,
                    codexRepository = codexRepository,
                )
            }
        }
    }
}
