package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.InvestigationActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.common.AccountIconView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.common.NewsAlert
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.StartScreenAnimationView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.review.ReviewLauncher
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.review.ReviewPopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.DropdownClickPair
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.DropdownNavigationPair
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.IconDropdownMenu
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose.LanguageIcon
import java.util.Locale

class StartScreenFragment : MainMenuFragment() {
    private var animationView: StartScreenAnimationView? = null

    private var canLoadNewsletter = true
    private var newsletterThread: Thread? = null

    private var buttonMsgInbox: ComposeView? = null
    private var newsIcon: ComposeView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()

        return inflater.inflate(R.layout.fragment_startscreen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // INITIALIZE VIEWS
        animationView = view.findViewById(R.id.backgroundanimationView)

        val labelLanguageName = view.findViewById<AppCompatTextView>(R.id.label_languageName)
        val buttonStart = view.findViewById<View>(R.id.button_start_solo)
        val buttonInfo = view.findViewById<ComposeView>(R.id.button_info)
        val buttonMenu = view.findViewById<ComposeView>(R.id.button_settings)
        val languageIcon = view.findViewById<ComposeView>(R.id.icon_language)
        val buttonReview = view.findViewById<AppCompatImageView>(R.id.button_review)
        buttonMsgInbox = view.findViewById(R.id.button_inbox)
        val buttonLanguage = view.findViewById<View>(R.id.listener_language)

        // LISTENERS
        buttonLanguage.setOnClickListener { v: View -> this.gotoLanguagesFragment(v) }
        buttonMsgInbox?.setOnClickListener { v: View -> this.gotoMessageCenterFragment(v) }
        buttonStart.setOnClickListener {
            gotoInvestigationActivity()
        }

        newsIcon = ComposeView(requireContext())
        newsIcon?.setContent { NewsAlert() }

        languageIcon?.setContent { LanguageIcon() }

        buttonMenu.setContent {
            val translationIcon: @Composable () -> Unit = { LanguageIcon() }

            IconDropdownMenu(
                R.drawable.ic_menu,
                R.navigation.titlescreen_navgraph,
                arrayOf(
                    DropdownNavigationPair(R.drawable.ic_info, R.id.appInfoFragment),
                    DropdownNavigationPair(R.drawable.ic_gear, R.id.appSettingsFragment),
                    DropdownNavigationPair(translationIcon, R.id.appLanguageFragment),
                    DropdownClickPair(R.drawable.ic_discord) {
                        gotoDiscordApplication()
                    }
                )
            ) { false }

        }

        buttonInfo.setContent {

            IconDropdownMenu(
                AccountIconView(requireContext()),
                R.navigation.titlescreen_navgraph,
                arrayOf(
                    DropdownNavigationPair(R.drawable.ic_person, R.id.accountOverviewFragment),
                        DropdownNavigationPair(R.drawable.ic_store, R.id.marketplaceFragment)
                )
            )

        }

        buttonMsgInbox?.setContent { NewsAlert() }

        setLanguageName(labelLanguageName)

        super.initAdView(view.findViewById(R.id.adView))

        try {
            if (!(requireActivity() as MainMenuActivity).checkForAppUpdate(requireActivity())) {
                initReviewRequest(buttonReview) } }
        catch (e: IllegalStateException) { e.printStackTrace() }
        catch (e: SendIntentException) { e.printStackTrace() }

        try { doReviewRequest() }
        catch (e: SendIntentException) { e.printStackTrace() }
    }

    override fun backPressedHandler() {
        closePopup()
    }

    private fun setLanguageName(labelLanguageName: AppCompatTextView) {
        var chosenLanguage = Locale.getDefault().displayLanguage
        if (chosenLanguage.isNotEmpty()) {
            chosenLanguage = chosenLanguage.substring(0, 1).uppercase(Locale.getDefault()) +
                    chosenLanguage.substring(1)
        }
        labelLanguageName.text = chosenLanguage
    }

    private fun gotoDiscordApplication() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                "https://discord.gg/ ${getString(R.string.aboutinfo_discordInvite)}"
                    .toUri()
            )
            startActivity(intent)
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun gotoInvestigationActivity() {

        try {
            val intent = Intent(requireActivity(), InvestigationActivity::class.java)
            intent.putExtra("lobby", 0)
            intent.putExtra("language", globalPreferencesViewModel.currentLanguageCode.value)
            startActivity(intent)
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun gotoMessageCenterFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment)
    }

    private fun gotoLanguagesFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment)
    }

    private fun initReviewRequest(buttonReview: AppCompatImageView) {
        val disableButton = {
            buttonReview.isEnabled = false
            buttonReview.visibility = View.INVISIBLE
        }

        // REQUEST REVIEW LISTENER
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            if (globalPreferencesViewModel.canShowReviewButton) {
                buttonReview.setOnClickListener {
                    try {
                        showReviewPopup(requireView())
                    }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }
            } else { disableButton() }
        }
    }

    @Throws(SendIntentException::class)
    fun doReviewRequest() {
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            if (globalPreferencesViewModel.canRequestReview) {
                Log.d("Review", "Review Request Accepted")
                Thread {
                    try { Thread.sleep(1000L) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                    globalPreferencesViewModel.setWasRequested(true)
                    try {
                        requireActivity().runOnUiThread {
                            try {
                                showReviewPopup(requireView())
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }
                        }
                        globalPreferencesViewModel.saveToFile(requireContext())
                    }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }.start()
            } else {
                Log.d("Review", "Review Request Denied")
            }
        }
    }

    private fun showReviewPopup(parentView: View) {
        //DESTROY PREVIOUS POPUP
        popupWindow?.dismiss()

        popupWindow = ReviewPopupWindow( parentView,
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        (popupWindow as ReviewPopupWindow).reviewPopupWindowListener =
            object: ReviewPopupWindow.ReviewPopupListener() {
                override fun onCreate() {
                    Log.d("ReviewRequest","SUCCESSFUL")

                    val params = Bundle()
                    params.putString("event_type", "review_requested")
                    params.putString("event_details", "request_ successful")
                    analytics?.logEvent("event_review_manager", params)
                }
                override fun onDestroy() {
                    popupWindow?.dismiss()
                    popupWindow = null
                }
                override fun onAccept() {
                    ReviewLauncher.launch(requireActivity(), analytics)
                    this.onDestroy()
                }
                override fun onDecline() {
                    val params = Bundle()
                    params.putString("event_type", "user_action")
                    params.putString("event_details", "request_rejected")
                    analytics?.logEvent("event_review_manager", params)
                    this.onDestroy()
                }
            }
    }

    /** onPause method */
    override fun onPause() {
        // SAVE PERSISTENT DATA
        saveGlobalPreferencesViewModel()

        // STOP THREADS
        animationView?.let { animationView ->
            animationView.stopAnimation()
            animationView.canAnimate = false
        }

        super.onPause()
    }

    /** onResume method */
    override fun onResume() {
        // START THREADS
        animationView?.init(mainMenuViewModel)

        super.onResume()
    }

    /** onDestroy method */
    override fun onDestroy() {
        // DESTROY AD-REQUEST
        mainMenuViewModel.adRequest = null

        super.onDestroy()
    }

}