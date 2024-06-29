package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.lifecycle.ViewModelProvider
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.FirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
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

    override fun backPressedHandler() {
        super.backPressedHandler()
    }

    override fun saveStates() {}
}