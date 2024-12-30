package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.newsletter.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.NewsAlert

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
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.NewsletterMessageView)
            title = a.getString(R.styleable.NewsletterMessageView_inboxMessage_title)
            a.recycle()
        }
        title?.let { setTitle(it) }

        requestLayout()
    }

    private fun setTitle(title: String?) {
        val titleView = findViewById<AppCompatTextView>(R.id.inboxTitle)

        titleView?.let {
            it.text = title
            it.isSelected = true
        }
    }

    fun notify(inboxModel: NewsletterInboxModel?) {
        val notifyView = findViewById<ComposeView>(R.id.notificationComposable)
        notifyView.setContent {
            NewsAlert(
                isActive = (inboxModel?.compareDate() ?: 0) > 0,
                baseDrawableId = null
            )
        }
    }
}

