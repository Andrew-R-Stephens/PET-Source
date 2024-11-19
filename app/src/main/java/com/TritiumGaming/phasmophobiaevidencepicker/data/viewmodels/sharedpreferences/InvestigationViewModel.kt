package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences

import android.app.Application
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
