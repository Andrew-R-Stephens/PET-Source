package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageListModel
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.NewsAlert

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
                R.styleable.NewsletterInboxView_inbox_icon, R.drawable.icon_logo_app)
            a.recycle()
        }
        title?.let { setTitle(it) }
        //icon?.let { this.icon = it }

        setBackgroundColor(resources.getColor(R.color.transparent))
        requestLayout()
    }

    fun setTitle(title: String?) {
        val titleView = findViewById<AppCompatTextView>(R.id.inboxTitle)
        titleView?.let {
            it.text = title
            it.isSelected = true
        }
    }

    fun notify(inboxModel: NewsletterMessageListModel?, @DrawableRes inboxIcon: Int) {
        val notifyView = findViewById<ComposeView>(R.id.notificationComposable)
        notifyView.setContent {
            NewsAlert(
                isActive = (inboxModel?.compareDates() ?: 0) > 0,
                inboxIcon
            )
        }
    }
}

