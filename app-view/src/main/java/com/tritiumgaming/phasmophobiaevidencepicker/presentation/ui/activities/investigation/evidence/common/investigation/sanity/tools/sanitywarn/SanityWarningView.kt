package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.sanity.tools.sanitywarn

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.timer.TimerHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.FlashBackground
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.FlashState
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * SanityWarningView class
 *
 * @author TritiumGamingStudios
 */
abstract class SanityWarningView : ConstraintLayout {

    var investigationViewModel: InvestigationViewModel? = null

    private var flashView: ComposeView? = null
    protected var labelView: AppCompatTextView? = null

    private var inactiveColor: Int = Color.WHITE
    private var activeColor: Int = Color.WHITE

    protected var thisPhase: TimerHandler.Phase = TimerHandler.Phase.SETUP
    protected var currentPhase: TimerHandler.Phase? = null

    private var canFlash: Boolean = true
    protected open var activeCondition: () -> Boolean = { false }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.item_sanity_tool_warn, this)

        flashView = findViewById(R.id.flashComposable)
        labelView = findViewById(R.id.flashLabel)

        inactiveColor = ColorUtils.getColorFromAttribute(context, R.attr.light_off)
        activeColor = ColorUtils.getColorFromAttribute(context, R.attr.light_inactive)

    }

    open fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        currentPhase = investigationViewModel.currentPhase.value

        initObservables()

        update()
    }

    private fun update() {
        val flashState: FlashState =
            when(activeCondition()) {
                true -> {
                    when (currentPhase) {
                        TimerHandler.Phase.HUNT -> {
                            if (currentPhase == thisPhase)
                                if(investigationViewModel?.canFlash?.value == true) {
                                    FlashState.ACTIVE_ANIMATED
                                } else { FlashState.ACTIVE_STEADY }

                            else { FlashState.ACTIVE_STEADY }
                        }

                        TimerHandler.Phase.SETUP, TimerHandler.Phase.ACTION -> {
                            FlashState.ACTIVE_STEADY
                        }

                        else -> { FlashState.OFF }
                    }
                }
                false -> { FlashState.OFF }
            }

        flashView?.setContent { FlashBackground(state = flashState) }

        when(flashState) {
            FlashState.OFF -> { labelView?.setTextColor(inactiveColor) }
            else -> { labelView?.setTextColor(activeColor) }
        }
    }

    private fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel?.currentPhase?.collectLatest {
                currentPhase = it
                update()
            }
        }
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel?.canFlash?.collectLatest {
                update()
            }
        }
    }
}
