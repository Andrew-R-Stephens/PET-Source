package com.tritiumgaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.lifecycle.ViewModelProvider
import com.tritiumgaming.phasmophobiaevidencepicker.activities.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected var mainMenuViewModel: MainMenuViewModel? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun initViewModels() { super.initGlobalPreferencesViewModel() }

    protected fun initMainMenuViewModel() {
        if (mainMenuViewModel == null) {
            try { mainMenuViewModel =
                    ViewModelProvider(requireActivity())[MainMenuViewModel::class.java]
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun saveStates() {}
}