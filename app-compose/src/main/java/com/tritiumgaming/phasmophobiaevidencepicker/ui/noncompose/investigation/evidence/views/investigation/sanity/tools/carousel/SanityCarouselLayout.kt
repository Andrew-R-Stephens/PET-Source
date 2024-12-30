package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.sanity.tools.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.views.global.PETImageButton

abstract class SanityCarouselLayout : ConstraintLayout {

    lateinit var investigationViewModel: InvestigationViewModel

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
        inflate(context, R.layout.layout_sanity_tool_carousel, this)

        leftButton = findViewById(R.id.carousel_prev)
        rightButton = findViewById(R.id.carousel_next)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SanityCarouselLayout)

            val title =
                a.getString(R.styleable.SanityCarouselLayout_SanityCarouselLayoutTitle)
            val name =
                a.getString(R.styleable.SanityCarouselLayout_SanityCarouselLayoutName)
            val image =
                a.getResourceId(R.styleable.SanityCarouselLayout_SanityCarouselLayoutImage, -1)

            setTitle(title)
            setName(name)
            setImage(image)

            a.recycle()
        }

        setDefaults()
    }

    private fun setDefaults() { }

    open fun init(investigationViewModel: InvestigationViewModel) {
        this.investigationViewModel = investigationViewModel

        setLeftListener()
        setRightListener()

        initObservables()
    }

    fun setTitle(title: Int) {
        setTitle(context.getString(title))
    }

    private fun setTitle(title: String?) {
        if(title.isNullOrEmpty()) return

        val titleView: AppCompatTextView? = findViewById(R.id.carousel_title)
        titleView?.text = title
    }

    private fun setImage(@DrawableRes drawableRes: Int) {
        if(drawableRes == -1) return

        val imageView: AppCompatImageView? = findViewById(R.id.carousel_image)
        imageView?.setImageResource(drawableRes)
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
