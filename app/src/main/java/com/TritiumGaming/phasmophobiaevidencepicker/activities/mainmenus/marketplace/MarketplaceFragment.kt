package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.MarketplaceListLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.ThemeBundleCardView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.ThemeSingleCardView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.bundle.MarketThemeBundleModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.theme.MarketSingleThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.bundles.FirestoreMerchandiseBundle.Companion.getBundleWhere
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.themes.FirestoreMerchandiseThemes.Companion.getThemesWhere
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.buildUserDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.addCredits
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.creditsDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.removeCredits
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory.Companion.addUnlockDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory.Companion.addUnlockedDocuments
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.firestore.OnFirestoreProcessListener
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.TritiumGaming.phasmophobiaevidencepicker.views.account.AccountObtainCreditsView
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope


class MarketplaceFragment : MainMenuFragment() {
    private var masterItemsList: LinearLayout? = null

    private var bundleMarketplaceList: MarketplaceListLayout? = null
    private var prestigeMarketplaceList: MarketplaceListLayout? = null
    private var eventMarketplaceList: MarketplaceListLayout? = null
    private var communityMarketplaceList: MarketplaceListLayout? = null

    private var accountCreditsTextView: AppCompatTextView? = null
    private var marketplaceErrorTextView: AppCompatTextView? = null

    private var obtainCreditsTextView: AccountObtainCreditsView? = null

    private var marketProgressBar: ProgressBar? = null

    private var rewardedAd: RewardedAd? = null

    private var scope: CoroutineScope? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_marketplace, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val backButton = navHeaderLayout.findViewById<PETImageButton>(R.id.button_left)

        val accountLoginButton =
            view.findViewById<AppCompatButton>(R.id.settings_account_login_button)
        val watchAdButton = view.findViewById<AppCompatButton>(R.id.button_ad_watch)
        val buyButton = view.findViewById<AppCompatButton>(R.id.settings_account_buy_button)

        obtainCreditsTextView = view.findViewById(R.id.layout_account_obtainCredits)

        marketplaceErrorTextView = view.findViewById(R.id.market_loaderror)
        accountCreditsTextView = view.findViewById(R.id.label_credits_actual)

        val marketplaceScrollView = view.findViewById<ScrollView>(R.id.scrollview_marketplace_items)
        marketplaceScrollView?.let {
            masterItemsList = marketplaceScrollView.findViewById(R.id.list_marketplace_items)
        }

        marketProgressBar = view.findViewById(R.id.market_progressbar)

        accountLoginButton.setOnClickListener {
            manualSignInAccount()
            view.invalidate()
        }

        watchAdButton.setOnClickListener { showRewardedAd() }

        buyButton.setOnClickListener { v: View? -> this.gotoBillingMarketplace() }

        if (currentFirebaseUser == null) { marketProgressBar?.visibility = View.GONE }

        // CANCEL BUTTON
        backButton.setOnClickListener { v: View? ->
            try { v?.let{ findNavController(it).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        obtainCreditsTextView?.enableAdWatchButton(false)
        requireActivity().runOnUiThread { loadRewardedAd(null) }

        requireActivity().runOnUiThread {
            Thread { this.initAccountCreditListener() }.start() }
        requireActivity().runOnUiThread {
            Thread { this.populateMarketplaceUnPurchasedItems() }.start() }
    }

    private fun gotoBillingMarketplace() {
        findNavController(requireActivity(),
            R.id.action_marketplaceFragment_to_marketplaceBillingFragment)
    }

    private fun initAccountCreditListener() {
        try {
            val creditDoc = creditsDocument
            creditDoc.get()
                .addOnSuccessListener { task: DocumentSnapshot ->
                    val userCredits =
                        task.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                    accountCreditsTextView?.text = userCredits.toString()
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could get user's account credit count!")
                    e.printStackTrace()
                }

            creditDoc.addSnapshotListener { documentSnapshot: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                if (documentSnapshot == null) { return@addSnapshotListener }
                val userCredits = documentSnapshot.get(
                    FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                accountCreditsTextView?.text = userCredits.toString()
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun populateMarketplaceUnPurchasedItems() {
        var unlockHistoryCollection: CollectionReference? = null
        try { unlockHistoryCollection = FirestoreUnlockHistory.unlockHistoryCollection }
        catch (e: Exception) { e.printStackTrace() }
        if (unlockHistoryCollection == null) { return }

        try {
            unlockHistoryCollection.get()
                .addOnSuccessListener { task: QuerySnapshot ->
                    for (documentSnapshot in task.documents) {
                        val uuid = documentSnapshot.reference.id
                        globalPreferencesViewModel?.colorThemeControl?.let { control ->
                            val customTheme = control.getThemeByUUID(uuid)
                            customTheme.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                        }
                    }
                    populateBundledThemes()
                    populateIndividualThemes()
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could not populate user's unlock history!")
                    marketProgressBar?.visibility = View.GONE
                    marketplaceErrorTextView?.visibility = View.VISIBLE
                    e.printStackTrace()
                }
        } catch (e: Exception) { e.printStackTrace() }

        try {
            unlockHistoryCollection.addSnapshotListener(EventListener {
                value: QuerySnapshot?, _: FirebaseFirestoreException? ->
                if (value == null) { return@EventListener }

                for (documentSnapshot in value.documents) {
                    val uuid = documentSnapshot.reference.id
                    globalPreferencesViewModel?.colorThemeControl?.let { control ->
                        val customTheme = control.getThemeByUUID(uuid)
                        customTheme.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                    }
                }

                revalidateMarketplaceBundles()
                revalidateMarketplaceSingles()
            })
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun revalidateMarketplaceBundles() {
        bundleMarketplaceList?.validateChildren()
    }

    private fun revalidateMarketplaceSingles() {
        prestigeMarketplaceList?.validateChildren()
        eventMarketplaceList?.validateChildren()
        communityMarketplaceList?.validateChildren()
    }

    private fun populateBundledThemes() {
        try { bundleMarketplaceList = MarketplaceListLayout(requireContext()) }
        catch (e: IllegalStateException) { e.printStackTrace() }

        bundleMarketplaceList?.let { list ->
            list.setLabel("Theme Bundles")
            list.showLabel(View.GONE)
        } ?: return

        masterItemsList?.addView(bundleMarketplaceList)

        val processCompleteListener: OnFirestoreProcessListener =
            object : OnFirestoreProcessListener() {
                var thrown: Boolean = false
                var labelShown: Boolean = true

                override fun onFailure() {
                    if (!thrown) thrown = true

                    labelShown = false

                    marketProgressBar?.visibility = View.GONE
                    marketplaceErrorTextView?.visibility = View.VISIBLE

                    try { Toast.makeText(requireActivity(), "Could not access the marketplace.",
                        Toast.LENGTH_SHORT).show() }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }

                override fun onSuccess() {
                    if (!thrown) thrown = true
                }

                override fun onComplete() {
                    if (!thrown) thrown = true

                    if (labelShown) {
                        bundleMarketplaceList?.showLabel(View.VISIBLE)
                    }

                    marketProgressBar?.visibility = View.GONE
                }
            }

        try {
            globalPreferencesViewModel?.networkPreference?.let { networkPreference ->
                if (isNetworkAvailable(requireContext(), networkPreference)) {
                    bundleMarketplaceList?.let { bundle ->
                        addMarketplaceBundleThemes(bundle, null, null,
                            null, null, processCompleteListener) } }
                else {
                    processCompleteListener.onFailure()
                    Toast.makeText(requireActivity(), "Internet not available.",
                        Toast.LENGTH_SHORT).show() }
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun populateIndividualThemes() {
        try {
            prestigeMarketplaceList = MarketplaceListLayout(requireContext())
            eventMarketplaceList = MarketplaceListLayout(requireContext())
            communityMarketplaceList = MarketplaceListLayout(requireContext())
        } catch (e: IllegalStateException) { e.printStackTrace() }

        if (prestigeMarketplaceList == null || eventMarketplaceList == null ||
            communityMarketplaceList == null) { return }

        prestigeMarketplaceList?.setLabel("Prestige Themes")
        eventMarketplaceList?.setLabel("Event Themes")
        communityMarketplaceList?.setLabel("Community Themes")

        masterItemsList?.let { masterList ->
            masterList.addView(prestigeMarketplaceList)
            masterList.addView(eventMarketplaceList)
            masterList.addView(communityMarketplaceList)
        }

        val processCompleteListener: OnFirestoreProcessListener =
            object : OnFirestoreProcessListener() {
                var thrown: Boolean = false
                var labelShown: Boolean = false

                override fun onFailure() {
                    if (!thrown) thrown = true

                    marketProgressBar?.visibility = View.GONE
                    marketplaceErrorTextView?.visibility = View.VISIBLE

                    try { Toast.makeText(requireActivity(), "Could not access the marketplace.",
                        Toast.LENGTH_SHORT).show() }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }

                override fun onSuccess() {
                    if (!thrown) thrown = true
                    labelShown = true
                }

                override fun onComplete() {
                    if (!thrown) thrown = true
                    marketProgressBar?.visibility = View.GONE
                }
            }

        try {
            if (!isNetworkAvailable(requireContext(), globalPreferencesViewModel!!.networkPreference)) {
                Toast.makeText(requireActivity(), "Internet not available.", Toast.LENGTH_SHORT).show()
                processCompleteListener.onFailure()
                return
            }

            prestigeMarketplaceList?.let { list ->
                addMarketplaceSingleThemes(list, "group", "Prestige",
                    "priority", Query.Direction.ASCENDING, processCompleteListener)
            }
            eventMarketplaceList?.let { list ->
                addMarketplaceSingleThemes(list, "group", "Event",
                    null, null, processCompleteListener)
            }
            communityMarketplaceList?.let { list ->
                addMarketplaceSingleThemes(list, "group", "Community",
                    null, null, processCompleteListener)
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun addMarketplaceSingleThemes(
        list: MarketplaceListLayout, field: String, value: String,
        orderField: String?, order: Query.Direction?,
        listener: OnFirestoreProcessListener
    ) {
        var query: Task<QuerySnapshot>? = null
        try { query = getThemesWhere(field, value, orderField, order) }
        catch (e: Exception) { e.printStackTrace() }

        if (query == null) {
            listener.onFailure()
            return
        }

        query.addOnSuccessListener(OnSuccessListener { snapshot: QuerySnapshot ->
            listener.onSuccess()
            for (documentSnapshot in snapshot.documents) {
                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "Theme document snapshot DNE.")
                } else {
                    val uuid = documentSnapshot.reference.id
                    var marketSingleThemeTemp: MarketSingleThemeModel? = null
                    try { marketSingleThemeTemp =
                        documentSnapshot.toObject(MarketSingleThemeModel::class.java) }
                    catch (e: Exception) {
                        Log.d("Firestore", "Error CREATING PETTheme!")
                        e.printStackTrace()
                    }

                    marketSingleThemeTemp?.let { singleThemeTemp ->
                        val customTheme = globalPreferencesViewModel!!.colorThemeControl.getThemeByUUID(uuid)

                        val marketSingleThemeFinal =
                            MarketSingleThemeModel(uuid, singleThemeTemp, customTheme)

                        try {
                            val marketplaceItem =
                                buildMarketplaceSingleThemeView(list, marketSingleThemeFinal)
                            if (!marketSingleThemeFinal.isUnlocked) {
                                list.addView(marketplaceItem)
                                list.requestLayout()
                                list.invalidate()
                            }
                        } catch (e: IllegalStateException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }).addOnFailureListener { listener.onFailure()
        }.addOnCompleteListener { listener.onComplete()
            if (list.childCount <= 1) { list.visibility = View.GONE }
            else { list.showLabel(View.VISIBLE) }
        }
    }

    private fun addMarketplaceBundleThemes(
        list: MarketplaceListLayout, field: String?, value: String?,
        orderField: String?, order: Query.Direction?, listener: OnFirestoreProcessListener) {
        var query: Task<QuerySnapshot>? = null
        try { query = getBundleWhere(field, value, orderField, order) }
        catch (e: Exception) { e.printStackTrace() }

        if (query == null) {
            listener.onFailure()
            return
        }

        query.addOnSuccessListener(OnSuccessListener { snapshot: QuerySnapshot ->
            listener.onSuccess()
            for (documentSnapshot in snapshot.documents) {
                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "Bundle document snapshot DNE.")
                    continue
                }

                documentSnapshot.data?.let { data ->
                    val docId = documentSnapshot.reference.id

                    val documentReferences = data["items"] as List<*>?
                    val themeIDs = ArrayList<String>()
                    documentReferences?.let { docRefs ->
                        for (item in docRefs) {
                            if (item is DocumentReference) { themeIDs.add(item.id) } }
                    }

                    var tempBundle: MarketThemeBundleModel? = null
                    try { tempBundle = documentSnapshot.toObject(MarketThemeBundleModel::class.java) }
                    catch (e: Exception) {
                        Log.d("Firestore", "Error CREATING PETTheme!")
                        e.printStackTrace()
                    }

                    tempBundle?.let { bundleTemp ->
                        val customThemes = ArrayList<ThemeModel>()
                        for (themeID in themeIDs) {
                            globalPreferencesViewModel?.colorThemeControl?.let { control ->
                                customThemes.add(control.getThemeByUUID(themeID)) } }
                        val finalBundle = MarketThemeBundleModel(docId, bundleTemp, customThemes)
                        if (!finalBundle.isUnlocked) {
                            val marketplaceItemView =
                                buildMarketplaceBundleThemeView(list, finalBundle)

                            marketplaceItemView?.let { view ->
                                list.addView(view)
                                list.requestLayout()
                                list.invalidate()
                            }
                        }
                    }
                }
            }
        }).addOnFailureListener { listener.onFailure() }
            .addOnCompleteListener { task: Task<QuerySnapshot>? ->
                if (list.childCount <= 1) {
                    list.visibility = View.GONE
                }
                listener.onComplete()
            }
    }

    fun onPurchaseSuccessAnimation(targetView: View, parentView: View) {
        val marketItemAnimation =
            targetView.animate()
                .setDuration(300)
                .translationX(parentView.width.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)

                        targetView.isEnabled = false
                    }
                })
        marketItemAnimation.start()
    }

    private fun buildMarketplaceBundleThemeView(
        list: MarketplaceListLayout, bundleThemes: MarketThemeBundleModel
    ): ThemeBundleCardView? {
        val marketplaceBundleView: ThemeBundleCardView
        try { marketplaceBundleView = ThemeBundleCardView(requireContext(), null) }
        catch (e: IllegalStateException) { e.printStackTrace()
            return null }
        marketplaceBundleView.setBundle(bundleThemes)

        val buyButtonListener = View.OnClickListener { _: View? ->
            val buyButtonCallback: OnFirestoreProcessListener =
                object : OnFirestoreProcessListener() {
                    override fun onSuccess() {
                        val uuids: ArrayList<String> = ArrayList()
                        bundleThemes.themes?.forEach { bundleTheme ->
                            bundleTheme.iD?.let { iD -> uuids.add(iD) } }

                        try {
                            addUnlockedDocuments(uuids, "Theme Bundle",
                                object : OnFirestoreProcessListener() {
                                    override fun onSuccess() {
                                        onPurchaseSuccessAnimation(marketplaceBundleView, list)
                                        /*val marketItemAnimation =
                                            marketplaceBundleView.animate()
                                                .setDuration(300)
                                                .translationX(list.width.toFloat())
                                                .setListener(object : AnimatorListenerAdapter() {
                                                    override fun onAnimationStart(animation: Animator) {
                                                        super.onAnimationStart(animation)

                                                        marketplaceBundleView.isEnabled = false
                                                    }
                                                })
                                        marketItemAnimation.start()*/
                                    }

                                    override fun onFailure() {
                                        Log.d("Firestore",
                                            "Could not add/retrieve purchase document!")
                                    }
                                })
                        } catch (e: Exception) { e.printStackTrace() }

                        try { Toast.makeText(requireActivity(), "Skin purchased!",
                            Toast.LENGTH_SHORT).show() }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }

                    override fun onFailure() {
                        try { Toast.makeText(requireActivity(),
                            "Not enough credits for purchase!", Toast.LENGTH_SHORT).show() }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }

                    override fun onComplete() {
                        Log.d("Bundle", "Bundle process completed.")
                    }
                }
            try { removeCredits(marketplaceBundleView.creditCost, buyButtonCallback) }
            catch (e: Exception) { throw RuntimeException(e) }
        }

        marketplaceBundleView.setBuyButtonListener(buyButtonListener)

        return marketplaceBundleView
    }


    @Throws(IllegalStateException::class)
    private fun buildMarketplaceSingleThemeView(
        list: MarketplaceListLayout, marketSingleTheme: MarketSingleThemeModel
    ): ThemeSingleCardView {
        val marketplaceItemView = ThemeSingleCardView(
            ContextThemeWrapper(requireContext(), marketSingleTheme.style),
            null, marketSingleTheme.style)

        marketplaceItemView.setTheme(marketSingleTheme)

        val buyButtonListener = View.OnClickListener {
            val buyButtonCallback: OnFirestoreProcessListener =
                object : OnFirestoreProcessListener() {
                    override fun onSuccess() {
                        val purchaseListener: OnFirestoreProcessListener =
                            object : OnFirestoreProcessListener() {
                                override fun onSuccess() {
                                    onPurchaseSuccessAnimation(marketplaceItemView, list)
                                    /*val marketItemAnimation =
                                        marketplaceItemView.animate()
                                            .setDuration(300)
                                            .translationX(list.width.toFloat())
                                            .setListener(object : AnimatorListenerAdapter() {
                                                override fun onAnimationStart(animation: Animator) {
                                                    super.onAnimationStart(animation)

                                                    marketplaceItemView.isEnabled = false
                                                }

                                                override fun onAnimationEnd(animation: Animator) {
                                                    super.onAnimationEnd(animation)

                                                    list.removeView(marketplaceItemView)
                                                }
                                            })
                                    marketItemAnimation.start()*/
                                }

                                override fun onFailure() {
                                    Log.d("Firestore",
                                        "Could not add/retrieve purchase document!")
                                }
                            }

                        try {
                            addUnlockDocument(marketSingleTheme.uuid.toString(),
                                "Single Theme", purchaseListener) }
                        catch (e: Exception) { e.printStackTrace() }

                        try { Toast.makeText(requireActivity(), "Skin purchased!",
                            Toast.LENGTH_SHORT).show() }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }

                    override fun onFailure() {
                        try { Toast.makeText(requireActivity(),
                            "Not enough credits for purchase!", Toast.LENGTH_SHORT).show() }
                        catch (e: IllegalStateException) { e.printStackTrace() }
                    }

                    override fun onComplete() {
                        Log.d("Bundle", "Single theme process completed.")
                    }
                }
            try { removeCredits(marketplaceItemView.creditCost, buyButtonCallback) }
            catch (e: Exception) { throw RuntimeException(e) }
        }

        marketplaceItemView.setBuyButtonListener(buyButtonListener)

        return marketplaceItemView
    }

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        result: FirebaseAuthUIAuthenticationResult ->
        try { onSignInResultAccount(result) }
        catch (runtimeException: RuntimeException) {
            val message = "Login Error: " + runtimeException.message
            try { Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show() }
            catch (ise: IllegalStateException) { ise.printStackTrace() }
        }
    }

    private fun manualSignInAccount() {
        if (currentFirebaseUser != null) {
            Log.d("ManuLogin", "User null!")
            return
        }
        Log.d("ManuLogin", "Continuing to sign-in.")

        try { globalPreferencesViewModel?.networkPreference?.let { networkPreference ->
                if (!isNetworkAvailable(requireContext(), networkPreference)) {
                    Toast.makeText(requireActivity(),
                        "Internet not available.", Toast.LENGTH_SHORT).show()
                    return
                }
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        val providers = listOf(GoogleBuilder().build())

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResultAccount(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            currentFirebaseUser?.let { user ->
                try { Toast.makeText(requireActivity(), "Welcome ${user.displayName}",
                    Toast.LENGTH_SHORT).show() }
                catch (e: IllegalStateException) { e.printStackTrace() }

                refreshFragment()

                // Generate a Firestore document for the User with default data if needed
                try { buildUserDocument().get().addOnCompleteListener {
                    initAccountCreditListener() } }
                catch (e: Exception) { e.printStackTrace() }
            }
        } else {
            var message = "ERROR: (Error data could not be acquired)."
            result.idpResponse?.error?.let {  error ->
                message = "ERROR " + error.errorCode + ": " + error.message }
            try { Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show() }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    interface OnAdLoadedListener {
        fun onAdLoaded()
    }

    private fun loadRewardedAd(listener: OnAdLoadedListener?) {
        RewardedAd.load(requireActivity(), getString(R.string.ad_rewarded_1),
            AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("RewardedAd", "Ad was loaded.")
                    rewardedAd = ad
                    rewardedAd?.let { rewardedAd ->
                        val options = ServerSideVerificationOptions.Builder()
                            .setCustomData("SAMPLE_CUSTOM_DATA_STRING").build()
                        rewardedAd.setServerSideVerificationOptions(options)

                        val rewardQuantity = rewardedAd.rewardItem.amount
                        obtainCreditsTextView?.let { textView ->
                            textView.setWatchAdsLabelDescription(rewardQuantity)
                            textView.enableAdWatchButton(true)
                        }
                        listener?.onAdLoaded()
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.d("RewardedAd", loadAdError.toString())
                    obtainCreditsTextView?.enableAdWatchButton(false)
                    rewardedAd = null
                }
            })
    }

    fun showRewardedAd() {
        rewardedAd?.let { rewardedAd ->
            rewardedAd.show(requireActivity()) { rewardItem: RewardItem ->
                // Handle the reward.
                Log.d("RewardedAd", "The user earned the reward.")
                val rewardAmount = rewardItem.amount

                try { addCredits(rewardAmount.toLong()) }
                catch (e: Exception) { e.printStackTrace() }

                // A watched ad cannot be re-watched, so chamber another ad
                loadRewardedAd(null)
            }
        } ?: {
            Log.d("RewardedAd", "The rewarded ad wasn't ready yet.")

            try {
                globalPreferencesViewModel?.networkPreference?.let { networkPreference ->
                    if (isNetworkAvailable(requireContext(), networkPreference)) {
                        loadRewardedAd(object: OnAdLoadedListener{
                            override fun onAdLoaded() { showRewardedAd() } })
                    } else { Toast.makeText(requireActivity(),
                        "Internet not available.", Toast.LENGTH_SHORT).show() }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }
}
