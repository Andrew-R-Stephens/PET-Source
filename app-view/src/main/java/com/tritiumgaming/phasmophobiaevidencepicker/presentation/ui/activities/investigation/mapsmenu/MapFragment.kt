package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.mapsmenu

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapViewModel

abstract class MapFragment: InvestigationFragment() {

    protected val mapViewModel: MapViewModel by activityViewModels { MapViewModel.Factory }

}