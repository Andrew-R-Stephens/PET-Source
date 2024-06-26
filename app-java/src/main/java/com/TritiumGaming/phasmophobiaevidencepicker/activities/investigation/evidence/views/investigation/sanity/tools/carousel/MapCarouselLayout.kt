package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
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

    override fun init(investigationViewModel: InvestigationViewModel) {
        super.init(investigationViewModel)

        leftListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.mapCarouselModel?.decrementIndex()
                Log.d("Carousel", "Decrementing Map")
                investigationViewModel.sanityModel?.reset()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.mapCarouselModel?.incrementIndex()
                Log.d("Carousel", "Incrementing Map")
                investigationViewModel.sanityModel?.reset()
            }
        }

        setName(investigationViewModel.mapCarouselModel?.currentName)
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.mapCarouselModel?.currentIndex?.collectLatest {
                setName(
                    investigationViewModel.mapCarouselModel?.currentName
                        ?.split(" ")?.get(0)
                )
            }
        }
    }
}
