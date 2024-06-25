package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.runnables.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.GhostOrderModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.MapCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EvidenceViewModel : ViewModel() {

    private val _radioButtonsChecked :  MutableStateFlow<SnapshotStateList<Int>> = MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    var investigationModel: InvestigationModel? = null
    var ghostOrderModel: GhostOrderModel? = null
        private set

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
        initInvestigationData(context)
        initGhostOrderData()

        initMapCarouselModel(context)
        initDifficultyCarouselModel(context)

        initTimerModel()

        initSanityModel()
    }

    private fun initInvestigationData(context: Context) {
        investigationModel = investigationModel ?: InvestigationModel(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    private fun initTimerModel() {
        timerModel =
            timerModel ?: PhaseTimerModel(this)
    }

    private fun initGhostOrderData() {
        ghostOrderModel =
            ghostOrderModel ?: GhostOrderModel(this)
    }

    private fun initSanityModel() {
        sanityModel =
            sanityModel ?: SanityModel(this)
    }

    private fun initMapCarouselModel(context: Context) {
        mapCarouselModel =
            mapCarouselModel ?: MapCarouselModel(context, this)
    }

    private fun initDifficultyCarouselModel(context: Context) {
        difficultyCarouselModel =
            difficultyCarouselModel ?: DifficultyCarouselModel(context, this)
    }

    fun hasSanityRunnable(): Boolean {
        return sanityRunnable != null
    }

    private fun createRadioButtonsChecked() {
        _radioButtonsChecked.value.clear()

        investigationModel?.evidenceList?.list?.forEach { _ ->
            _radioButtonsChecked.value.add(EvidenceModel.Ruling.NEUTRAL.ordinal)
        }
    }

    private fun resetRadioButtonsChecked() {
        _radioButtonsChecked.value.fill(1)
    }

    fun setRadioButtonChecked(evidenceIndex: Int, buttonIndex: Int) {
        try {
            _radioButtonsChecked.value[evidenceIndex] = buttonIndex
        } catch (ex : IndexOutOfBoundsException) {
            ex.printStackTrace()
        }
    }

    private fun createRejectionPile() {
        rejectionPile = BooleanArray(GhostListModel.getCount())
    }

    fun swapStatusInRejectedPile(index: Int): Boolean {
        val pile = getRejectionPile()
        pile!![index] = !pile[index]
        return pile[index]
    }

    private fun updateRejectionPile() {
        rejectionPile = BooleanArray(GhostListModel.getCount())
        rejectionPile?.let { rejectionPile ->
            for (i in rejectionPile.indices) {
                rejectionPile[i] =
                    investigationModel?.ghostList?.getAt(i)?.isForcefullyRejected == true
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

    /*
    fun skipSanityToPercent(lowerBounds: Int, higherBounds: Int, newValue: Int) {
        if ((timerModel?.timeRemaining?.value ?: 0L) <= lowerBounds &&
            (sanityModel?.sanityActual ?: 0L) < higherBounds) {

            sanityModel?.setProgressManually(newValue.toLong())
        }
    }
    */

    fun reset() {
        resetRadioButtonsChecked()
        ghostOrderModel?.createOrder()
        createRejectionPile()
        timerModel?.reset()
        investigationModel?.reset()
        sanityModel?.reset()
    }

}
