package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

class SanitySeekBarView : AppCompatSeekBar {

    private lateinit var evidenceViewModel: EvidenceViewModel
    private var sanityPercentTextView: AppCompatTextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init(evidenceViewModel: EvidenceViewModel, sanityPercentTextView: AppCompatTextView?) {
        this.evidenceViewModel = evidenceViewModel
        this.sanityPercentTextView = sanityPercentTextView

        progress = evidenceViewModel.sanityData?.sanityActual?.toInt() ?: 0

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    evidenceViewModel.sanityData?.setProgressManually(progress.toLong())
                    evidenceViewModel.sanityData?.tick()

                    /*sanityPercentTextView.setText(sanityData.toPercentString());*/
                    if (sanityPercentTextView != null) {
                        sanityPercentTextView.text = formatSanityPercent()
                        //sanityPercentTextView.invalidate();
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        if (sanityPercentTextView != null) {
            sanityPercentTextView.text = formatSanityPercent()
        }
    }

    @SuppressLint("DefaultLocale")
    fun formatSanityPercent(): String {
        val nbsp = "\u00A0"

        var percentStr = evidenceViewModel.sanityData?.toPercentString() ?: "NA"
        percentStr = percentStr.replace(nbsp, "").trim { it <= ' ' }

        var percentNum = 100
        try {
            percentNum = percentStr.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        val input = String.format("%3d", percentNum)

        val output = StringBuilder()
        var i = 0
        while (i < input.length) {
            if (input[i] != '0') {
                break
            }
            output.append(nbsp)
            i++
        }
        while (i < input.length) {
            output.append(input[i])
            i++
        }
        output.append("%")

        return output.toString()
    }

    fun updateProgress() {
        progress = evidenceViewModel.sanityData?.sanityActual?.toInt() ?: 0
        if (sanityPercentTextView != null) {
            sanityPercentTextView!!.text = formatSanityPercent()
        }
        invalidate()
    }

    fun resetProgress() {
        progress = 0
        if (sanityPercentTextView != null) {
            sanityPercentTextView!!.text = formatSanityPercent()
        }
        invalidate()
    } /*
    @Override
    public void invalidate() {
        super.invalidate();

        sanityPercentTextView.setText(formatSanityPercent());
    }
    */
}
