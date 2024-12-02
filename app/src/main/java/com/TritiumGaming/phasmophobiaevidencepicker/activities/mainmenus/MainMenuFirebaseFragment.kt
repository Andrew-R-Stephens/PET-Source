package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.fragment.app.activityViewModels
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.FirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.MainMenuViewModel

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    /*override fun saveStates() {}*/
}