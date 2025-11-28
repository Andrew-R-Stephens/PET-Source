package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterMessageModel
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource

class MessagesAdapterView(
    private val currentInbox: NewsletterInboxModel,
    private val colors: IconVectorColors,
    private val onMessageListener: OnMessageListener,
) : RecyclerView.Adapter<MessagesAdapterView.ViewHolder>() {

    private val messages: ArrayList<NewsletterMessageModel> = currentInbox.messages

    class ViewHolder(view: View, onMessageListener: OnMessageListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        val messageTitleTextView: AppCompatTextView =
            itemView.findViewById(R.id.textView_messageName)
        val icon: ComposeView? = itemView.findViewById(R.id.notificationComposable)

        private val onMessageListener: OnMessageListener

        init {
            view.setOnClickListener(this)
            this.onMessageListener = onMessageListener
        }

        override fun onClick(v: View) {
            onMessageListener.onNoteClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val messageView = inflater.inflate(
            R.layout.item_newsletter_inbox_message, parent, false)
        return ViewHolder(messageView, this.onMessageListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val textView = holder.messageTitleTextView
        textView.text = messages[position].title
        textView.isSelected = true
        val icon = holder.icon
        if(currentInbox.compareDate(messages[position].date) > 0) {
            icon?.setContent {
                NotificationIndicator(
                    isActive = true,
                    badgeComponent = @Composable { modifier ->
                        IconResource.NOTIFY.ToComposable(
                            modifier = modifier,
                            colors = colors
                        )
                    },
                )
            }
        } else { icon?.visibility = GONE }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    interface OnMessageListener {
        fun onNoteClick(position: Int)
    }

}