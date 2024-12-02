package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.missions.views.objectivelayout

import android.content.Context
import android.util.AttributeSet
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.ObjectivesViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton


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
            objectivesViewModel?.spinnerCompletionStatus?.get(missionId) ?: false) {
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
