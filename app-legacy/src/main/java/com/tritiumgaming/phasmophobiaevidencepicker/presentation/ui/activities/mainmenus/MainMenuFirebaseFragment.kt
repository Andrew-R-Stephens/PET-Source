package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import androidx.lifecycle.ViewModelProvider
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel

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