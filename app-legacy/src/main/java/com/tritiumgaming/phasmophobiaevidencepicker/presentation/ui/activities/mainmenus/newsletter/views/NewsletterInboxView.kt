package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource

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
                    R.styleable.NewsletterInboxView_inbox_icon,
                    com.tritiumgaming.core.resources.R.drawable.icon_logo_app
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


    fun notify(
        inboxModel: NewsletterInboxModel?,
        @DrawableRes inboxIcon: Int
    ) {
        val notifyView = findViewById<ComposeView>(R.id.notificationComposable)

        val isActive = inboxModel?.compareDates() ?: false

        notifyView.setContent {
            NotificationIndicator(
                isActive = isActive,
                baseComponent = @Composable { modifier ->
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(inboxIcon),
                        contentDescription = null
                    )
                },
                badgeComponent = @Composable { modifier ->
                    IconResource.NOTIFY.ToComposable(
                        modifier = modifier,
                        colors = IconVectorColors(
                            fillColor = Color(getColorFromAttribute(
                                context, R.attr.backgroundColor)),
                            strokeColor = Color(getColorFromAttribute(
                                context, R.attr.inboxNotification))
                        )
                    )
                },
            )
        }
    }

    /*fun notify(inboxModel: NewsletterInboxModel?, @DrawableRes inboxIcon: Int) {
        val notifyView = findViewById<ComposeView>(R.id.notificationComposable)
        notifyView.setContent {
            NewsAlert(
                isActive = inboxModel?.compareDates() ?: false,
                inboxIcon
            )
        }
    }*/
}

