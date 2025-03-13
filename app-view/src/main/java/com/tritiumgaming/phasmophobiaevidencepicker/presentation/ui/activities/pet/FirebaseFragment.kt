package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet

abstract class FirebaseFragment : PETFragment {

    protected constructor() : super()

    protected constructor(layout: Int) : super(layout)

    abstract override fun saveStates()
}
