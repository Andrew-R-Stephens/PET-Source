package com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.newsletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.newsletter.views.NewsletterInboxView
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.views.global.PETImageButton

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
                    inboxView.setTitle(inboxType.getName(requireContext()))
                    inboxView.notify(
                        newsLetterViewModel.getInbox(inboxType),
                        inboxType.getIcon(requireContext()))

                    when (inboxType) {
                        InboxType.GENERAL -> inboxView.setOnClickListener {
                            v: View -> this.gotoGeneralNews(v) }

                        InboxType.PET -> inboxView.setOnClickListener {
                            v: View -> this.gotoPetNews(v) }

                        InboxType.PHASMOPHOBIA -> inboxView.setOnClickListener {
                            v: View -> this.gotoPhasNews(v) }
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

    private fun navigateToInboxFragment(v: View) {
        try {
            findNavController(v).navigate(R.id.action_inboxFragment_to_inboxMessageListFragment)
        } catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    /** showExtraNewsPopup method */
    private fun gotoGeneralNews(v: View) {
        try {
            newsLetterViewModel.currentInboxType = InboxType.GENERAL
            navigateToInboxFragment(v)
        } catch (e: NullPointerException) { e.printStackTrace() }
    }

    /** showPetNewsPopup method */
    private fun gotoPetNews(v: View) {
        try {
            newsLetterViewModel.currentInboxType = InboxType.PET
            navigateToInboxFragment(v)
        } catch (e: NullPointerException) { e.printStackTrace() }
    }

    /** showPhasNewsPopup method */
    private fun gotoPhasNews(v: View) {
        try {
            newsLetterViewModel.currentInboxType = InboxType.PHASMOPHOBIA
            navigateToInboxFragment(v)
        } catch (e: NullPointerException) { e.printStackTrace() }
    }

    /*override fun onPause() {
        saveStates()
        super.onPause()
    }*/
}