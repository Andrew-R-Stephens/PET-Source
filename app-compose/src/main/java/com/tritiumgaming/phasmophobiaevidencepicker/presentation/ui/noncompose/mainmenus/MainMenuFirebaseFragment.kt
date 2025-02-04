package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.mainmenus

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel

@Deprecated("Migrate to Jetpack Compose")
abstract class MainMenuFirebaseFragment :
    FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    /*override fun saveStates() {}*/
}