package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.toolbar.tools.carousel

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
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
                investigationViewModel.decrementMapIndex()
                Log.d("Carousel", "Decrementing Map")
                investigationViewModel.resetSanityHandler()
            }
        }

        rightListener = object : CarouselButtonListener() {
            override fun onAction() {
                investigationViewModel.incrementMapIndex()
                Log.d("Carousel", "Incrementing Map")
                investigationViewModel.resetSanityHandler()
            }
        }

        investigationViewModel.currentMapName.value.let { nameRes ->
            setName(context.getString(nameRes).split(" ")[0])
        }
    }

    override fun initObservables() {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel.currentMapIndex.collectLatest {
                setName(context.getString(investigationViewModel.currentMapName.value).split(" ")[0])
            }
        }
    }
}
