package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.evidence.views.section.ghost

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
import androidx.annotation.IntegerRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.GhostScoreModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

class GhostView : ConstraintLayout {

    private var investigationViewModel: InvestigationViewModel? = null
    private var ghostScore: GhostScoreModel.GhostScore? = null

    @IntegerRes private var neutralSelColor = 0
    @IntegerRes private var negativeSelColor = 0
    @IntegerRes private var positiveSelColor = 0

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
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
    fun build(investigationViewModel: InvestigationViewModel, groupIndex: Int) {
        this.investigationViewModel = investigationViewModel
        this.ghostScore =
            investigationViewModel.investigationModel?.ghostScoreModel?.getGhostScore(groupIndex)

        val nameView = findViewById<AppCompatTextView>(R.id.label_name)
        val iconRowLayout = findViewById<LinearLayoutCompat>(R.id.icon_container)

        nameView.text = ghostScore?.ghostModel?.name?.let { resId -> context.getString(resId) }

        ghostScore?.let { redrawGhostRejectionStatus(it, groupIndex, false) }

        if (iconRowLayout != null) {
            var k = 0
            val evidenceCount = ghostScore?.ghostModel?.normalEvidenceList?.size ?: 0
            Log.d("Ghost", "Icon count = $evidenceCount")
            while (k < evidenceCount) {
                val currentEvidence = ghostScore?.ghostModel?.normalEvidenceList?.get(k) ?: return

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

        val swipeListener = GestureDetector(context, OnSwipeListener(groupIndex))
        nameView.setOnTouchListener { _: View?, motionEvent: MotionEvent? ->
            motionEvent?.let { swipeListener.onTouchEvent(motionEvent) }
            true
        }

        addOnLayoutChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int,
                                    _: Int, _: Int, _: Int, _: Int ->
            ghostScore?.let { redrawGhostRejectionStatus(it, groupIndex, false) }
            if (iconRowLayout != null) {
                ghostScore?.ghostModel?.normalEvidenceList?.forEachIndexed { index, evidenceModel ->
                    val evidenceIcon = iconRowLayout.getChildAt(index)
                        .findViewById<AppCompatImageView>(R.id.evidence_icon)
                    evidenceModel?.let {
                        evidenceIcon.setColorFilter(getRulingColor(it.ruling))
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

        ghostScore?.let { ghostScore ->
            ghostScore.ghostModel.normalEvidenceList.forEachIndexed { index, evidence ->
                val evidenceIcon =
                    iconRowLayout.getChildAt(index) as AppCompatImageView

                evidence.ruling.let { ruling ->
                    evidenceIcon.setColorFilter(getRulingColor(ruling))
                    getRulingColor(ruling)
                }
            }
            redrawGhostRejectionStatus(ghostScore, ghostScore.ghostModel.id, true)
        }
    }

    @IntegerRes
    private fun getRulingColor(ruling: Ruling): Int {
        return when (ruling) {
            Ruling.POSITIVE -> positiveSelColor
            Ruling.NEGATIVE -> negativeSelColor
            Ruling.NEUTRAL -> neutralSelColor
        }
    }

    private fun redrawGhostRejectionStatus(ghostScore: GhostScoreModel.GhostScore, index: Int, animate: Boolean) {
        val statusIcon = findViewById<AppCompatImageView>(R.id.icon_status)

        investigationViewModel?.investigationModel?.getRejectionPile()?.get(index)?.let { rejectionStatus ->
            if (rejectionStatus) { statusIcon.setImageLevel(1) }
            else {
                val score: Int = investigationViewModel?.investigationModel?.ghostScoreModel
                    ?.getGhostScore(ghostScore.ghostModel)?.score ?: 0
                when {
                    score <= -5 -> statusIcon.setImageLevel(2 + (Math.random() * 3).toInt())
                    score == 3 -> statusIcon.setImageLevel(5)
                    else -> statusIcon.setImageLevel(0)
                }
            }
        }
    }

    inner class OnSwipeListener(private val index: Int) : SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent, velX: Float, velY: Float): Boolean {

            investigationViewModel?.let { investigationViewModel ->
                investigationViewModel.investigationModel?.swapStatusInRejectedPile(index)
                investigationViewModel.investigationModel?.ghostScoreModel?.let { ghostScoreModel ->
                    ghostScoreModel.updateOrder()
                    redrawGhostRejectionStatus(
                        ghostScoreModel.getGhostScore(index), index, true)
                }
            }
            return true
        }

        /*
        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent, velX: Float, velY: Float): Boolean {
            investigationViewModel?.let { investigationViewModel ->
                investigationViewModel.investigationModel?.swapStatusInRejectedPile(index)
                investigationViewModel.investigationModel?.ghostScoreModel?.updateOrder()
                investigationViewModel.investigationModel?.ghostScoreModel
                    ?.ghostScores?.get(index)?.let { ghostScore ->
                        redrawGhostRejectionStatus(ghostScore, index, true)
                    }
            }
            return true
        }
        */

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            ghostViewListener?.onCreatePopup()
            return super.onSingleTapConfirmed(e)
        }
    }

    var ghostViewListener: GhostViewListener? = null
    abstract class GhostViewListener {
        abstract fun onCreatePopup()
    }
}
