package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.views.investigation.sanity.tools.timer

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhaseTimerLayout : ConstraintLayout {

    private lateinit var investigationViewModel: InvestigationViewModel

    private lateinit var playToggleButton: PETImageButton
    private lateinit var skipButton: PETImageButton
    private var phaseTimerTextView: AppCompatTextView? = null // TIMER VIEW

   private companion object TimerStates {
       val states: HashMap<Boolean, Int> = HashMap()
       init {
           states[false] = 0
           states[true] = 1
       }
   }

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_tool_timer, this)

        playToggleButton = findViewById(R.id.timer_play_pause)
        phaseTimerTextView = findViewById(R.id.evidence_timer_text)
        skipButton = findViewById(R.id.timer_skip)

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        playToggleButton.setOnClickListener {
            investigationViewModel.timerModel?.toggleTimer()
        }

        skipButton.setOnClickListener {
            investigationViewModel.timerModel?.fastForwardTimer(0)
        }

        phaseTimerTextView?.text = investigationViewModel.timerModel?.displayTime
        setPlayButtonIcon()

        initObservables()
    }

    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.timerModel?.paused?.collectLatest {
                setPlayButtonIcon()
            }
        }

        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.timerModel?.timeRemaining?.collectLatest {
                phaseTimerTextView?.text = investigationViewModel.timerModel?.displayTime
            }
        }

        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.difficultyCarouselModel?.currentIndex?.collectLatest {
                phaseTimerTextView?.text = investigationViewModel.timerModel?.displayTime
            }
        }
    }

    private fun setPlayButtonIcon() {
        playToggleButton.drawable.level =
            states[investigationViewModel.timerModel?.paused?.value == true] ?: 0
    }
}
