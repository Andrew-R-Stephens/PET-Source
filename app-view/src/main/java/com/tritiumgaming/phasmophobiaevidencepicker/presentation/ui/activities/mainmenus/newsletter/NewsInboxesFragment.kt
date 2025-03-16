package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter.views.NewsletterInboxView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton

class NewsInboxesFragment : MainMenuFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_news_inboxes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val backButton = view.findViewById<PETImageButton>(R.id.button_left)
        val inboxList = view.findViewById<ViewGroup>(R.id.list_inboxes)

        newsLetterViewModel.let { newsLetterViewModel ->

            for (inboxType in InboxType.entries.toTypedArray()) {

                try {

                    val inboxView = NewsletterInboxView(requireContext(), null)
                    inboxView.setTitle(getString(inboxType.title))
                    inboxView.notify(
                        newsLetterViewModel.getInbox(inboxType),
                        inboxType.icon
                    )

                    inboxView.setOnClickListener { v: View ->
                        this.gotoInbox(v, inboxType)
                    }

                    if (inboxList is GridLayout) {
                        inboxView.layoutParams = GridLayout.LayoutParams(
                            GridLayout.spec(GridLayout.UNDEFINED, 1f),
                            GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        )
                    }
                    inboxList.addView(inboxView)

                } catch (e: IllegalStateException) { e.printStackTrace() }
            }
        }

        backButton.setOnClickListener { v: View? ->
            v?.let { findNavController(v).popBackStack() }
        }

        super.initAdView(view.findViewById(R.id.adView))
    }

    private fun gotoInbox(v: View, inboxType: InboxType) {

        Log.d("NewsInboxesFragment", "Going to inbox: ${inboxType.id} ${inboxType.name}")
        val bundle = Bundle()
        bundle.putInt("inboxType", inboxType.id)

        findNavController(v).navigate(R.id.action_inboxFragment_to_inboxMessageListFragment, bundle)
    }

    override fun onPause() {
        saveStates()
        super.onPause()
    }
}