package com.tritiumgaming.phasmophobiaevidencepicker.views.global

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

class NavHeaderLayout : ConstraintLayout {
    constructor(context: Context) :
            super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init(attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { init(attrs) }

    fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.layout_navigationheader, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NavHeaderLayout)

            val centerTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutCenterTitle, 0)
            val leftTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutLeftTitle, 0)
            val rightTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutRightTitle, 0)
            val buttonTypeLeft =
                a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonLeft, 0)
            val buttonTypeRight =
                a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonRight, 0)

            a.recycle()

            try { setCenterTitle(context.getString(centerTitleResId)) }
            catch (ex: NotFoundException) { ex.printStackTrace() }
            try { setLeftTitle(context.getString(leftTitleResId)) }
            catch (ex: NotFoundException) { ex.printStackTrace() }
            try { setRightTitle(context.getString(rightTitleResId)) }
            catch (ex: NotFoundException) { ex.printStackTrace() }

            setButtonLeft(buttonTypeLeft)
            setButtonRight(buttonTypeRight)
        }
    }

    private fun setButtonLeft(buttonType: Int) {
        val buttonLeft = findViewById<PETImageButton>(R.id.button_left)
        buttonLeft?.setImageLevel(buttonType)
    }

    private fun setButtonRight(buttonType: Int) {
        val buttonRight = findViewById<PETImageButton>(R.id.button_right)
        buttonRight?.setImageLevel(buttonType)
    }

    private fun setCenterTitle(title: String?) {
        if(title.isNullOrEmpty()) return
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_title)
        textViewTitle?.text = title
    }

    private fun setLeftTitle(title: String?) {
        if(title.isNullOrEmpty()) return
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_left)
        textViewTitle?.text = title
        textViewTitle?.visibility = VISIBLE
    }

    private fun setRightTitle(title: String?) {
        if(title.isNullOrEmpty()) return
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_right)
        textViewTitle?.text = title
        textViewTitle?.visibility = VISIBLE
    }
}
