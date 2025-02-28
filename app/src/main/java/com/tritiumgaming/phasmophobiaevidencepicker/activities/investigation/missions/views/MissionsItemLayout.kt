package com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.missions.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.missions.views.objectivelayout.MissionsCompletedButton
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.missions.views.objectivelayout.MissionsSpinner
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.ObjectivesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.ObjectivesViewModel.Companion.NOT_COMPLETE

class MissionsItemLayout : LinearLayout {

    var objectivesViewModel: ObjectivesViewModel? = null

    var button: MissionsCompletedButton? = null
    var spinner: MissionsSpinner? = null
    var strikethrough: AppCompatImageView? = null

    var missionId: Int = 0

    constructor(context: Context) : super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    private fun initView(attrs: AttributeSet? = null) {
        inflate(context, R.layout.layout_objectives_spinner, this)
        setDefaults()

        button = findViewById(R.id.evidence_button_completed)
        spinner = findViewById(R.id.objectives_item)
        strikethrough = findViewById(R.id.completionStrikethrough)

        val titleTextView = findViewById<AppCompatTextView>(R.id.label_objectivetitle)
        attrs?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.MissionsItemLayout)

            val title =
                typedArray.getString(R.styleable.MissionsItemLayout_MissionsItemLayoutTitle)
            titleTextView?.text = title

            typedArray.recycle()
        }
    }

    private fun setDefaults() {
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        layoutParams = params
        orientation = VERTICAL
    }

    fun init(objectivesViewModel: ObjectivesViewModel, missionId: Int) {
        this.objectivesViewModel = objectivesViewModel
        this.missionId = missionId

        button?.init(objectivesViewModel, missionId)
        spinner?.init(objectivesViewModel, missionId)

        if(objectivesViewModel.spinnerCompletionStatus[missionId] == NOT_COMPLETE ) {
            strikethrough?.visibility = GONE }
        else { strikethrough?.visibility = VISIBLE }

        button?.missionCompleteButtonListener =
            object: MissionsCompletedButton.MissionCompleteButtonListener() {
                override fun onClick() {
                    if(objectivesViewModel.spinnerCompletionStatus[missionId] == NOT_COMPLETE ) {
                        strikethrough?.visibility = GONE }
                    else { strikethrough?.visibility = VISIBLE }
                }
            }
    }

}
