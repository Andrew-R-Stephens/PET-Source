package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel

class SanitySeekBarView : AppCompatSeekBar {

    private var sanityData: SanityModel? = null
    private var sanityPercentTextView: AppCompatTextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun init(sanityData: SanityModel, sanityPercentTextView: AppCompatTextView?) {
        this.sanityData = sanityData
        this.sanityPercentTextView = sanityPercentTextView

        progress = sanityData.sanityActual.toInt()

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    sanityData.setProgressManually(progress.toLong())
                    sanityData.tick()

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

        var percentStr = sanityData!!.toPercentString()
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
        progress = sanityData!!.sanityActual.toInt()
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
