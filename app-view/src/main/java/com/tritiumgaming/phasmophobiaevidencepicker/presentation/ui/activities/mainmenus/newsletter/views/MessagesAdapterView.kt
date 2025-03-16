package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterMessage
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.NewsAlert

class MessagesAdapterView(
    private val currentInbox: NewsletterInbox?,
    private val onMessageListener: OnMessageListener)
    : RecyclerView.Adapter<MessagesAdapterView.ViewHolder>() {

    private val messages: List<Pair<String, NewsletterMessage>> =
        currentInbox?.messages?.toList() ?: listOf()

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
        val message = messages[position].second

        val textView = holder.messageTitleTextView
        textView.text = message.title
        textView.isSelected = true
        val icon = holder.icon
        currentInbox?.compareDate(message.date)?.let {
            if(it > 0) {
                icon?.setContent {
                    NewsAlert(baseDrawableId = null)
                }
            } else { icon?.visibility = GONE }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    interface OnMessageListener {
        fun onNoteClick(position: Int)
    }

}