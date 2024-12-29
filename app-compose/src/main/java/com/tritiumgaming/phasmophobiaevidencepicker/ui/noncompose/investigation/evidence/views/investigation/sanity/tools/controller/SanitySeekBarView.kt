package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.sanity.tools.controller

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SanitySeekBarView : AppCompatSeekBar {

    private var investigationViewModel: InvestigationViewModel? = null
    var onProgressChangedListener: OnSanityBarProgressChangedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    investigationViewModel.sanityModel.progressToStartTime(progress.toFloat())
                    investigationViewModel.sanityModel.tick()
                }
                onProgressChangedListener?.onChange()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) { }
        })

        initObservables()
    }

    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel?.sanityModel?.sanityLevel?.collectLatest {
                val newProgress = (100 - it).toInt()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setProgress(newProgress, true)
                } else {
                    progress = newProgress
                }
            }
        }
    }

    abstract class OnSanityBarProgressChangedListener {
        abstract fun onChange()
    }
}
