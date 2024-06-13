package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.carousel

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
                evidenceViewModel.difficultyCarouselData?.decrementIndex()
                print("Decrementing Diff")
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                evidenceViewModel.difficultyCarouselData?.incrementIndex()
                print("Incrementing Diff")
            }
        }

        setName(evidenceViewModel.difficultyCarouselData?.currentName)
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.difficultyCarouselData?.currentIndex?.collectLatest {
                setName(
                    evidenceViewModel.difficultyCarouselData?.currentName
                        ?.split(" ")?.get(0)
                )
            }
        }
    }
}
