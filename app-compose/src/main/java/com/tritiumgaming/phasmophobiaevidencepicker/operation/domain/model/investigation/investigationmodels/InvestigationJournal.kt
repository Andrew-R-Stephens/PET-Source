package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.EvidenceRulingHandler
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationJournal(
    ghostRepository: GhostRepository,
    evidenceRepository: EvidenceRepository,
) {

    private val evidenceHandler: EvidenceRulingHandler = EvidenceRulingHandler(evidenceRepository)
    private val ghostScoreHandler: GhostScoreHandler = GhostScoreHandler(ghostRepository)

    val ruledEvidence = evidenceHandler.ruledEvidence
    val scores = ghostScoreHandler.scores
    val order = ghostScoreHandler.order

    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence? {
        return evidenceHandler.getRuledEvidence(evidenceModel)
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: RuledEvidence.Ruling) {
        evidenceHandler.setEvidenceRuling(evidenceIndex, ruling)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: RuledEvidence.Ruling) {
        evidenceHandler.setEvidenceRuling(evidence, ruling)
    }

    fun getGhostScore(ghostModel: GhostType): StateFlow<Int>? {
        return ghostScoreHandler.getGhostScorePoints(ghostModel)
    }
    fun setGhostNegation(ghostModel: GhostType, isForceNegated: Boolean) {
        ghostScoreHandler.setForcedNegation(ghostModel, isForceNegated)
    }
    fun toggleGhostNegation(ghostModel: GhostType) {
        ghostScoreHandler.toggleForcedNegation(ghostModel)
    }

    /*private val _evidenceRadioButtonUi : MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    val evidenceRadioButtonUi = _evidenceRadioButtonUi.asStateFlow()
    fun checkEvidenceRadioButtonUi(
        evidenceIndex: Int,
        buttonIndex: Int
    ) {
        try { _evidenceRadioButtonUi.value[evidenceIndex] = buttonIndex }
        catch (ex : IndexOutOfBoundsException) { ex.printStackTrace() }

        updateRejectionPile()
    }
    private fun createEvidenceRadioButtonUi() {
        _evidenceRadioButtonUi.value.clear()

        evidenceRepository.evidenceList.forEach { _ ->
            _evidenceRadioButtonUi.value.add(RuledEvidence.Ruling.NEUTRAL.ordinal)
        }
    }
    private fun resetEvidenceRadioButtonUi() {
        _evidenceRadioButtonUi.value.fill(1)
    }

    private val _rejectionPileUi : MutableStateFlow<SnapshotStateList<Boolean>> =
        MutableStateFlow(mutableStateListOf())
    var rejectionPileUi = _rejectionPileUi.asStateFlow()
    private fun createRejectionPile() {
        for(i in 0 until ghostRepository.count) {
            _rejectionPileUi.value.add(false)
        }
    }
    fun swapStatusInRejectedPile(index: Int): Boolean {
        rejectionPileUi.value[index] = !rejectionPileUi.value[index]
        return rejectionPileUi.value[index]
    }
    private fun updateRejectionPile() {
        rejectionPileUi.value.forEachIndexed { index, it ->
            rejectionPileUi.value[index] =
                ghostScoreHandler.getScores(index)?.forcefullyRejected?.value == true
        }
    }*/

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(isCollapsed: Boolean) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        _isInvestigationToolsDrawerCollapsed.update { !(isInvestigationToolsDrawerCollapsed.value) }
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> = MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    fun reorderGhostScores(difficultyCarouselHandler: DifficultyCarouselHandler) {
        ghostScoreHandler.reorder(evidenceHandler, difficultyCarouselHandler)
    }

    init {
        //if(evidenceRadioButtonUi.value.isEmpty()) createEvidenceRadioButtonUi()

        //createRejectionPile()
    }

    /** Resets the Ruling for each Evidence type */
    fun reset(difficultyCarouselHandler: DifficultyCarouselHandler) {
        //resetEvidenceRadioButtonUi()
        evidenceHandler.reset()
        ghostScoreHandler.reset(
            evidenceHandler,
            difficultyCarouselHandler
        )
        //createRejectionPile()
    }

    companion object {
        const val TOOL_SANITY = 0
        const val TOOL_MODIFIER_DETAILS = 1
    }

}
