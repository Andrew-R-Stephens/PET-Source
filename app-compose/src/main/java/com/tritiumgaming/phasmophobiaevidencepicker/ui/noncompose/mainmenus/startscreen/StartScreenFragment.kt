package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.startscreen

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.net.Uri
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.InvestigationActivity
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.MainMenuActivity
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.startscreen.views.StartScreenAnimationView
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.startscreen.views.review.ReviewLauncher
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.startscreen.views.review.ReviewPopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.views.account.AccountIconView
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.DropdownClickPair
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.DropdownNavigationPair
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.IconDropdownMenu
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.LanguageIcon
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.NewsAlert
import java.util.Locale

class StartScreenFragment : MainMenuFragment() {

    private var animationView: StartScreenAnimationView? = null

    private var canLoadNewsletter = true
    private var newsletterThread: Thread? = null

    private var buttonMsgInbox: ComposeView? = null
    //private var newsIcon: ComposeView? = null

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
            try { val intent = Intent(requireActivity(), InvestigationActivity::class.java)
                intent.putExtra("lobby", 0)
                startActivity(intent)
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }

        //newsIcon = ComposeView(requireContext())
        //newsIcon?.setContent { NewsAlert(false) }

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
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW, Uri.parse(
                                    "https://discord.gg/ ${getString(R.string.aboutinfo_discordInvite)}"
                                )
                            )
                        )
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

        buttonMsgInbox?.setContent { NewsAlert(false) }

        //setBackgroundLogo(iconApp)
        setLanguageName(labelLanguageName)

        super.initAdView(view.findViewById(R.id.adView))

        try { if (!(requireActivity() as MainMenuActivity).checkForAppUpdate()) {
                initReviewRequest(buttonReview) } }
        catch (e: IllegalStateException) { e.printStackTrace() }
        catch (e: SendIntentException) { e.printStackTrace() }

        try { doReviewRequest() }
        catch (e: SendIntentException) { e.printStackTrace() }
    }

    override fun backPressedHandler() {
        closePopup()
    }

    /*
    private fun loadMessageCenter() {
        try { newsLetterViewModel.registerInboxes(requireContext()) }
        catch (e: IllegalStateException) {
            Log.d("MessageCenter", "Failed registering inboxes")
            e.printStackTrace() }
    }
    */

    private fun setLanguageName(labelLanguageName: AppCompatTextView) {
        var chosenLanguage = Locale.getDefault().displayLanguage
        if (chosenLanguage.isNotEmpty()) {
            chosenLanguage = chosenLanguage.substring(0, 1).uppercase(Locale.getDefault()) +
                    chosenLanguage.substring(1)
        }
        labelLanguageName.text = chosenLanguage
    }

    private fun loadBannerAd(view: View) {
        try { MobileAds.initialize(requireActivity()) { } }
        catch (e: IllegalStateException) { e.printStackTrace() }

        val mAdView = view.findViewById<AdView>(R.id.adView)

        mainMenuViewModel.let { mainMenuViewModel ->
            if (!mainMenuViewModel.hasAdRequest()) {
                mainMenuViewModel.adRequest = AdRequest.Builder().build()
            }
            mainMenuViewModel.adRequest?.let { adRequest ->  mAdView.loadAd(adRequest) }
        }

    }

    private fun gotoMessageCenterFragment(v: View) {
        try {
            findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment)
        } catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    private fun gotoLanguagesFragment(v: View) {
        try {
            findNavController(v).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment)
        } catch (e: IllegalArgumentException) { e.printStackTrace() }
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
        } ?: disableButton()
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
                        //globalPreferencesViewModel.saveAll()
                        //globalPreferencesViewModel.saveToFile(requireContext())
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

    private fun doNewsletterNotification() {
        Log.d("MessageCenter", "Starting animation")
        try {
            buttonMsgInbox?.setContent { NewsAlert(true) }
            //newsIcon?.setContent { NewsAlert(true) }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    /*
    private fun startInitNewsletterThread() {
        if (checkInternetConnection()) { startLoadNewsletterThread() }
        else {
            Log.d("MessageCenter", "Could not connect to the internet.")
            newsLetterViewModel.let { newsLetterViewModel ->
                newsLetterViewModel.compareAllInboxDates()
                if (newsLetterViewModel.requiresNotify) {
                    doNewsletterNotification()
                }
            }
        }
    }
    */

    /*
    private fun startLoadNewsletterThread() {
        newsletterThread = Thread {
            Log.d("MessageCenter", "Attempting to load inboxes...")
            val maxRetries = 3
            var retries = 0

            newsLetterViewModel.let { newsLetterViewModel ->
                while (canLoadNewsletter &&
                    (!newsLetterViewModel.isUpToDate && (retries < maxRetries))) {
                    Log.d("MessageCenter", "Attempting to load inboxes...")

                    loadMessageCenter()

                    if (!newsLetterViewModel.isUpToDate) {
                        Log.d("MessageCenter", "[Attempt $retries / $maxRetries ] " +
                                    "Inboxes are not up to date yet! Retrying...\n")
                        try { Thread.sleep(1000) }
                        catch (e: InterruptedException) { e.printStackTrace() }
                    }
                    retries++
                }

                Log.d("MessageCenter",
                    "Inboxes ${if (newsLetterViewModel.isUpToDate) "are now up to date."
                    else "could not be updated in time." } Loading completed.")

                try {
                    newsLetterViewModel.compareAllInboxDates()
                    if (newsLetterViewModel.requiresNotify) {
                        requireActivity().runOnUiThread { this.doNewsletterNotification() }
                    }
                } catch (e: IllegalStateException) { e.printStackTrace() }
            }
        }
        newsletterThread?.start()
    }
    */

    private fun stopLoadNewsletterThread() {
        newsletterThread?.interrupt()
        newsletterThread = null

        if (newsLetterViewModel?.isUpToDate == true) {
            canLoadNewsletter = false
            Log.d("MessageCenter", "IS up to date") }
        else { Log.d("MessageCenter", "IS NOT up to date") }
    }

    /** onPause method */
    override fun onPause() {
        // SAVE PERSISTENT DATA
        //saveGlobalPreferencesViewModel()

        // STOP THREADS
        animationView?.let { animationView ->
            animationView.stopAnimation()
            animationView.canAnimate = false
        }

        stopLoadNewsletterThread()

        super.onPause()
    }

    /** onResume method */
    override fun onResume() {
        // START THREADS
        animationView?.init(mainMenuViewModel.animationModel)

        //startInitNewsletterThread()

        super.onResume()
    }

    /** onDestroy method */
    override fun onDestroy() {
        // DESTROY AD-REQUEST
        mainMenuViewModel.adRequest = null

        super.onDestroy()
    }

}