package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.evidence

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.RulingGroup

class EvidenceView : ConstraintLayout {

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.item_investigation_evidence, this)
        setDefaults()
    }

    private fun setDefaults() {
        val params =
            LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1f
            )
        layoutParams = params
    }

    fun build(investigationViewModel: InvestigationViewModel, groupIndex: Int, ghostList: LinearLayout) {
        val nameView = findViewById<AppCompatTextView>(R.id.label_name)
        investigationViewModel.investigationModel?.evidenceListModel?.evidenceList
            ?.get(groupIndex)?.name?.let{ resId ->
            nameView?.text = context.getString(resId)
        }
        investigationViewModel.investigationModel?.let {
            val radioGroupComposable = findViewById<ComposeView>(R.id.radioGroup)
            radioGroupComposable?.setContent {
                RulingGroup(
                    investigationModel = it,
                    groupIndex = groupIndex,
                    onClick = { onSelectEvidenceIcon(ghostList) }
                )
            }
        }

        visibility = INVISIBLE
        alpha = 0f

        animate()
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    visibility = VISIBLE
                }
            })
            .alpha(1f)
            .setStartDelay((10f * groupIndex).toLong())
            .setDuration(100)

        val evidenceNameGesture = EvidenceNameGesture()
        val nameDetector = GestureDetector(context, evidenceNameGesture)
        nameView.setOnTouchListener { _: View?, motionEvent: MotionEvent? ->
            motionEvent?.let { nameDetector.onTouchEvent(motionEvent) }
            true
        }
    }

    private fun onSelectEvidenceIcon(ghostContainer: LinearLayout) {
        evidenceViewListener?.onAttemptInvalidate()

        val parentScroller = (ghostContainer.parent as ScrollView)
        parentScroller.smoothScrollTo(0, 0)
    }

    inner class EvidenceNameGesture : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            Log.i("onDown :", e.action.toString())
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            evidenceViewListener?.onCreatePopup()
            return true
        }
    }

    var evidenceViewListener: EvidenceViewListener? = null
    abstract class EvidenceViewListener {
        abstract fun onCreatePopup()
        abstract fun onAttemptInvalidate()
    }
}
