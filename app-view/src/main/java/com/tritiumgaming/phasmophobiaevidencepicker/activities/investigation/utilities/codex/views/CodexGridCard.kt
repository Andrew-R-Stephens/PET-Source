package com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.google.android.material.card.MaterialCardView

class CodexGridCard : MaterialCardView {

    constructor(context: Context) : super(context) { initView(context) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(context, attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(context, attrs) }

    fun initView(context: Context?, attrs: AttributeSet?) {
        initView(context)

        val a = getContext().obtainStyledAttributes(attrs, R.styleable.CodexGridCard)
        val text = a.getString(R.styleable.CodexGridCard_label_text)
        val img = a.getDrawable(R.styleable.CodexGridCard_background_image)
        a.recycle()

        setText(text)
        setBackgroundImage(img)
    }

    fun initView(context: Context?) {
        inflate(context, R.layout.item_codexmenu_option, this)
    }

    fun setText(text: String?) {
        val textView = findViewById<AppCompatTextView>(R.id.label_itemName)
        textView.text = text
    }

    private fun setBackgroundImage(imageRes: Drawable?) {
        val imgView = findViewById<AppCompatImageView>(R.id.image_item)
        imgView.setImageDrawable(imageRes)
    }
}