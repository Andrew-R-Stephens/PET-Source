package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.mapsmenu

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapsViewModel

abstract class MapFragment: InvestigationFragment() {

    protected val mapViewModel: MapsViewModel by activityViewModels { MapsViewModel.Factory }

}