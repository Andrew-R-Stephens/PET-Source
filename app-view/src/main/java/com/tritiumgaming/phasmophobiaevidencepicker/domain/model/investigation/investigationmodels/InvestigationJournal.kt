package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.JournalItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselHandler
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
    val ghostRepository: GhostRepository,
    val evidenceRepository: EvidenceRepository,
) {

    private val evidenceHandler: EvidenceRulingHandler = EvidenceRulingHandler(evidenceRepository)
    private val ghostScoreHandler: GhostScoreHandler = GhostScoreHandler(ghostRepository)

    val ruledEvidence = evidenceHandler.ruledEvidence
    val scores = ghostScoreHandler.scores
    val order = ghostScoreHandler.order

    fun getRuledEvidence(evidenceModel: EvidenceModel): RuledEvidence? {
        return evidenceHandler.getRuledEvidence(evidenceModel)
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: RuledEvidence.Ruling) {
        evidenceHandler.setEvidenceRuling(evidenceIndex, ruling)
    }
    fun setEvidenceRuling(evidence: EvidenceModel, ruling: RuledEvidence.Ruling) {
        evidenceHandler.setEvidenceRuling(evidence, ruling)
    }

    fun getGhostScore(ghostModel: GhostModel): StateFlow<Int>? {
        return ghostScoreHandler.getGhostScorePoints(ghostModel)
    }
    fun setGhostNegation(ghostModel: GhostModel, isForceNegated: Boolean) {
        ghostScoreHandler.setForcedNegation(ghostModel, isForceNegated)
    }
    fun toggleGhostNegation(ghostModel: GhostModel) {
        ghostScoreHandler.toggleForcedNegation(ghostModel)
    }

    private val _popupUi : MutableStateFlow<JournalItemModel?> = MutableStateFlow(null)
    val popupUi = _popupUi.asStateFlow()
    fun setPopupUi(popupModel: JournalItemModel?) {
        _popupUi.update { popupModel }
    }
    fun unsetPopupUi() {
        _popupUi.update { null }
    }

    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    private val _evidenceRadioButtonUi : MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    val evidenceRadioButtonUi = _evidenceRadioButtonUi.asStateFlow()
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    fun checkEvidenceRadioButtonUi(
        evidenceIndex: Int,
        buttonIndex: Int
    ) {
        try { _evidenceRadioButtonUi.value[evidenceIndex] = buttonIndex }
        catch (ex : IndexOutOfBoundsException) { ex.printStackTrace() }

        updateRejectionPile()
    }
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    private fun createEvidenceRadioButtonUi() {
        _evidenceRadioButtonUi.value.clear()

        evidenceRepository.evidenceList.forEach { _ ->
            _evidenceRadioButtonUi.value.add(RuledEvidence.Ruling.NEUTRAL.ordinal)
        }
    }
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    private fun resetEvidenceRadioButtonUi() {
        _evidenceRadioButtonUi.value.fill(1)
    }

    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    private val _rejectionPileUi : MutableStateFlow<SnapshotStateList<Boolean>> =
        MutableStateFlow(mutableStateListOf())
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    var rejectionPileUi = _rejectionPileUi.asStateFlow()
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    private fun createRejectionPile() {
        for(i in 0 until ghostRepository.count) {
            _rejectionPileUi.value.add(false)
        }
    }
    @Deprecated("Unused", level = DeprecationLevel.WARNING)
    fun swapStatusInRejectedPile(index: Int): Boolean {
        rejectionPileUi.value[index] = !rejectionPileUi.value[index]
        return rejectionPileUi.value[index]
    }
    private fun updateRejectionPile() {
        rejectionPileUi.value.forEachIndexed { index, it ->
            rejectionPileUi.value[index] =
                ghostScoreHandler.getScores(index)?.forcefullyRejected?.value == true
        }
    }

    private val _isInvestigationToolsDrawerCollapsed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInvestigationToolsDrawerCollapsed = _isInvestigationToolsDrawerCollapsed.asStateFlow()
    fun setInvestigationToolsDrawerState(isCollapsed: Boolean) {
        _isInvestigationToolsDrawerCollapsed.update { isCollapsed }
    }
    fun toggleInvestigationToolsDrawerState() {
        _isInvestigationToolsDrawerCollapsed.update { !isInvestigationToolsDrawerCollapsed.value }
    }

    private val _investigationToolsCategory: MutableStateFlow<Int> = MutableStateFlow(TOOL_SANITY)
    val investigationToolsCategory = _investigationToolsCategory.asStateFlow()
    fun setInvestigationToolsCategory(categoryIndex: Int) {
        _investigationToolsCategory.value = categoryIndex
    }

    fun reorderGhostScores(difficultyCarouselHandler: DifficultyCarouselHandler) {
        ghostScoreHandler.reorder(evidenceHandler, difficultyCarouselHandler)
    }

    /*
    init {
        if(evidenceRadioButtonUi.value.isEmpty()) createEvidenceRadioButtonUi()

        createRejectionPile()
    }
    */

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