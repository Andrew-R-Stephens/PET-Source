package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
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

    override fun init(evidenceViewModel: EvidenceViewModel) {
        super.init(evidenceViewModel)

        leftListener = object : CarouselButtonListener() {
            override fun onAction() {
                evidenceViewModel.difficultyCarouselModel?.decrementIndex()
                print("Decrementing Diff")
                evidenceViewModel.sanityModel?.reset()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                evidenceViewModel.difficultyCarouselModel?.incrementIndex()
                print("Incrementing Diff")
                evidenceViewModel.sanityModel?.reset()
            }
        }

        setName(evidenceViewModel.difficultyCarouselModel?.currentName)
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.difficultyCarouselModel?.currentIndex?.collectLatest {
                setName(
                    evidenceViewModel.difficultyCarouselModel?.currentName
                        ?.split(" ")?.get(0)
                )
            }
        }
    }
}
