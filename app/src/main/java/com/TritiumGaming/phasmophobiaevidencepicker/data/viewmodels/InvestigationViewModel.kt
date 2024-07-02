package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.runnables.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.MapCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InvestigationViewModel : ViewModel() {

    private val _radioButtonsChecked :  MutableStateFlow<SnapshotStateList<Int>> = MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    var investigationModel: InvestigationModel? = null

    var sanityModel: SanityModel? = null
        private set

    var timerModel: PhaseTimerModel? = null
        private set

    var mapCarouselModel: MapCarouselModel? = null
        private set
    var difficultyCarouselModel: DifficultyCarouselModel? = null
        private set

    var sanityRunnable: SanityRunnable? = null

    private var rejectionPile: BooleanArray? = null

    var isDrawerCollapsed = false

    fun init(context: Context) {
        initInvestigationModel(context)

        mapCarouselModel =
            mapCarouselModel ?: MapCarouselModel(context, this)
        difficultyCarouselModel =
            difficultyCarouselModel ?: DifficultyCarouselModel(context, this)
        timerModel = timerModel ?: PhaseTimerModel(this)

        sanityModel = sanityModel ?: SanityModel(this)
    }

    private fun initInvestigationModel(context: Context) {
        investigationModel =
            investigationModel ?: InvestigationModel(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    private fun createRadioButtonsChecked() {
        _radioButtonsChecked.value.clear()
        investigationModel?.evidenceListModel?.evidenceList?.forEach { _ ->
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
                    investigationModel?.ghostListModel?.getAt(i)?.forcefullyRejected == true
            }
        }
    }

    fun getRejectionPile(): BooleanArray? {
        rejectionPile ?: updateRejectionPile()
        return rejectionPile
    }

    fun getRejectedStatus(index: Int): Boolean {
        getRejectionPile()?.let { pile ->
            return !(index >= 0 && index < pile.size)
        }
        return false
    }

    fun skipSanityToPercent(lowerBounds: Int, higherBounds: Int, newValue: Int) {
        if (((timerModel?.timeRemaining?.value ?: 0L) <= lowerBounds) &&
            ((sanityModel?.sanityLevel?.value ?: 0f) < higherBounds)) {
            sanityModel?.setStartTimeFromProgress(newValue)
        }
    }

    fun reset() {
        resetRadioButtonsChecked()
        createRejectionPile()
        timerModel?.reset()
        investigationModel?.reset()
        sanityModel?.reset()
    }

}
