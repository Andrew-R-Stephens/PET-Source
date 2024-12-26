package com.tritiumgaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.fragment.app.activityViewModels
import com.tritiumgaming.phasmophobiaevidencepicker.activities.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.MainMenuViewModel

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    /*override fun saveStates() {}*/
}