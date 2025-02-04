package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.investigation.utilities.codex.children.itemstore.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

@Deprecated("Migrate to Jetpack Compose")
class ItemStoreList : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.layout_itemstore, this)
    }

}
