package com.tritiumstudios.phasmophobiaevidencetool.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumstudios.phasmophobiaevidencetool.R

class NavHeaderLayout : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun init(c: Context, attrs: AttributeSet?) {
        inflate(c, R.layout.layout_navigationheader, this)
        if (attrs != null) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.NavHeaderLayout)
            val centerTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutCenterTitle, 0)
            val leftTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutLeftTitle, 0)
            val rightTitleResId =
                a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutRightTitle, 0)
            val buttonTypeLeft = a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonLeft, 0)
            val buttonTypeRight =
                a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonRight, 0)
            a.recycle()
            try {
                setCenterTitle(c.getString(centerTitleResId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setLeftTitle(c.getString(leftTitleResId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                setRightTitle(c.getString(rightTitleResId))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setButtonLeft(buttonTypeLeft)
            setButtonRight(buttonTypeRight)
        }
    }

    private fun setButtonLeft(buttonType: Int) {
        val button = findViewById<PETImageButton>(R.id.button_left)

        button?.setImageLevel(buttonType)
    }

    private fun setButtonRight(buttonType: Int) {
        val buttonRight = findViewById<PETImageButton>(R.id.button_right)

        buttonRight?.setImageLevel(buttonType)
    }

    fun setCenterTitle(title: String?) {
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_title)

        textViewTitle?.text = title
    }

    fun setLeftTitle(title: String?) {
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_left)

        textViewTitle?.text = title
        textViewTitle?.visibility = VISIBLE
    }

    fun setRightTitle(title: String?) {
        val textViewTitle = findViewById<AppCompatTextView>(R.id.textView_right)

        textViewTitle?.text = title
        textViewTitle?.visibility = VISIBLE
    }
}
