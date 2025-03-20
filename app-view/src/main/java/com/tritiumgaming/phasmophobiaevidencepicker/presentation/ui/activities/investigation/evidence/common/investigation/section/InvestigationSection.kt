package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

class InvestigationSection : ConstraintLayout {
    constructor(context: Context) :
            super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView() }

    private fun initView() {
        inflate(context, R.layout.item_evidence_tool_section, this)
    }

    fun setLabel(label: String?) {
        (findViewById<View>(R.id.label_container) as TextView).text = label
    }

    fun getList(): ViewStub? {
        return findViewById(R.id.list)
    }
}
