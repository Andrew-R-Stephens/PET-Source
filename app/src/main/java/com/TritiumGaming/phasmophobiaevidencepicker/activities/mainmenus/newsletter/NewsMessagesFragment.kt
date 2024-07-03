package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views.MessagesAdapterView
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton

class NewsMessagesFragment : MainMenuFragment() {

    var recyclerViewMessages: RecyclerView? = null

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_news_messageslist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleTextView = view.findViewById<AppCompatTextView>(R.id.textView_title)
        val backButton = view.findViewById<PETImageButton>(R.id.button_left)
        val markAsReadButton = view.findViewById<AppCompatButton>(R.id.markReadButton)
        recyclerViewMessages = view.findViewById(R.id.recyclerview_messageslist)

        // SCROLL BAR
        recyclerViewMessages?.scrollBarFadeDuration = -1

        val updateStateOfMarkAsReadButton: () -> Unit = {
            newsLetterViewModel?.currentInbox?.let { currentInbox ->
                markAsReadButton?.let{ button ->
                    val hasUnreadMessages = currentInbox.compareDates()
                    button.isEnabled = hasUnreadMessages
                    button.alpha = if(hasUnreadMessages) 1f else .4f
                    button.isClickable = hasUnreadMessages
                }
            }
        }

        newsLetterViewModel?.let { newsLetterViewModel ->
            // SET VIEW TEXT
            try {
                titleTextView.text = newsLetterViewModel.currentInboxType.getName(requireContext())
            } catch (e: IllegalStateException) { e.printStackTrace() }

            val inbox = newsLetterViewModel.getInbox(newsLetterViewModel.currentInboxType)
            inbox?.let {
                try { it.inboxType?.let {  inboxType ->
                    newsLetterViewModel.saveToFile(requireContext(), inboxType) } }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }

            updateStateOfMarkAsReadButton()
            Thread { requireActivity().runOnUiThread { populateAdapter(view) } }.start()
        }

        markAsReadButton.setOnClickListener {
            newsLetterViewModel?.currentInbox?.updateLastReadDate()
            updateStateOfMarkAsReadButton()
            Thread { requireActivity().runOnUiThread { populateAdapter(view) } }.start()
        }

        // LISTENERS
        backButton.setOnClickListener { v: View? ->
            v?.let { findNavController(v).popBackStack() } }
        super.initAdView(view.findViewById(R.id.adView))
    }

    private fun populateAdapter(view: View) {
        newsLetterViewModel?.currentInbox?.let { currentInbox ->
            val adapter = MessagesAdapterView(
                currentInbox, object: MessagesAdapterView.OnMessageListener {
                    override fun onNoteClick(position: Int) {
                        newsLetterViewModel?.let { newsletterViewModel ->
                            newsletterViewModel.currentMessageIndex = position
                            newsletterViewModel.currentMessage?.let { message ->
                                newsletterViewModel.currentInbox?.updateLastReadDate(message)
                                findNavController(view).navigate(
                                    R.id.action_inboxMessageListFragment_to_inboxMessageFragment)
                            }
                        }
                    }
                })
            recyclerViewMessages?.adapter = adapter
            try { recyclerViewMessages?.layoutManager = LinearLayoutManager(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        } ?: try {
            Log.d("MessageCenter", "Inbox does not exist! " +
                    newsLetterViewModel?.currentInboxType?.getName(requireContext()))
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    override fun initViewModels() {
        super.initViewModels()
        initMainMenuViewModel()
        initNewsletterViewModel()
    }
}