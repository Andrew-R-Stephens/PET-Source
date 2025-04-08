package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DifficultyCarouselLayout : SanityCarouselLayout {

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    override fun init(investigationViewModel: InvestigationViewModel) {
        super.init(investigationViewModel)

        leftListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.decrementDifficultyIndex()
                print("Decrementing Diff")
                investigationViewModel.resetSanityHandler()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.incrementDifficultyIndex()
                print("Incrementing Diff")
                investigationViewModel.resetSanityHandler()
            }
        }
        setName(context.getString(investigationViewModel.currentDifficultyName))
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.currentDifficultyIndex.collectLatest {
                setName(context.getString(investigationViewModel.currentDifficultyName))
            }
        }
    }
}
