package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.missions.views.objectivelayout

import android.content.Context
import android.util.AttributeSet
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.views.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.ObjectivesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute


class MissionsCompletedButton : PETImageButton {

    var objectivesViewModel: ObjectivesViewModel? = null

    var missionId: Int = 0

    constructor(context: Context) : super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) { initView() }

    private fun initView() { }

    fun init(objectivesViewModel: ObjectivesViewModel?, missionId: Int) {
        this.objectivesViewModel = objectivesViewModel
        this.missionId = missionId

        setState()

        setOnClickListener{
            toggleState()
            missionCompleteButtonListener?.onClick()
        }
    }

    private fun setState(
        isComplete: Boolean =
            objectivesViewModel?.spinnerCompletionStatus?.get(missionId) == true) {
        if(isComplete)
            setColorFilter(getColorFromAttribute(context, R.attr.selectedColor))
        else {
            clearColorFilter()
            invalidate()
        }
    }

    private fun toggleState() {
        objectivesViewModel?.toggleCompletionStatus(missionId)

        setState()
    }

    var missionCompleteButtonListener: MissionCompleteButtonListener? = null
    abstract class MissionCompleteButtonListener {
        abstract fun onClick()
    }
}
