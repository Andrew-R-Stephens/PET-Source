package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views.MessagesAdapterView
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton

class NewsMessagesFragment : MainMenuFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_news_messageslist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleTextView = view.findViewById<AppCompatTextView>(R.id.textView_title)
        val backButton = view.findViewById<PETImageButton>(R.id.button_left)
        val recyclerViewMessages = view.findViewById<RecyclerView>(R.id.recyclerview_messageslist)

        // SCROLL BAR
        recyclerViewMessages.scrollBarFadeDuration = -1

        newsLetterViewModel?.let { newsLetterViewModel ->
            // SET VIEW TEXT
            try {
                titleTextView.text = newsLetterViewModel.currentInboxType.getName(requireContext())
            } catch (e: IllegalStateException) { e.printStackTrace() }

            val inbox = newsLetterViewModel.getInbox(newsLetterViewModel.currentInboxType)
            inbox?.let {
                it.updateLastReadDate()
                try { it.inboxType?.let {  inboxType ->
                    newsLetterViewModel.saveToFile(requireContext(), inboxType) } }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }

            // SET CONTENT
            newsLetterViewModel.currentInbox?.let { currentInbox ->
                val adapter = MessagesAdapterView(
                    currentInbox.messages, object: MessagesAdapterView.OnMessageListener {
                    override fun onNoteClick(position: Int) {
                        newsLetterViewModel.setCurrentMessageId(position)
                        findNavController(view).navigate(
                            R.id.action_inboxMessageListFragment_to_inboxMessageFragment)
                    }
                })
                recyclerViewMessages.adapter = adapter
                try { recyclerViewMessages.layoutManager = LinearLayoutManager(requireContext()) }
                catch (e: IllegalStateException) { e.printStackTrace() }
            } ?: try { Log.d("InboxMessageListFrag", "Inbox does not exist! " +
                    newsLetterViewModel.currentInboxType.getName(requireContext()))
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }

        // LISTENERS
        backButton.setOnClickListener { v: View? ->
            v?.let { findNavController(v).popBackStack() } }
        super.initAdView(view.findViewById(R.id.adView))
    }


    override fun initViewModels() {
        super.initViewModels()
        initMainMenuViewModel()
        initNewsletterViewModel()
    }
}