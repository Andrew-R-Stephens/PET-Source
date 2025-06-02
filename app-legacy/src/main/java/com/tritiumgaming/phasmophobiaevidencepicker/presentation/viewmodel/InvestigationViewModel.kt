package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.InvestigationModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.DifficultyCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels.MapCarouselModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.warning.PhaseWarningModel

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

    fun init() {
        investigationModel =
            investigationModel ?: InvestigationModel(getApplication(), this)

        mapCarouselModel = mapCarouselModel
            ?: MapCarouselModel(getApplication(), this)
        difficultyCarouselModel = difficultyCarouselModel
            ?: DifficultyCarouselModel(getApplication(), this)

        timerModel = timerModel ?: PhaseTimerModel(this)
        phaseWarnModel = phaseWarnModel ?: PhaseWarningModel(this)

        sanityModel = sanityModel ?: SanityModel(this)
    }

    fun reset() {
        timerModel?.reset()
        investigationModel?.reset()
        sanityModel?.reset()
        phaseWarnModel?.reset()
    }

}