package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.runnables.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.carousels.MapCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationmodels.GhostOrderModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationmodels.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationtype.evidence.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationtype.ghost.GhostListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EvidenceViewModel : ViewModel() {

    private val _radioButtonsChecked :  MutableStateFlow<SnapshotStateList<Int>> = MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    var investigationData: InvestigationModel? = null
    var ghostOrderData: GhostOrderModel? = null
        private set

    var sanityModel: SanityModel? = null
        private set

    var timerModel: PhaseTimerModel? = null
        private set

    var mapCarouselData: MapCarouselModel? = null
        private set
    var difficultyCarouselData: DifficultyCarouselModel? = null
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
        investigationData = investigationData ?: InvestigationModel(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    private fun initTimerModel() {
        timerModel =
            timerModel ?: PhaseTimerModel(this)
    }

    private fun initGhostOrderData() {
        ghostOrderData =
            ghostOrderData ?: GhostOrderModel(this)
    }

    private fun initSanityModel() {
        sanityModel =
            sanityModel ?: SanityModel(this)
    }

    private fun initMapCarouselModel(context: Context) {
        mapCarouselData =
            mapCarouselData ?: MapCarouselModel(context, this)
    }

    private fun initDifficultyCarouselModel(context: Context) {
        difficultyCarouselData =
            difficultyCarouselData ?: DifficultyCarouselModel(context, this)
    }

    fun hasSanityRunnable(): Boolean {
        return sanityRunnable != null
    }

    fun hasSanityModel(): Boolean {
        return sanityModel != null
    }

    fun hasTimerModel(): Boolean {
        return timerModel != null
    }

    private fun createRadioButtonsChecked() {
        _radioButtonsChecked.value.clear()

        investigationData?.evidenceList?.list?.forEach { _ ->
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
        for (i in rejectionPile!!.indices) {
            rejectionPile!![i] = investigationData!!.ghostList.getAt(i).isForcefullyRejected
        }
    }

    fun getRejectionPile(): BooleanArray? {
        rejectionPile ?: updateRejectionPile()
        return rejectionPile
    }

    fun getRejectedStatus(index: Int): Boolean {
        val pile = getRejectionPile()
        return (
            if (index >= 0 && index < pile!!.size) false
            else pile?.get(index) ?: false
        )
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
        ghostOrderData?.createOrder()
        createRejectionPile()
        timerModel?.reset()
        investigationData?.reset()
        sanityModel?.reset()
    }

}
