package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.DifficultyCarouselData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.MapCarouselData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.PhaseTimerData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.GhostOrderData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.InvestigationData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.Evidence
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.GhostList
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.runnables.SanityRunnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EvidenceViewModel : ViewModel() {
    private val _radioButtonsChecked :  MutableStateFlow<SnapshotStateList<Int>> = MutableStateFlow(mutableStateListOf())
    val radioButtonsChecked = _radioButtonsChecked.asStateFlow()

    private var rejectionPile: BooleanArray? = null

    @JvmField
    var isDrawerCollapsed = false
    @JvmField
    var investigationData: InvestigationData? = null
    var ghostOrderData: GhostOrderData? = null
        private set
    @JvmField
    var sanityRunnable: SanityRunnable? = null
    var sanityData: SanityData? = null
        private set
    var phaseTimerData: PhaseTimerData? = null
        private set
    var mapCarouselData: MapCarouselData? = null
        private set
    var difficultyCarouselData: DifficultyCarouselData? = null
        private set

    fun init(context: Context) {
        initInvestigationData(context)
        initDifficultyCarouselData(context)
        initMapCarouselData()
        initSanityData()
        initGhostOrderData()
        initPhaseTimerData()
    }

    private fun initInvestigationData(context: Context) {
        investigationData = investigationData ?: InvestigationData(context, this)
        if(radioButtonsChecked.value.isEmpty()) createRadioButtonsChecked()
    }

    private fun initPhaseTimerData() {
        phaseTimerData = phaseTimerData ?: PhaseTimerData(difficultyCarouselData)
    }

    private fun initGhostOrderData() {
        ghostOrderData = ghostOrderData ?: GhostOrderData(this)
    }

    private fun initSanityData() {
        sanityData = sanityData ?: SanityData(this)
    }

    private fun initMapCarouselData() {
        mapCarouselData = mapCarouselData ?: MapCarouselData()
    }

    private fun initDifficultyCarouselData(context: Context) {
        difficultyCarouselData =
            difficultyCarouselData ?: DifficultyCarouselData(context, this)
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
            _radioButtonsChecked.value.add(Evidence.Ruling.NEUTRAL.ordinal)
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
        rejectionPile = BooleanArray(GhostList.getCount())
    }

    fun swapStatusInRejectedPile(index: Int): Boolean {
        val pile = getRejectionPile()
        pile!![index] = !pile[index]
        return pile[index]
    }

    private fun updateRejectionPile() {
        rejectionPile = BooleanArray(GhostList.getCount())
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
        if (phaseTimerData!!.timeRemaining <=
            lowerBounds && sanityData!!.getSanityActual() < higherBounds
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
