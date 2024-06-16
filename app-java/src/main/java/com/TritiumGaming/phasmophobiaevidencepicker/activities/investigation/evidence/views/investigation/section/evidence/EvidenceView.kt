package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.evidence

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.setRulingGroup

abstract class EvidenceView : ConstraintLayout {

    @ColorInt
    var fontEmphasisColor: Int = 0

    constructor(context: Context) :
            super(context) { initView(context) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(context) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(context) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(context) }

    fun initView(context: Context) {
        inflate(context, R.layout.item_investigation_evidence, this)

        val params =
            LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1f
            )
        layoutParams = params

        fontEmphasisColor = getColorFromAttribute(getContext(), R.attr.textColorBodyEmphasis)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun build(evidenceViewModel: EvidenceViewModel, groupIndex: Int, ghostList: LinearLayout) {
        val name = findViewById<AppCompatTextView>(R.id.label_name)
        name.text = evidenceViewModel.investigationData!!.evidenceList
            .list[groupIndex].name

        val radioGroupComposable = findViewById<ComposeView>(R.id.radioGroup)
        setRulingGroup(radioGroupComposable, evidenceViewModel, groupIndex) {
            onSelectEvidenceIcon(ghostList)
        }

        visibility = INVISIBLE
        alpha = 0f

        animate()
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    visibility = VISIBLE
                }
            }
            )
            .alpha(1f)
            .setStartDelay((10f * groupIndex).toLong())
            .setDuration(100)

        val evidenceNameGesture = EvidenceNameGesture()
        val nameDetector =
            GestureDetector(context, evidenceNameGesture)
        name.setOnTouchListener { _: View?, motionEvent: MotionEvent? ->
            nameDetector.onTouchEvent(
                motionEvent!!
            )
        }
    }

    private fun onSelectEvidenceIcon(ghostContainer: LinearLayout) {
        requestInvalidateGhostContainer()

        val parentScroller = (ghostContainer.parent as ScrollView)
        parentScroller.smoothScrollTo(0, 0)
    }

    inner class EvidenceNameGesture : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            Log.i("onDown :", e.action.toString())
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            createPopup()
            return true
        }
    }

    abstract fun createPopup()

    abstract fun requestInvalidateGhostContainer()
}
