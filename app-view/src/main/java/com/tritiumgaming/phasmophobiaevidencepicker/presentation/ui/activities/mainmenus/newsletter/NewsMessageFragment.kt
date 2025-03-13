package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.newsletter

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton

class NewsMessageFragment : MainMenuFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
    View? {
        super.init()

        return inflater.inflate(R.layout.fragment_news_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonBack = view.findViewById<PETImageButton>(R.id.button_left)
        // INITIALIZE VIEWS
        val labelTitle = view.findViewById<AppCompatTextView>(R.id.textview_messageTitle)
        val labelDate = view.findViewById<AppCompatTextView>(R.id.textView_messageDate)
        val labelContent = view.findViewById<AppCompatTextView>(R.id.textView_messageContent)

        // LISTENERS
        buttonBack.setOnClickListener { v: View? ->
            v?.let { findNavController(v).popBackStack() }
        }

        // SET CONTENT
        val message = newsLetterViewModel.currentMessage
        message?.let {
            labelTitle.text = Html.fromHtml(message.title)
            labelDate.text = Html.fromHtml(message.getDateFormatted())
            labelContent.text = Html.fromHtml(message.description)
        } ?: {
            labelTitle.text = Html.fromHtml("Data unavailable")
            labelDate.text = Html.fromHtml("Data unavailable")
            labelContent.text = Html.fromHtml("Data unavailable")
        }

        super.initAdView(view.findViewById(R.id.adView))
    }

    override fun initViewModels() {
        super.initViewModels()
        initNewsletterViewModel()
    }
}