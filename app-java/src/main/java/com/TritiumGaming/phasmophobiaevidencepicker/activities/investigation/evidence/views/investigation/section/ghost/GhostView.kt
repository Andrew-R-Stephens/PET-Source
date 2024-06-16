package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.ghost

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.IntegerRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

abstract class GhostView : ConstraintLayout {

    private var evidenceViewModel: EvidenceViewModel? = null

    private var ghostData: GhostModel? = null

    @IntegerRes private var neutralSelColor = 0
    @IntegerRes private var negativeSelColor = 0
    @IntegerRes private var positiveSelColor = 0

    constructor(context: Context) :
            super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView() }

    fun initView() {
        inflate(context, R.layout.item_investigation_ghost, this)

        setDefaults()
    }

    private fun setDefaults() {
        layoutParams =
            LinearLayoutCompat.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1f
            )

        neutralSelColor = getColorFromAttribute(context, R.attr.neutralSelColor)
        negativeSelColor = getColorFromAttribute(context, R.attr.negativeSelColor)
        positiveSelColor = getColorFromAttribute(context, R.attr.positiveSelColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun build(evidenceViewModel: EvidenceViewModel, groupIndex: Int) {
        this.evidenceViewModel = evidenceViewModel
        this.ghostData = evidenceViewModel.investigationData?.ghostList?.getAt(groupIndex)

        val nameView = findViewById<AppCompatTextView>(R.id.label_name)
        val iconRowLayout = findViewById<LinearLayoutCompat>(R.id.icon_container)

        nameView.text = ghostData?.name

        ghostData?.let { redrawGhostRejectionStatus(it, groupIndex, false) }

        if (iconRowLayout != null) {
            var k = 0
            while (k < (ghostData?.getEvidence()?.size ?: 0)) {
                val currentEvidence = ghostData?.getEvidence()?.get(k) ?: return

                val evidenceIcon =
                    iconRowLayout.getChildAt(k) as AppCompatImageView

                evidenceIcon.setImageResource(currentEvidence.icon)

                when (currentEvidence.ruling) {
                    Ruling.POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor)
                    Ruling.NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor)
                    Ruling.NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor)
                }
                k++
            }

            while (k < iconRowLayout.childCount) {
                iconRowLayout.getChildAt(k).visibility = GONE
                k++
            }
        }

        visibility = INVISIBLE
        alpha = 0f
        id = groupIndex

        val swipeListener =
            GestureDetector(context, GhostSwipeListener(groupIndex))

        nameView.setOnTouchListener { v: View?, motionEvent: MotionEvent? ->
            swipeListener.onTouchEvent(
                motionEvent!!
            )
            true
        }

        addOnLayoutChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int,
                                    _: Int, _: Int, _: Int, _: Int ->
            ghostData?.let { redrawGhostRejectionStatus(it, groupIndex, false) }
            if (iconRowLayout != null) {
                ghostData?.getEvidence()?.forEachIndexed { index, evidenceModel ->
                    val evidenceIcon = iconRowLayout.getChildAt(index)
                        .findViewById<AppCompatImageView>(R.id.evidence_icon)

                    when (evidenceModel.ruling) {
                        Ruling.POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor)
                        Ruling.NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor)
                        Ruling.NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor)
                    }
                }
            }
        }

        post {
            animate()
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        visibility = VISIBLE
                    }
                }
                ).alpha(1f).setStartDelay((10f * groupIndex).toLong()).setDuration(100)
        }
    }

    fun forceUpdateComponents() {
        val iconRowLayout = findViewById<LinearLayoutCompat>(R.id.icon_container) ?: return

        ghostData?.evidence?.forEachIndexed { index, _ ->
            val evidenceIcon =
                iconRowLayout.getChildAt(index) as AppCompatImageView

            val ruling = ghostData?.evidence?.get(index)?.ruling ?: return
            when (ruling) {
                Ruling.POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor)
                Ruling.NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor)
                Ruling.NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor)
            }
        }

        redrawGhostRejectionStatus(ghostData!!, ghostData!!.id, true)
    }

    private fun redrawGhostRejectionStatus(ghost: GhostModel, index: Int, animate: Boolean) {
        val score = ghost.evidenceScore
        val statusIcon = findViewById<AppCompatImageView>(R.id.icon_status)

        val rejectionStatus = evidenceViewModel?.getRejectionPile()?.get(index) ?: return

        if (rejectionStatus) { statusIcon.setImageLevel(1) }
        else {
            when {
                score <= -5 -> statusIcon.setImageLevel(2 + (Math.random() * 3).toInt())
                score == 3 -> statusIcon.setImageLevel(5)
                else -> statusIcon.setImageLevel(0)
            }
        }
    }

    inner class GhostSwipeListener(private val index: Int) : SimpleOnGestureListener() {
        override fun onFling(
            event1: MotionEvent?, event2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            val status = evidenceViewModel?.swapStatusInRejectedPile(index) == false

            evidenceViewModel?.ghostOrderData?.updateOrder()

            evidenceViewModel?.investigationData?.ghostList?.getAt(index)?.let {
                redrawGhostRejectionStatus(it, index, true)
            }

            /*
            val params = Bundle()
            params.putString("event_type", "ghost_swiped")
            params.putString("event_details", if (status) "ghost_impartial" else "ghost_rejected")

            analytics.logEvent("event_investigation", params);
            */
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            createPopup()

            return super.onSingleTapConfirmed(e)
        }
    }

    abstract fun createPopup()
}
