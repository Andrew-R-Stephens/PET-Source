package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.viewmodel.InvestigationViewModel
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
                investigationViewModel.investigationModel?.ghostScoreModel?.updateOrder()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.difficultyCarouselModel?.incrementIndex()
                print("Incrementing Diff")
                investigationViewModel.sanityModel?.reset()
                investigationViewModel.investigationModel?.ghostScoreModel?.updateOrder()
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
