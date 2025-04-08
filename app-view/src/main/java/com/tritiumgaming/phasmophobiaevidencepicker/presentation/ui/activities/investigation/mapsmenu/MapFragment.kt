package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.mapsmenu

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapsViewModel

abstract class MapFragment: InvestigationFragment() {

    protected val mapViewModel: MapsViewModel by activityViewModels { MapsViewModel.Factory }

}