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

class NewsletterMessageView : ConstraintLayout {

    constructor(context: Context) :
            super(context) { initView(context, null) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView(context, attrs) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(context, attrs) }

    fun initView(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.item_newsletter_inbox, this)

        var title: String? = null
        @DrawableRes var icon: Int? = null
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NewsletterMessageView)
            title = a.getString(R.styleable.NewsletterMessageView_inboxMessage_title)
            icon = a.getResourceId(R.styleable.NewsletterMessageView_inboxMessage_icon,
                    R.drawable.app_icon_sm)
            a.recycle()
        }

        title?.let { setInboxTitle(it) }
        icon?.let { setInboxIcon(it) }

        requestLayout()
    }

    private fun setInboxIcon(icon: Int) {
        val iconView = findViewById<AppCompatImageView>(R.id.inboxIcon)

        iconView?.setImageResource(icon)
    }

    private fun setInboxTitle(title: String?) {
        val titleView = findViewById<AppCompatTextView>(R.id.inboxTitle)

        titleView?.let {
            it.text = title
            it.isSelected = true
        }
    }

    fun initNotify(context: Context, inboxData: NewsletterMessageListModel?) {
        val notifyView = findViewById<AppCompatImageView>(R.id.notifyIcon)

        val animation = AnimationUtils.loadAnimation(context, R.anim.notifyblink)
        if (inboxData != null && inboxData.compareDates()) {
            notifyView.alpha = 0.9f
            notifyView.startAnimation(animation)
        }
    }
}

