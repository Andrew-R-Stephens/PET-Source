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
                investigationViewModel.difficultyCarouselModel?.decrementIndex()
                print("Decrementing Diff")
                investigationViewModel.sanityModel?.reset()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.difficultyCarouselModel?.incrementIndex()
                print("Incrementing Diff")
                investigationViewModel.sanityModel?.reset()
            }
        }
        investigationViewModel.difficultyCarouselModel?.currentName?.let { nameRes ->
            setName(context.getString(nameRes))
        }
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.difficultyCarouselModel?.currentIndex?.collectLatest {
                investigationViewModel.difficultyCarouselModel?.currentName?.let { nameRes ->
                    setName(context.getString(nameRes))
                }
            }
        }
    }
}
