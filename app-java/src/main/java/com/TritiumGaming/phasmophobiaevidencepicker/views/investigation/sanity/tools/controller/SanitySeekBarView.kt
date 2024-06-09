package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

class SanitySeekBarView : AppCompatSeekBar {

    private lateinit var evidenceViewModel: EvidenceViewModel

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(evidenceViewModel: EvidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel

        progress = evidenceViewModel.sanityData?.sanityActual?.toInt() ?: 0

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    evidenceViewModel.sanityData?.setProgressManually(progress.toLong())
                    evidenceViewModel.sanityData?.tick()

                    /*sanityPercentTextView.setText(sanityData.toPercentString());*/
                    onProgressChangedListener?.onChange()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    fun updateProgress() {
        progress = evidenceViewModel.sanityData?.sanityActual?.toInt() ?: 0
        onProgressChangedListener?.onChange()
        invalidate()
    }

    fun resetProgress() {
        progress = 0
        onProgressChangedListener?.onReset()
        invalidate()
    }

    var onProgressChangedListener: OnSanityBarProgressChangedListener? = null

    abstract class OnSanityBarProgressChangedListener {
        abstract fun onChange()
        abstract fun onReset()
    }
}
