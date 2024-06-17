package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views.StartScreenAnimationView
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils
import com.TritiumGaming.phasmophobiaevidencepicker.views.account.AccountIconView
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.NewsAlert
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.setIconDropdownMenu
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import java.util.Locale

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
class StartScreenFragment : MainMenuFragment() {
    private var animationView: StartScreenAnimationView? = null

    private val bitmapUtils = BitmapUtils()

    private var canRunMessageCenter = true
    private var threadMessageCenter: Thread? = null

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
        val iconApp = view.findViewById<AppCompatImageView>(R.id.icon_appicon)
        val buttonInfo = view.findViewById<ComposeView>(R.id.button_info)
        val buttonSettings = view.findViewById<ComposeView>(R.id.button_settings)
        val buttonReview = view.findViewById<AppCompatImageView>(R.id.button_review)
        buttonMsgInbox = view.findViewById(R.id.button_inbox)
        val buttonLanguage = view.findViewById<View>(R.id.listener_language)

        // LISTENERS
        buttonInfo.setOnClickListener { v: View -> this.gotoAppInfoFragment(v) }
        buttonLanguage.setOnClickListener { v: View -> this.gotoLanguagesFragment(v) }
        buttonMsgInbox?.setOnClickListener { v: View -> this.gotoMessageCenterFragment(v) }
        buttonStart.setOnClickListener {
            try {
                val intent = Intent(requireActivity(), InvestigationActivity::class.java)
                intent.putExtra("lobby", 0)
                startActivity(intent)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }

        newsIcon = ComposeView(requireContext())
        newsIcon?.setContent { NewsAlert(false) }

        setIconDropdownMenu(
            buttonInfo,
            AccountIconView(requireContext()),
            R.navigation.titlescreen_navgraph,
            arrayOf(
                R.drawable.ic_person
            ),
            arrayOf(
                R.id.marketplaceFragment
            )
        )
        setIconDropdownMenu(
            buttonSettings,
            R.drawable.icon_ts_gear,
            R.navigation.titlescreen_navgraph,
            arrayOf(
                R.drawable.icon_ts_gear,
                R.drawable.icon_ts_globe
            ),
            arrayOf(
                R.id.appSettingsFragment,
                R.id.appLanguageFragment
            )
        )

        buttonMsgInbox?.setContent { NewsAlert(false) }

        setBackgroundLogo(iconApp)
        setLanguageName(labelLanguageName)

        super.initAdView(view.findViewById(R.id.adView))

        try {
            if (!(requireActivity() as MainMenuActivity).checkForAppUpdates()) {
                initReviewRequest(buttonReview)
            } }
        catch (e: IllegalStateException) { e.printStackTrace() }

        try { doReviewRequest() }
        catch (e: SendIntentException) { e.printStackTrace() }
    }

    override fun backPressedHandler() {
        closePopup()
    }

    override fun initViewModels() {
        super.initViewModels()
        initMainMenuViewModel()
        initNewsletterViewModel()
    }

    private fun loadMessageCenter() {
        try { newsLetterViewModel?.registerInboxes(requireContext()) }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun setBackgroundLogo(iconApp: AppCompatImageView) {
        bitmapUtils.clearResources()
        bitmapUtils.setResource(R.drawable.app_icon_sm)

        try { iconApp.setImageBitmap(bitmapUtils.compileBitmaps(requireContext())) }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun setLanguageName(labelLanguageName: AppCompatTextView) {
        var chosenLanguage = Locale.getDefault().displayLanguage
        if (chosenLanguage.isNotEmpty()) {
            chosenLanguage = chosenLanguage.substring(0, 1).uppercase(Locale.getDefault()) +
                    chosenLanguage.substring(1)
        }
        labelLanguageName.text = chosenLanguage
    }

    private fun loadBannerAd(view: View) {
        try {
            MobileAds.initialize(requireActivity()) { }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        val mAdView = view.findViewById<AdView>(R.id.adView)

        if (!mainMenuViewModel.hasAdRequest()) {
            mainMenuViewModel.adRequest = AdRequest.Builder().build()
        }
        mAdView.loadAd(mainMenuViewModel.adRequest!!)
    }

    private fun gotoMessageCenterFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment)
    }

    private fun gotoMarketplaceFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_marketplaceFragment)
    }

    /** gotoAppInfoFragment method */
    private fun gotoAppInfoFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_appInfoFragment)
    }

    /** gotoAppSettingsFragment method */
    private fun gotoAppSettingsFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_appSettingsFragment)
    }

    /** gotoLanguagesFragment method */
    private fun gotoLanguagesFragment(v: View) {
        findNavController(v).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment)
    }

    private fun initReviewRequest(buttonReview: AppCompatImageView) {
        // REQUEST REVIEW LISTENER
        if (globalPreferencesViewModel.reviewRequestData.timesOpened > 2) {
            buttonReview.setOnClickListener {
                try { showReviewPopup(requireView()) }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }
        } else {
            buttonReview.isEnabled = false
            buttonReview.visibility = View.INVISIBLE
        }
    }

    /**
     * doReviewRequest method
     */
    @Throws(SendIntentException::class)
    fun doReviewRequest() {
        if (globalPreferencesViewModel.reviewRequestData.canRequestReview()) {
            Log.d("Review", "Review Request Accepted")
            Thread {
                try { Thread.sleep(1000L) }
                catch (e: InterruptedException) { e.printStackTrace() }
                globalPreferencesViewModel.reviewRequestData.wasRequested = true
                try {
                    requireActivity().runOnUiThread {
                        try { showReviewPopup(requireView()) }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }
                    globalPreferencesViewModel.saveToFile(requireContext())
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }.start()
        } else {
            Log.d("Review", "Review Request Denied")
        }
    }

    /**
     * showReviewPopup method
     */
    private fun showReviewPopup(parentView: View) {
        //DESTROY PREVIOUS POPUP
        popupWindow?.dismiss()

        //INFLATE LAYOUT
        val inflater: LayoutInflater = requireView().context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("InflateParams") val customView =
            inflater.inflate(R.layout.popup_requestreview, null)

        // INITIALIZE POPUPWINDOW
        popupWindow = PopupWindow(
            customView,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        popupWindow?.isOutsideTouchable = false

        val acceptButton = customView.findViewById<AppCompatButton>(R.id.label_accept)
        val declineButton = customView.findViewById<AppCompatButton>(R.id.label_decline)

        // LISTENERS
        acceptButton.setOnClickListener {
            popupWindow?.dismiss()
            popupWindow = null
            startReviewLauncher()
        }

        declineButton.setOnClickListener {
            val params = Bundle()
            params.putString("event_type", "user_action")
            params.putString("event_details", "request_rejected")
            analytics.logEvent("event_review_manager", params)

            popupWindow?.dismiss()
            popupWindow = null
        }

        // FINALIZE
        popupWindow?.animationStyle = androidx.navigation.ui.R.anim.nav_default_enter_anim

        val success = parentView.post {
            popupWindow?.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0)
        }

        Log.d("ReviewRequest", (if (success) "SUCCESSFUL" else "UNSUCCESSFUL"))

        val params = Bundle()
        params.putString("event_type", "review_requested")
        params.putString(
            "event_details",
            "request_" + (if (success) "successful" else "unsuccessful")
        )
        analytics.logEvent("event_review_manager", params)
    }

    private fun startReviewLauncher() {
        //THIS IS FOR THE IN-APP REVIEW
        try {
            val manager =
                ReviewManagerFactory.create(requireContext())

            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { requestTask: Task<ReviewInfo?> ->
                if (requestTask.isSuccessful) {
                    Log.e("ReviewManager", "Task Successful")
                    // We can get the ReviewInfo object
                    val reviewInfo = requestTask.result

                    try {
                        val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                        flow.addOnCompleteListener { flowTask: Task<Void?>? -> }
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(
                        Uri.parse(
                            resources.getString
                                (R.string.review_storelink_website)
                        )
                    )
                    intent.setPackage("com.android.vending")

                    try {
                        requireContext().startActivity(intent)
                        Log.e(
                            "ReviewManager",
                            "SUCCEEDED intent navigation to the App Store."
                        )
                    } catch (e: IllegalStateException) {
                        Log.e(
                            "ReviewManager",
                            "FAILED intent navigation to the App Store."
                        )
                        e.printStackTrace()

                        val params = Bundle()
                        params.putString("event_type", "review_navigation")
                        params.putString("event_details", "navigation_failed")
                        analytics.logEvent("event_review_manager", params)
                    } catch (e: ActivityNotFoundException) {
                        Log.e(
                            "ReviewManager",
                            "FAILED intent navigation to the App Store."
                        )
                        e.printStackTrace()

                        val params = Bundle()
                        params.putString("event_type", "review_navigation")
                        params.putString("event_details", "navigation_failed")
                        analytics.logEvent("event_review_manager", params)
                    }
                } else {
                    Log.e("ReviewManager", "Task Failed")
                    requestTask.exception?.printStackTrace()
                }
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun doMessageCenterNotification() {
        Log.d("MessageCenter", "Starting animation")
        try {
            buttonMsgInbox?.setContent { NewsAlert(true) }
            newsIcon?.setContent { NewsAlert(true) }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun startLoadMessageCenterThread() {
        threadMessageCenter = Thread {
            Log.d("MessageCenter", "Attempting to load inboxes...")
            val maxCycles = 3
            var currentCycle = 0

            while (canRunMessageCenter &&
                (!newsLetterViewModel.isUpToDate && (currentCycle < maxCycles))
            ) {
                Log.d("MessageCenter", "Attempting to load inboxes...")

                loadMessageCenter()

                if (!newsLetterViewModel.isUpToDate) {
                    Log.d(
                        "MessageCenter",
                        "[Attempt " + currentCycle + "/" + maxCycles + "] " +
                                "Inboxes are not up to date yet! Retrying...\n"
                    )
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                currentCycle++
            }

            Log.d("MessageCenter",
                "Inboxes ${if (newsLetterViewModel.isUpToDate) "are now up to date." 
                else "could not be updated in time." } Loading completed.")
            try {
                if (!newsLetterViewModel.init(requireContext())) {
                    Log.e("MessageCenter", "Initialization failed.")
                }
                newsLetterViewModel.compareAllInboxDates()
                if (newsLetterViewModel.requiresNotify()) {
                    requireActivity().runOnUiThread { this.doMessageCenterNotification() }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
        threadMessageCenter?.start()
    }

    private fun startInitMessageCenterThread() {
        if (checkInternetConnection()) {
            startLoadMessageCenterThread()
        } else {
            Log.d("MessageCenter", "Could not connect to the internet.")
            newsLetterViewModel.compareAllInboxDates()
            if (newsLetterViewModel.requiresNotify()) {
                doMessageCenterNotification()
            }
        }
    }

    private fun stopLoadMessageCenterThread() {
        threadMessageCenter?.interrupt()
        threadMessageCenter = null
    }

    /** onPause method */
    override fun onPause() {
        // SAVE PERSISTENT DATA
        saveGlobalPreferencesViewModel()

        // STOP THREADS
        animationView?.let { animationView ->
            animationView.stopAnimThreads()
            animationView.stopAnimInitThreads()
            animationView.canAnimateBackground(false)
        }

        stopLoadMessageCenterThread()
        if (newsLetterViewModel.isUpToDate) {
            canRunMessageCenter = false
            Log.d("MessageCenter", "IS up to date")
        } else { Log.d("MessageCenter", "IS NOT up to date") }

        // RECYCLE ANIMATION VIEW
        animationView?.recycleBitmaps()

        super.onPause()
    }

    /** onResume method */
    override fun onResume() {
        // START THREADS
        animationView?.let { animationView ->
            animationView.startAnimInitThreads(mainMenuViewModel, bitmapUtils)
            animationView.startAnimThreads()
        }

        startInitMessageCenterThread()

        super.onResume()
    }

    /** onDestroy method */
    override fun onDestroy() {
        // DESTROY AD-REQUEST
        mainMenuViewModel?.adRequest = null

        super.onDestroy()
    }

    /** onLowMemory method */
    override fun onLowMemory() {
        // RECYCLE ANIMATION VIEW BITMAPS
        animationView?.recycleBitmaps()

        super.onLowMemory()
    }
}