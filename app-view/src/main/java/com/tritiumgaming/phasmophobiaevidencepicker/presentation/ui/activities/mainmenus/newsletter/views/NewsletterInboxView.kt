package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.NewsAlert
import androidx.core.content.withStyledAttributes
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox

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
            context.withStyledAttributes(attrs, R.styleable.NewsletterInboxView) {
                title = getString(R.styleable.NewsletterInboxView_inbox_title)
                icon = getResourceId(
                    R.styleable.NewsletterInboxView_inbox_icon, R.drawable.icon_logo_app
                )
            }
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

    fun notify(inboxModel: NewsletterInbox?, @DrawableRes inboxIcon: Int) {
        val notifyView = findViewById<ComposeView>(R.id.notificationComposable)
        notifyView.setContent {
            NewsAlert(
                inboxType = inboxModel?.inboxType?.inboxType,
                /*isActive = inboxModel?.compareDates() == true,*/
                baseDrawableId = inboxIcon
            )
        }
    }
}

