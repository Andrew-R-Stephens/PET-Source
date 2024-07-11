package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.InvestigationModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.DifficultyCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.carousels.MapCarouselModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.sanity.SanityRunnable
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.warning.PhaseWarningModel

class InvestigationViewModel(application: Application): AndroidViewModel(application) {

    var investigationModel: InvestigationModel? = null

    var sanityModel: SanityModel? = null
        private set

    var timerModel: PhaseTimerModel? = null
        private set
    var phaseWarnModel: PhaseWarningModel? = null
        private set

    var mapCarouselModel: MapCarouselModel? = null
        private set
    var difficultyCarouselModel: DifficultyCarouselModel? = null
        private set

    var sanityRunnable: SanityRunnable? = null

    var isDrawerCollapsed = false

    init {
        initInvestigationModel(application)

        mapCarouselModel = mapCarouselModel ?: MapCarouselModel(application, this)
        difficultyCarouselModel =
            difficultyCarouselModel ?: DifficultyCarouselModel(application, this)
        timerModel = timerModel ?: PhaseTimerModel(this)
        phaseWarnModel = phaseWarnModel ?: PhaseWarningModel(this)

        sanityModel = sanityModel ?: SanityModel(this)
    }

    private fun initInvestigationModel(context: Context) {
        investigationModel =
            investigationModel ?: InvestigationModel(context, this)
    }

    fun reset() {
        timerModel?.reset()
        investigationModel?.reset()
        sanityModel?.reset()
        phaseWarnModel?.reset()
    }

}
