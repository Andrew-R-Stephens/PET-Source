package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.controller

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityModel.SanityConstants.MAX_SANITY
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanitySeekBarView : AppCompatSeekBar {

    private lateinit var evidenceViewModel: EvidenceViewModel

    var onProgressChangedListener: OnSanityBarProgressChangedListener? = null
    abstract class OnSanityBarProgressChangedListener { abstract fun onChange() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(evidenceViewModel: EvidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    evidenceViewModel.sanityModel?.setStartTimeFromProgress(progress)

                    onProgressChangedListener?.onChange()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        initObservables()
    }

    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.sanityModel?.sanityLevel?.collectLatest {
                Log.d("SanityLevel", "$it")
                val newProgress = (100 - it).toInt()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setProgress(newProgress, true)
                } else {
                    progress = newProgress
                }
            }
        }
    }

}
