package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.sanitywarn

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.timer.PhaseTimerModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.FlashBackground
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.FlashState

/**
 * SanityWarningView class
 *
 * @author TritiumGamingStudios
 */
abstract class SanityWarningView : ConstraintLayout {

    lateinit var investigationViewModel: InvestigationViewModel

    private var flashView: ComposeView? = null
    protected var labelView: AppCompatTextView? = null

    private var inactiveColor: Int = Color.WHITE
    private var activeColor: Int = Color.WHITE

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

        initObservables()
    }

    fun setState(
        phaseState: PhaseTimerModel.Phase, thisPhase: PhaseTimerModel.Phase, isActive: Boolean) {
        val flashState: FlashState =
            when(isActive) {
                true -> {
                    when (phaseState) {
                        PhaseTimerModel.Phase.HUNT -> {
                            if (phaseState == thisPhase &&
                                investigationViewModel.sanityModel?.canFlashWarning == true) {
                                FlashState.ACTIVE_ANIMATED
                            } else
                                FlashState.ACTIVE_STEADY
                        }

                        PhaseTimerModel.Phase.SETUP, PhaseTimerModel.Phase.ACTION -> {
                            FlashState.ACTIVE_STEADY
                        }
                    }
                }
                false -> {
                    FlashState.OFF
                }
            }

        flashView?.setContent { FlashBackground(state = flashState) }

        when(flashState) {
            FlashState.OFF -> { labelView?.setTextColor(inactiveColor) }
            else -> { labelView?.setTextColor(activeColor) }
        }
    }

    abstract fun initObservables()

}
