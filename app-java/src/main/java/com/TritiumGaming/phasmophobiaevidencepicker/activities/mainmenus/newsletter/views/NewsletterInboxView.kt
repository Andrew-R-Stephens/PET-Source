package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageListModel

class NewsletterInboxView : ConstraintLayout {
    constructor(context: Context) : super(context) { initView(context, null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(context, attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(context, attrs) }

    fun initView(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.item_newsletter_inbox, this)

        var title: String? = ""
        @DrawableRes var icon: Int? = null
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NewsletterInboxView)
            title = a.getString(R.styleable.NewsletterInboxView_inbox_title)
            icon = a.getResourceId(
                R.styleable.NewsletterInboxView_inbox_icon, R.drawable.app_icon_sm)
            a.recycle()
        }
        title?.let { setInboxTitle(it) }
        icon?.let { setInboxIcon(it) }

        setBackgroundColor(resources.getColor(R.color.transparent))
        requestLayout()
    }

    fun setInboxIcon(icon: Int) {
        val iconView = findViewById<AppCompatImageView>(R.id.inboxIcon)
        iconView?.setImageResource(icon)
    }

    fun setInboxTitle(title: String?) {
        val titleView = findViewById<AppCompatTextView>(R.id.inboxTitle)
        titleView?.let {
            it.text = title
            it.isSelected = true
        }
    }

    fun initNotify(context: Context, inboxData: NewsletterMessageListModel?) {
        val notifyView = findViewById<AppCompatImageView>(R.id.notifyIcon)
        val animation = AnimationUtils.loadAnimation(context, R.anim.notifyblink)
        if (inboxData?.compareDates() == true) {
            notifyView.alpha = 0.9f
            notifyView.startAnimation(animation)
        }
    }
}

