package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R

class SanityCarouselView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_carousel, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SanityCarouselView)

            val title =
                a.getString(R.styleable.SanityCarouselView_SanityCarouselViewTitle)
            val name =
                a.getString(R.styleable.SanityCarouselView_SanityCarouselViewName)

            setTitle(title)
            setName(name)

            a.recycle()
        }

        setDefaults()
    }

    private fun setDefaults() {
    }

    fun setTitle(title: Int) {
        setTitle(context.getString(title))
    }

    fun setTitle(title: String?) {
        if(title.isNullOrEmpty()) return

        val titleView = findViewById<AppCompatTextView>(R.id.carousel_title)
        titleView?.text = title
    }

    fun setName(name: Int) {
        setName(context.getString(name))
    }

    fun setName(name: String?) {
        if(name.isNullOrEmpty()) return

        val nameView = findViewById<AppCompatTextView>(R.id.carousel_name)
        nameView?.text = name
    }

    fun setRightListener() {
    }

    fun setLeftListener() {
    }
}
