package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.FlashBackground
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.FlashState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
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

    protected var thisPhase: PhaseTimerModel.Phase = PhaseTimerModel.Phase.SETUP
    protected var currentPhase: PhaseTimerModel.Phase? = null

    private var canFlash: Boolean = true
    protected open var activeCondition: () -> Boolean = { false }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.item_flashwarn, this)

        flashView = findViewById(R.id.flashComposable)
        labelView = findViewById(R.id.flashLabel)

        inactiveColor = ColorUtils.getColorFromAttribute(context, R.attr.light_off)
        activeColor = ColorUtils.getColorFromAttribute(context, R.attr.light_inactive)

    }

    open fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        investigationViewModel.timerModel?.currentPhase?.value?.let { value ->
            currentPhase = value
        }

        initObservables()

        update()
    }

    private fun update() {
        val flashState: FlashState =
            when(activeCondition()) {
                true -> {
                    when (currentPhase) {
                        PhaseTimerModel.Phase.HUNT -> {
                            if (currentPhase == thisPhase)
                                if(investigationViewModel?.phaseWarnModel?.canFlash?.value == true) {
                                    FlashState.ACTIVE_ANIMATED
                                } else { FlashState.ACTIVE_STEADY }

                            else { FlashState.ACTIVE_STEADY }
                        }

                        PhaseTimerModel.Phase.SETUP, PhaseTimerModel.Phase.ACTION -> {
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
            investigationViewModel?.timerModel?.currentPhase?.collectLatest {
                currentPhase = it
                update()
            }
        }
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel?.phaseWarnModel?.canFlash?.collectLatest {
                update()
            }
        }
    }
}
