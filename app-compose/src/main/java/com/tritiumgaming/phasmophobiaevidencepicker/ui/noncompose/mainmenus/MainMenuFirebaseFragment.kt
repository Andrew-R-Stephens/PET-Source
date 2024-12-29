package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.pet.FirebaseFragment

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    /*override fun saveStates() {}*/
}