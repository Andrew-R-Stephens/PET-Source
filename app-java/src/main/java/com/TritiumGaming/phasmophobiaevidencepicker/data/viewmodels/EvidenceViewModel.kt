package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.MapCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels.GhostOrderModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.EvidenceModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.GhostListModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.runnables.SanityRunnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EvidenceViewModel : ViewModel() {

    private val _radioButtonsChecked :  MutableStateFlow<SnapshotStateList<Int>> = MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    var investigationData: InvestigationModel? = null
    var ghostOrderData: GhostOrderModel? = null
        private set
    var sanityData: SanityModel? = null
        private set
    var phaseTimerData: PhaseTimerModel? = null
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
        initDifficultyCarouselData(context)
        initMapCarouselData()
        initSanityData()
        initGhostOrderData()
        initPhaseTimerData()
    }

    private fun initInvestigationData(context: Context) {
        investigationData = investigationData ?: InvestigationModel(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    private fun initPhaseTimerData() {
        phaseTimerData =
            phaseTimerData ?: difficultyCarouselData?.let { PhaseTimerModel(it) }
    }

    private fun initGhostOrderData() {
        ghostOrderData =
            ghostOrderData ?: GhostOrderModel(this)
    }

    private fun initSanityData() {
        sanityData =
            sanityData ?: SanityModel(this)
    }

    private fun initMapCarouselData() {
        mapCarouselData =
            mapCarouselData ?: MapCarouselModel()
    }

    private fun initDifficultyCarouselData(context: Context) {
        difficultyCarouselData =
            difficultyCarouselData ?: DifficultyCarouselModel(context, this)
    }

    fun hasDifficultyCarouselData(): Boolean {
        return difficultyCarouselData != null
    }

    fun hasSanityRunnable(): Boolean {
        return sanityRunnable != null
    }

    fun hasSanityData(): Boolean {
        return sanityData != null
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

    fun skipSanityToPercent(lowerBounds: Int, higherBounds: Int, newValue: Int) {
        if (
            phaseTimerData!!.timeRemaining <=
            lowerBounds && sanityData!!.sanityActual < higherBounds
        ) {
            sanityData!!.setProgressManually(newValue.toLong())
        }
    }

    fun reset() {
        resetRadioButtonsChecked()
        ghostOrderData?.createOrder()
        createRejectionPile()
        phaseTimerData?.reset()
        investigationData?.reset()
        sanityData?.reset()
        mapCarouselData?.mapCurrentIndex = 0
    }

}
