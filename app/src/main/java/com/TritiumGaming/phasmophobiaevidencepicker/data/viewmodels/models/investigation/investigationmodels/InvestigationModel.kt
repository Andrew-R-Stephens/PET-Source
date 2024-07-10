package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    context: Context,
    val investigationViewModel: InvestigationViewModel?
) {
    val ghostListModel: GhostListModel = GhostListModel()
    val evidenceListModel: EvidenceListModel = EvidenceListModel()
    val ghostOrderModel: GhostOrderModel = GhostOrderModel(ghostListModel)

    private val _radioButtonsChecked : MutableStateFlow<SnapshotStateList<Int>> =
        MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    private var rejectionPile: BooleanArray? = null

    init {
        evidenceListModel.init(context)
        ghostListModel.init(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
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

    fun setRadioButtonChecked(evidenceIndex: Int, buttonIndex: Int) {
        try { _radioButtonsChecked.value[evidenceIndex] = buttonIndex }
        catch (ex : IndexOutOfBoundsException) { ex.printStackTrace() }
    }


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

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        resetRadioButtonsChecked()
        evidenceListModel.reset()
        ghostListModel.reset()
        ghostOrderModel.reset()
        createRejectionPile()
    }

}