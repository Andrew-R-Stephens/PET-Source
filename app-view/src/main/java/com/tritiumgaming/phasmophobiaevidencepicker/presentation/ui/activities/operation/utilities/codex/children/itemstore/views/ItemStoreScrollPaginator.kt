package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.utilities.codex.children.itemstore.views

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

class ItemStoreScrollPaginator : GridLayout {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.layout_itemstore_scrollview_paginator, this)
    }
}
