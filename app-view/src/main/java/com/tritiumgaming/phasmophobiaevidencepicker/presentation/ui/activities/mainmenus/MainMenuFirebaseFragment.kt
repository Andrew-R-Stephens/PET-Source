package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.FirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel

abstract class MainMenuFirebaseFragment : FirebaseFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun initViewModels() { super.initGlobalPreferencesViewModel() }

    override fun saveStates() {}
}