package com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.ui.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    /*override fun saveStates() {}*/
}