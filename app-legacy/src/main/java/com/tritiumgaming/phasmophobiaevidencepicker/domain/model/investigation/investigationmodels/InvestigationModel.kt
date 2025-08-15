package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceListModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.ghost.GhostListModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    context: Context,
    var investigationViewModel: InvestigationViewModel?
) {
    val ghostListModel: GhostListModel = GhostListModel()
    val evidenceListModel: EvidenceListModel = EvidenceListModel()
    val ghostOrderModel: GhostOrderModel = GhostOrderModel(ghostListModel)

    private val _radioButtonsChecked : MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()
    fun setRadioButtonChecked(evidenceIndex: Int, buttonIndex: Int) {
        try { _radioButtonsChecked.value[evidenceIndex] = buttonIndex }
        catch (ex : IndexOutOfBoundsException) { ex.printStackTrace() }
    }
    private fun createRadioButtonsChecked() {
        _radioButtonsChecked.value.clear()
        evidenceListModel.evidenceList.forEach { _ ->
            _radioButtonsChecked.value.add(EvidenceModel.Ruling.NEUTRAL.ordinal)
        }
    }
    private fun resetRadioButtonsChecked() {
        _radioButtonsChecked.value.fill(1)
    }

    private var rejectionPile: BooleanArray? = null
    private fun createRejectionPile() {
        rejectionPile = BooleanArray(GhostListModel.count)
    }
    fun swapStatusInRejectedPile(index: Int): Boolean {
        val pile = getRejectionPile()
        pile!![index] = !pile[index]
        return pile[index]
    }
    private fun updateRejectionPile() {
        rejectionPile = BooleanArray(GhostListModel.count)
        rejectionPile?.let { rejectionPile ->
            for (i in rejectionPile.indices) {
                rejectionPile[i] =
                    investigationViewModel?.investigationModel?.ghostListModel
                        ?.getAt(i)?.forcefullyRejected == true
            }
        }
    }
    fun getRejectionPile(): BooleanArray? {
        rejectionPile ?: updateRejectionPile()
        return rejectionPile
    }

    private val _isSanityDrawerCollapsed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSanityDrawerCollapsed = _isSanityDrawerCollapsed.asStateFlow()
    fun setDrawerState(isCollapsed: Boolean) {
        _isSanityDrawerCollapsed.update { isCollapsed }
    }
    fun toggleDrawerState() {
        _isSanityDrawerCollapsed.update { !isSanityDrawerCollapsed.value }
    }

    init {
        evidenceListModel.init(context)
        ghostListModel.init(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        resetRadioButtonsChecked()
        evidenceListModel.reset()
        ghostListModel.reset()
        ghostOrderModel.reset()
        createRejectionPile()
    }

}