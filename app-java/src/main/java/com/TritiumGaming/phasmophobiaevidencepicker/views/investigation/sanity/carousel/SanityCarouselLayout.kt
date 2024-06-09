package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton

abstract class SanityCarouselLayout : ConstraintLayout {

    lateinit var evidenceViewModel: EvidenceViewModel

    private lateinit var leftButton: PETImageButton
    private lateinit var rightButton: PETImageButton

    constructor(context: Context) :
            super(context) { initView(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView(attrs) }

    fun initView(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_sanity_carousel, this)

        leftButton = findViewById(R.id.carousel_prev)
        rightButton = findViewById(R.id.carousel_next)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SanityCarouselLayout)

            val title =
                a.getString(R.styleable.SanityCarouselLayout_SanityCarouselLayoutTitle)
            val name =
                a.getString(R.styleable.SanityCarouselLayout_SanityCarouselLayoutName)

            setTitle(title)
            setName(name)

            a.recycle()
        }

        setDefaults()
    }

    private fun setDefaults() {
    }

    open fun init(evidenceViewModel: EvidenceViewModel) {
        this.evidenceViewModel = evidenceViewModel

        setLeftListener()
        setRightListener()

        initObservables()
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

    abstract fun initObservables()

    abstract class CarouselButtonListener {
        abstract fun onAction()
    }

    var rightListener: CarouselButtonListener? = null
    private fun setRightListener() {
        rightButton.setOnClickListener { rightListener?.onAction() }
    }

    var leftListener: CarouselButtonListener? = null
    private fun setLeftListener() {
        leftButton.setOnClickListener { leftListener?.onAction() }
    }

}
