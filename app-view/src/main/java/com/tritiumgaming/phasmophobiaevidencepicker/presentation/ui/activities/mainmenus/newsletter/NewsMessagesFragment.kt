package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views.MessagesAdapterView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsMessagesFragment : MainMenuFragment() {

    var recyclerViewMessages: RecyclerView? = null

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_news_messageslist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val inboxType: InboxType? =
            arguments?.getInt("inboxType")?.let {
                NewsletterRepository.NewsletterInboxType.getInbox(it)
            }
        val inbox = inboxType?.let { newsLetterViewModel.getInbox(it) }
        val messages = inbox?.let { inbox.messages.toList() }

        val titleTextView = view.findViewById<AppCompatTextView>(R.id.textView_title)
        val backButton = view.findViewById<PETImageButton>(R.id.button_left)
        val markAsReadButton = view.findViewById<AppCompatButton>(R.id.markReadButton)
        recyclerViewMessages = view.findViewById(R.id.recyclerview_messageslist)

        // SCROLL BAR
        recyclerViewMessages?.scrollBarFadeDuration = -1

        val updateStateOfMarkAsReadButton: () -> Unit = {

            inbox?.let { currentInbox ->

                markAsReadButton?.let{ button ->
                    val hasUnreadMessages = currentInbox.compareDates()
                    button.isEnabled = hasUnreadMessages
                    button.alpha = if(hasUnreadMessages) 1f else .4f
                    button.isClickable = hasUnreadMessages
                }
            }

        }

        // SET VIEW TEXT
        try { inboxType?.let { titleTextView.text = getString(it.title) } }
        catch (e: IllegalStateException) { e.printStackTrace() }

        updateStateOfMarkAsReadButton()
        Thread { requireActivity().runOnUiThread {
            populateAdapter(view, inboxType)
        } }.start()

        markAsReadButton.setOnClickListener {
            inboxType?.let { inboxType ->
                messages?.firstOrNull()?.second?.date?.let {
                    newsLetterViewModel.setLastReadDate(inboxType, it)
                }
            }
        }

        // LISTENERS
        backButton.setOnClickListener { v: View? ->
            v?.let { findNavController(v).popBackStack() } }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    inbox?.lastReadDate?.collect {
                        Thread { requireActivity().runOnUiThread {
                            populateAdapter(view, inboxType)

                            updateStateOfMarkAsReadButton()
                        } }.start()
                    }
                }
            }
        }

        super.initAdView(view.findViewById(R.id.adView))
    }

    private fun populateAdapter(view: View, inboxType: InboxType?) {
        inboxType?.let { inboxType ->
            val adapter = MessagesAdapterView(
                newsLetterViewModel.getInbox(inboxType),
                object: MessagesAdapterView.OnMessageListener {
                    override fun onNoteClick(position: Int) {
                        newsLetterViewModel.getInbox(inboxType)?.let { inbox ->
                            val message = inbox.messages.toList()[position]
                            gotoMessage(view, inboxType, message.second.id)
                        }

                    }
                }
            )

            recyclerViewMessages?.adapter = adapter
            try { recyclerViewMessages?.layoutManager = LinearLayoutManager(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }

        }

    }

    private fun gotoMessage(v: View, inboxType: InboxType, messageID: String) {

        val bundle = Bundle()
        bundle.putInt("inboxType", inboxType.id)
        bundle.putString("messageID", messageID)

        findNavController(v).navigate(
            R.id.action_inboxMessageListFragment_to_inboxMessageFragment, bundle)
    }

}