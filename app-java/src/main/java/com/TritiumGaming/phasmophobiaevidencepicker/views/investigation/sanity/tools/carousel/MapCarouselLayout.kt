package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapCarouselLayout : SanityCarouselLayout {

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
                evidenceViewModel.mapCarouselData?.decrementIndex()
                Log.d("Carousel", "Decrementing Map")
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                evidenceViewModel.mapCarouselData?.incrementIndex()
                Log.d("Carousel", "Incrementing Map")
            }
        }

        setName(evidenceViewModel.mapCarouselData?.mapCurrentName?.value)
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            evidenceViewModel.mapCarouselData?.mapCurrentName?.collectLatest {
                setName(it.split(" ")[0])
            }
        }
    }
}
