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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.common.menus.IconDropdownMenu
import com.tritiumgaming.core.ui.common.menus.IconDropdownMenuColors
import com.tritiumgaming.core.ui.common.menus.SecondarySelector
import com.tritiumgaming.core.ui.common.prefabicon.AccountIcon
import com.tritiumgaming.core.ui.common.prefabicon.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.common.prefabicon.BadgeIcon
import com.tritiumgaming.core.ui.common.prefabicon.LanguageIcon
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.base.GearIcon
import com.tritiumgaming.core.ui.icon.base.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.base.InfoIcon
import com.tritiumgaming.core.ui.icon.base.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.base.PersonIcon
import com.tritiumgaming.core.ui.icon.base.ReviewIcon
import com.tritiumgaming.core.ui.icon.base.StoreIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.InvestigationActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.StartScreenAnimationView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.review.ReviewLauncher
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.review.ReviewPopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getDrawableFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getTextStyleFromAttribute
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource
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
        val headerBar = view.findViewById<ComposeView>(R.id.headerbar)
        val languageButton = view.findViewById<ComposeView>(R.id.icon_language)
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

        val languageIcon: @Composable () -> Unit = {
            LanguageIcon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                colors = IconVectorColors.defaults(
                    fillColor = Color(getColorFromAttribute(requireActivity(), R.attr.textColorBody)),
                    strokeColor = Color(getColorFromAttribute(requireActivity(), R.attr.backgroundColor))
                )
            ) {
                try {
                    findNavController(view).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment)
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
        }
        languageButton?.setContent { languageIcon() }

        headerBar?.setContent {

            val context = LocalContext.current
            val discordInvitation = stringResource(com.tritiumgaming.core.resources.R.string.link_discordInvite)

            val menuIcon: @Composable () -> Unit = { HamburgerMenuIcon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),

                colors = IconVectorColors.defaults(
                    fillColor = Color(getColorFromAttribute(context, R.attr.backgroundColor)),
                    strokeColor = Color(getColorFromAttribute(context, R.attr.textColorBody))
                )
            ) }
            val infoIcon: @Composable () -> Unit = { InfoIcon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                colors = IconVectorColors.defaults(
                    fillColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                    strokeColor = Color(getColorFromAttribute(context, R.attr.backgroundColor))
                )
            ) }
            val gearIcon: @Composable () -> Unit = { GearIcon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                colors = IconVectorColors.defaults(
                    fillColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                    strokeColor = Color(getColorFromAttribute(context, R.attr.backgroundColor))
                )
            ) }
            val discordIcon: @Composable () -> Unit = {
                BadgeIcon(
                    modifier = Modifier
                        .size(48.dp),
                    baseComponent = {
                        IconResource.DISCORD.ToComposable(
                            colors = IconVectorColors.defaults(
                                fillColor = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground)),
                                strokeColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                            )
                        )
                    }
                ) {
                    OpenInNewIcon(
                        colors = IconVectorColors.defaults(
                            fillColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                            strokeColor = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground)),
                        )
                    )
                }
            }
            val patreonIcon: @Composable () -> Unit = {
                BadgeIcon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    baseComponent = {
                        IconResource.PATREON.ToComposable(
                            colors = IconVectorColors.defaults(
                                fillColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                                strokeColor = Color(getColorFromAttribute(context, R.attr.textColorBody)),
                            )
                        )
                    }
                )
            }
            val reviewIcon: @Composable () -> Unit = {
                ReviewIcon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                        .clickable(onClick = {
                            try {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        getString(com.tritiumgaming.core.resources.R.string.link_appstore_review).toUri()
                                    )
                                )
                            } catch (e: IllegalStateException) {
                                e.printStackTrace()
                            }
                        }),
                    colors = IconVectorColors.defaults(
                        fillColor = Color(getColorFromAttribute(context, R.attr.backgroundColor)),
                        strokeColor = Color(getColorFromAttribute(context, R.attr.textColorBody))
                    )
                )
            }

            val accountIcon: @Composable () -> Unit = {
                AccountIcon(
                    modifier = Modifier
                        .size(48.dp),
                    borderColor =  Color(getColorFromAttribute(
                        context, R.attr.textColorBody)),
                    backgroundColor = Color(getColorFromAttribute(
                        context, R.attr.backgroundColorOnBackground)),
                    placeholder = {
                        IconResource.PERSON.ToComposable(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            colors = IconVectorColors.defaults(
                                fillColor = Color(getColorFromAttribute(
                                    context, R.attr.backgroundColor)),
                                strokeColor = Color(getColorFromAttribute(
                                    context, R.attr.textColorBody))
                            )
                        )
                    },
                    content = {
                        val authUser = Firebase.auth.currentUser
                        val authUserName = authUser?.displayName ?: ""

                        val names: List<String?> = (authUserName).split(" ")

                        val textStyle = getTextStyleFromAttribute(
                            context, R.attr.primaryFont_Bold_Auto)

                        AccountIconPrimaryContent(
                            firstName = names.getOrNull(0) ?: "",
                            lastName = names.getOrNull(1) ?: "",
                            textStyle = textStyle.copy(
                                color = Color(getColorFromAttribute(
                                    context, R.attr.textColorBody)),
                                textAlign = TextAlign.Center,
                                shadow = Shadow(
                                    color = Color(getColorFromAttribute(
                                        context, R.attr.backgroundColorOnBackground)),
                                    blurRadius = 8f
                                ),
                            )
                        ){
                            getDrawableFromAttribute(
                                context = context,
                                attribute = R.attr.theme_badge
                            )?.let { drawableRes ->
                                Image(
                                    painter = painterResource(id = drawableRes),
                                    contentDescription = "",
                                    contentScale = ContentScale.Inside,
                                    alpha = .5f
                                )
                            }
                        }
                    }
                )
            }
            val personIcon: @Composable () -> Unit = {
                PersonIcon(
                    modifier = Modifier,
                    colors = IconVectorColors.defaults(
                        strokeColor = Color(getColorFromAttribute(
                            context, R.attr.textColorBody))
                    )
                )
            }
            val storeIcon: @Composable () -> Unit = {
                StoreIcon(
                    modifier = Modifier,
                    colors = IconVectorColors.defaults(
                        fillColor = Color(getColorFromAttribute(
                            context, R.attr.textColorBody)),
                        strokeColor = Color(getColorFromAttribute(
                            context, R.attr.textColorBody))
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconDropdownMenu(
                    modifier = Modifier,
                    colors = IconDropdownMenuColors(
                        primaryContentBackground = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground)),
                        dropdownContentBackground = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground))
                    ),
                    primaryContent = menuIcon,
                    dropdownContent = @Composable {
                        SecondarySelector(
                            onClick = {
                                try {
                                    findNavController(view).navigate(
                                        R.id.action_titleScreenFragment_to_appInfoFragment)
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }
                        ) {
                            infoIcon()
                        }
                        SecondarySelector(
                            onClick = {
                                try {
                                    findNavController(view).navigate(
                                        R.id.action_titleScreenFragment_to_appSettingsFragment)
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }
                        ) {
                            gearIcon()
                        }
                        SecondarySelector(
                            onClick = {
                                try {
                                    findNavController(view).navigate(
                                        R.id.action_titleScreenFragment_to_appLanguageFragment)
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }
                        ) {
                            languageIcon()
                        }
                        SecondarySelector(
                            onClick = {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            "https://patreon.com/ ${getString(com.tritiumgaming.core.resources.R.string.link_patreonInvite)}"
                                                .toUri()
                                        )
                                    )
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }
                        ) {
                            patreonIcon()
                        }
                        SecondarySelector(
                            onClick = {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        "https://discord.gg/ $discordInvitation".toUri()
                                    )
                                )
                            }) {
                            discordIcon()
                        }
                    }
                ) { false }

                val notificationState = newsLetterViewModel?.requiresNotify?.collectAsStateWithLifecycle()
                // News Button

                NotificationIndicator(
                    isActive = notificationState?.value ?: false,
                    baseComponent = @Composable { modifier ->
                        IconResource.NEWS.ToComposable(
                            modifier = modifier,
                            colors = IconVectorColors(
                                fillColor = Color(getColorFromAttribute(
                                    context, R.attr.backgroundColor)),
                                strokeColor = Color(getColorFromAttribute(
                                    context, R.attr.textColorBody))
                            ),
                        )
                    },
                    badgeComponent = @Composable { modifier ->
                        IconResource.NOTIFY.ToComposable(
                            modifier = modifier,
                            colors = IconVectorColors(
                                fillColor = Color(getColorFromAttribute(
                                        context, R.attr.backgroundColor)),
                                strokeColor = Color(getColorFromAttribute(
                                        context, R.attr.inboxNotification))
                            )
                        )
                    },
                ) {
                    try {
                        findNavController(view).navigate(
                            R.id.action_titleScreenFragment_to_inboxFragment)
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }

                /*NotificationIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    isActive = notificationState?.value ?: false,
                    baseIcon = IconResources.IconResource.NEWS,
                    baseTint = IconVectorColors(
                        fillColor = Color(
                            getColorFromAttribute(context, R.attr.backgroundColor)),
                        strokeColor = Color(
                            getColorFromAttribute(context, R.attr.textColorBody))
                    ),
                    alertIcon = IconResources.IconResource.NOTIFY,
                    alertTint = IconVectorColors(
                        fillColor = Color(
                            getColorFromAttribute(
                                context, R.attr.backgroundColor)),
                        strokeColor = Color(
                            getColorFromAttribute(
                                context, R.attr.inboxNotification))
                    )
                ) {
                    try {
                        findNavController(view).navigate(
                            R.id.action_titleScreenFragment_to_inboxFragment)
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }*/

                reviewIcon()

                IconDropdownMenu(
                    modifier = Modifier,
                    colors = IconDropdownMenuColors(
                        primaryContentBackground = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground)),
                        dropdownContentBackground = Color(getColorFromAttribute(context, R.attr.backgroundColorOnBackground))
                    ),
                    primaryContent = accountIcon,
                    dropdownContent = @Composable {
                        SecondarySelector(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp),
                            onClick = {
                                try {
                                    findNavController(view).navigate(R.id.action_titleScreenFragment_to_accountOverviewFragment)
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }) {
                            personIcon()
                        }
                        SecondarySelector(
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp),
                            onClick = {
                                try {
                                    findNavController(view).navigate(R.id.action_titleScreenFragment_to_marketplaceFragment)
                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()
                                }
                            }
                        ) {
                            storeIcon()
                        }
                    }
                ) { false }
            }

        }

        //setBackgroundLogo(iconApp)
        setLanguageName(labelLanguageName)

        super.initAdView(view.findViewById(R.id.adView))

        /*try {
            initReviewRequest(buttonReview)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }*/

        try {
            doReviewRequest()
        }
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
        catch (e: IllegalStateException) {
            Log.d("MessageCenter", "Failed registering inboxes")
            e.printStackTrace() }
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
        try { MobileAds.initialize(requireActivity()) { } }
        catch (e: IllegalStateException) { e.printStackTrace() }

        val mAdView = view.findViewById<AdView>(R.id.adView)

        mainMenuViewModel?.let { mainMenuViewModel ->
            if (!mainMenuViewModel.hasAdRequest()) {
                mainMenuViewModel.adRequest = AdRequest.Builder().build()
            }
            mainMenuViewModel.adRequest?.let { adRequest ->  mAdView.loadAd(adRequest) }
        }

    }

    private fun gotoMessageCenterFragment(v: View) {
        try {
            findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment)
        } catch (e: IllegalStateException) { e.printStackTrace() }
        catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    private fun gotoLanguagesFragment(v: View) {
        try {
            findNavController(v).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment)
        } catch (e: IllegalStateException) { e.printStackTrace() }
        catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    private fun initReviewRequest(buttonReview: AppCompatImageView) {
        val disableButton = {
            buttonReview.isEnabled = false
            buttonReview.visibility = View.INVISIBLE
        }

        // REQUEST REVIEW LISTENER
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            if (globalPreferencesViewModel.reviewRequestData.canShowReviewButton()) {
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
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            if (globalPreferencesViewModel.reviewRequestData.canRequestReview()) {
                Log.d("Review", "Review Request Accepted")
                Thread {
                    try { Thread.sleep(1000L) }
                    catch (e: InterruptedException) { e.printStackTrace() }
                    globalPreferencesViewModel.reviewRequestData.wasRequested = true
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

    private fun startInitNewsletterThread() {
        if (checkInternetConnection()) { startLoadNewsletterThread() }
        else {
            Log.d("MessageCenter", "Could not connect to the internet.")
            newsLetterViewModel?.compareAllInboxDates()
        }
    }

    private fun startLoadNewsletterThread() {
        newsletterThread = Thread {
            Log.d("MessageCenter", "Attempting to load inboxes...")
            val maxRetries = 3
            var retries = 0

            newsLetterViewModel?.let { newsLetterViewModel ->
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
                } catch (e: IllegalStateException) { e.printStackTrace() }
            }
        }
        newsletterThread?.start()
    }

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
        saveGlobalPreferencesViewModel()

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
        animationView?.init(mainMenuViewModel)

        startInitNewsletterThread()

        super.onResume()
    }

    /** onDestroy method */
    override fun onDestroy() {
        // DESTROY AD-REQUEST
        mainMenuViewModel?.adRequest = null

        super.onDestroy()
    }

}