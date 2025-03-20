package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.MapCarouselModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    val ghostRepository: GhostRepository,
    val evidenceRepository: EvidenceRepository,
    val mapCarouselModel: MapCarouselModel?,
    val difficultyCarouselModel: DifficultyCarouselModel?
) {

    companion object {
        const val TOOL_SANITY = 0
        const val TOOL_MODIFIER_DETAILS = 1
    }

    val ghostScoreModel: GhostScoreModel = GhostScoreModel(
        evidenceRepository, ghostRepository, mapCarouselModel, difficultyCarouselModel
    )

    private val _radioButtonsChecked : MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()
    fun setRadioButtonChecked(evidenceIndex: Int, buttonIndex: Int) {
        try { _radioButtonsChecked.value[evidenceIndex] = buttonIndex }
        catch (ex : IndexOutOfBoundsException) { ex.printStackTrace() }
    }
    private fun createRadioButtonsChecked() {
        _radioButtonsChecked.value.clear()

        evidenceRepository.evidenceList.forEach { _ ->
            _radioButtonsChecked.value.add(EvidenceModel.Ruling.NEUTRAL.ordinal)
        }
    }
    private fun resetRadioButtonsChecked() {
        _radioButtonsChecked.value.fill(1)
    }

    private var rejectionPile: BooleanArray? = null
    private fun createRejectionPile() {
        rejectionPile = BooleanArray(ghostScoreModel.ghostScores.value.size)
    }
    fun swapStatusInRejectedPile(index: Int): Boolean {
        val pile = getRejectionPile()
        pile!![index] = !pile[index]
        return pile[index]
    }
    private fun updateRejectionPile() {
        rejectionPile = BooleanArray(ghostScoreModel.ghostScores.value.size)
        rejectionPile?.let { rejectionPile ->
            for (i in rejectionPile.indices) {
                rejectionPile[i] =
                    ghostScoreModel.getGhostScore(i).forcefullyRejected == true
            }
        }
    }
    fun getRejectionPile(): BooleanArray? {
        rejectionPile ?: updateRejectionPile()
        return rejectionPile
    }

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(isCollapsed: Boolean) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        _isInvestigationToolsDrawerCollapsed.value = !(isInvestigationToolsDrawerCollapsed.value)
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> = MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    init {
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        resetRadioButtonsChecked()
        evidenceRepository.reset()
        ghostScoreModel.reset()
        ghostScoreModel.reset()
        createRejectionPile()
    }


}