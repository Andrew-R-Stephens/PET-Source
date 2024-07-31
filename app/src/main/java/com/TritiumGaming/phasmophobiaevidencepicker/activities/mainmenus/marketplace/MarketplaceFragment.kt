package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.MarketplaceListLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.MarketBundleView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.MarketItemView.MarketItemOnPurchaseListener
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.MarketThemeView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.bundle.MarketBundleModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.theme.MarketThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.bundles.FirestoreMerchandiseBundle.Companion.getBundleWhere
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.themes.FirestoreMerchandiseThemes.Companion.getThemesWhere
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.buildUserDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.addCredits
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.creditsDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.removeCredits
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountPreferences
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountPreferences.Companion.preferencesDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountPreferences.Companion.setMarketplaceAgreementState
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory.Companion.addUnlockDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory.Companion.addUnlockedDocuments
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory.Companion.unlockHistoryCollection
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.firestore.OnFirestoreProcessListener
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.TritiumGaming.phasmophobiaevidencepicker.views.account.AccountObtainCreditsView
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.EquipConfirmationDialog
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.MarketplaceDialog
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Locale


class MarketplaceFragment : MainMenuFirebaseFragment() {

    private var masterItemsList: LinearLayout? = null

    private var bundleList: MarketplaceListLayout? = null
    private var themePrestigeList: MarketplaceListLayout? = null
    private var themeEventList: MarketplaceListLayout? = null
    private var themeCommunityList: MarketplaceListLayout? = null

    private var accountCreditsTextView: AppCompatTextView? = null
    private var marketplaceErrorTextView: AppCompatTextView? = null

    private var obtainCreditsTextView: AccountObtainCreditsView? = null

    private var confirmationDialog: ComposeView? = null
    private var equipDialog: ComposeView? = null

    private var marketProgressBar: ProgressBar? = null

    private var rewardedAd: RewardedAd? = null

    private var scopeIO: CoroutineScope? = CoroutineScope(Dispatchers.IO)
    private var scopeMain: CoroutineScope? = CoroutineScope(Dispatchers.Main)

    private var launchAgreementDialogJob: Job? =  scopeIO?.launch{
        initAgreementDialog()
    }

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
            view.findViewById<SignInButton>(R.id.settings_account_login_button)
        val watchAdButton = view.findViewById<AppCompatButton>(R.id.button_ad_watch)
        val buyButton = view.findViewById<AppCompatButton>(R.id.settings_account_buy_button)

        confirmationDialog = view.findViewById(R.id.confirmationDialog)
        equipDialog = view.findViewById(R.id.equipDialog)

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

        buyButton.setOnClickListener { v -> this.gotoBillingMarketplace(v) }

        // CANCEL BUTTON
        backButton.setOnClickListener { v: View? ->
            try { v?.let{ findNavController(it).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        if (currentFirebaseUser == null) {
            marketProgressBar?.visibility = GONE
            // marketplaceErrorTextView?.visibility = VISIBLE
        }

        obtainCreditsTextView?.enableAdWatchButton(false)
        scopeMain?.launch {
            loadRewardedAd(null)
        }?.start()

        scopeIO?.launch {
            initAccountCreditListener()
        }?.start()

        scopeIO?.launch {
            populateAvailableItems()
        }?.start()

        launchAgreementDialogJob?.start()
    }

    private fun gotoBillingMarketplace(v: View) {
        try {
            findNavController(v).navigate(R.id.action_marketplaceFragment_to_marketplaceBillingFragment)
        } catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    private fun gotoSettingsMarketplace(v: View) {
        try {
            findNavController(v).navigate(R.id.action_marketplaceFragment_to_appSettingsFragment)
        } catch (e: IllegalArgumentException) { e.printStackTrace() }
    }

    private fun initAccountCreditListener() {
        try {
            creditsDocument.get()
                .addOnSuccessListener { task: DocumentSnapshot ->
                    val userCredits =
                        task.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                    accountCreditsTextView?.text = userCredits.toString()
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could not get user's account credit count!")
                    e.printStackTrace()
                }

            creditsDocument.addSnapshotListener { documentSnapshot: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                if (documentSnapshot == null) { return@addSnapshotListener }
                val userCredits = documentSnapshot.get(
                    FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                accountCreditsTextView?.text = userCredits.toString()
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun initAgreementDialog() {
        try {
            preferencesDocument.get()
                .addOnSuccessListener { task: DocumentSnapshot ->
                    val shown = task.get(
                        FirestoreAccountPreferences.FIELD_MARKETPLACE_AGREEMENT_SHOWN,
                        Boolean::class.java)
                    if(shown == false) {
                        confirmationDialog?.setContent {
                            MarketplaceDialog(
                                onConfirm = { confirmationDialog?.visibility = GONE }
                            )
                        }
                        confirmationDialog?.visibility = VISIBLE
                        try {
                            setMarketplaceAgreementState(true)
                        }
                        catch (e: Exception) { e.printStackTrace() }
                        Log.d("Firestore", "Showing user agreement.")
                    } else {
                        Log.d("Firestore", "User was already shown agreement.")
                    }
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could get user's account preferences!")
                    e.printStackTrace()
                }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun populateAvailableItems() {
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

                    populateBundlesList()
                    populateThemesLists()

                    testToast("Test Success 0")

                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could not populate user's unlock history!")
                    marketProgressBar?.visibility = GONE
                    marketplaceErrorTextView?.visibility = VISIBLE
                    e.printStackTrace()

                    testToast("Test Failure 0")

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

                CoroutineScope(Dispatchers.Main).launch {
                    revalidateBundles()
                    revalidateThemes()
                }.start()
            })
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun revalidateBundles() {
        bundleList?.validateChildren()
    }

    private fun revalidateThemes() {
        themePrestigeList?.validateChildren()
        themeEventList?.validateChildren()
        themeCommunityList?.validateChildren()
    }

    private fun populateBundlesList() {
        try { bundleList = MarketplaceListLayout(requireContext()) }
        catch (e: IllegalStateException) { e.printStackTrace() }

        bundleList?.let { list ->
            list.setLabel("Theme Bundles")
            list.showLabel(GONE)

            try {
                masterItemsList?.addView(bundleList)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }


            val listener: OnFirestoreProcessListener =
            object : OnFirestoreProcessListener() {
                var thrown: Boolean = false
                var labelShown: Boolean = true

                override fun onFailure() {
                    if (!thrown) thrown = true

                    labelShown = false

                    marketProgressBar?.visibility = GONE
                    marketplaceErrorTextView?.visibility = VISIBLE

                    testToast("Test Failure 1")

                    try {
                        Toast.makeText(requireActivity(),
                            getString(R.string.alert_marketplace_access_failure),
                            Toast.LENGTH_SHORT).show()
                    }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }

                override fun onSuccess() {
                    if (!thrown) thrown = true

                    if (labelShown) {
                        bundleList?.showLabel(VISIBLE)
                    }

                    marketProgressBar?.visibility = GONE

                    testToast("Test Success 1")
                }

                override fun onComplete() {
                    if (!thrown) thrown = true

                    if (labelShown) {
                        bundleList?.showLabel(VISIBLE)
                    }

                    marketProgressBar?.visibility = GONE

                    testToast("Test Complete 1")
                }
            }

            try {
                globalPreferencesViewModel?.networkPreference?.let { networkPreference ->
                    if (isNetworkAvailable(requireContext(), networkPreference)) {
                        addBundles(list, listener = listener)
                    }
                    else {
                        listener.onFailure()

                        Toast.makeText(requireActivity(),
                            getString(R.string.alert_internet_unavailable), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }

    }

    private fun populateThemesLists() {
        try {
            themePrestigeList = MarketplaceListLayout(requireContext())
            themeEventList = MarketplaceListLayout(requireContext())
            themeCommunityList = MarketplaceListLayout(requireContext())
        } catch (e: IllegalStateException) { e.printStackTrace() }

        if (themePrestigeList == null || themeEventList == null ||
            themeCommunityList == null) { return }

        themePrestigeList?.setLabel("Prestige Themes")
        themeEventList?.setLabel("Event Themes")
        themeCommunityList?.setLabel("Community Themes")

        masterItemsList?.let { masterList ->

            try { masterList.addView(themePrestigeList) }
            catch (e: IllegalStateException) { e.printStackTrace() }
            try { masterList.addView(themeEventList) }
            catch (e: IllegalStateException) { e.printStackTrace() }
            try { masterList.addView(themeCommunityList) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        val processCompleteListener: OnFirestoreProcessListener =
            object : OnFirestoreProcessListener() {
                var thrown: Boolean = false
                var labelShown: Boolean = false

                override fun onFailure() {
                    if (!thrown) thrown = true

                    marketProgressBar?.visibility = GONE
                    marketplaceErrorTextView?.visibility = VISIBLE

                    try {
                        Toast.makeText(requireActivity(),
                            getString(R.string.alert_marketplace_access_failure),
                            Toast.LENGTH_SHORT).show()
                    }
                    catch (e: IllegalStateException) { e.printStackTrace() }
                }

                override fun onSuccess() {
                    if (!thrown) thrown = true
                    labelShown = true

                    testToast("Test Success 2")
                }

                override fun onComplete() {
                    if (!thrown) thrown = true
                    marketProgressBar?.visibility = GONE

                    testToast("Test Complete 2")
                }
            }

        try {
            if (!isNetworkAvailable(requireContext(), globalPreferencesViewModel!!.networkPreference)) {
                Toast.makeText(requireActivity(),
                    getString(R.string.alert_internet_unavailable),
                    Toast.LENGTH_SHORT).show()

                processCompleteListener.onFailure()

                return
            }

            themePrestigeList?.let { list ->
                addThemes(list, "group", "Prestige",
                    "priority", Query.Direction.ASCENDING, processCompleteListener)
            }
            themeEventList?.let { list ->
                addThemes(list, "group", "Event",
                    null, null, processCompleteListener)
            }
            themeCommunityList?.let { list ->
                addThemes(list, "group", "Community",
                    null, null, processCompleteListener)
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun addThemes(
        list: MarketplaceListLayout, field: String = "group", value: String? = null,
        orderField: String? = null, order: Query.Direction? = null,
        listener: OnFirestoreProcessListener
    ) {
        var query: Task<QuerySnapshot>? = null
        try { query = getThemesWhere(field, value, orderField, order) }
        catch (e: Exception) { e.printStackTrace() }

        if (query == null) {
            listener.onFailure()
            testToast("Test Failed. Theme query null")

            return
        }

        query
            .addOnSuccessListener(OnSuccessListener { snapshot: QuerySnapshot ->
                listener.onSuccess()
                for (documentSnapshot in snapshot.documents) {
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Theme document snapshot DNE.")

                        testToast("Test Failed. Theme doc DNE")

                    } else {
                        val uuid = documentSnapshot.reference.id
                        try {

                            val tempTheme = MarketThemeModel()
                            documentSnapshot.data?.let { map ->
                                tempTheme.name = map["name"] as String?
                                tempTheme.group = map["group"] as String?
                                tempTheme.buyCredits = map["buyCredits"] as Long
                            }

                            testToast("Test Success. Created Theme Model")

                            globalPreferencesViewModel?.let { globalPreferencesViewModel ->
                                val customTheme =
                                    globalPreferencesViewModel.colorThemeControl.getThemeByUUID(uuid)

                                val marketSingleThemeFinal =
                                    MarketThemeModel(uuid, tempTheme, customTheme)

                                try {
                                    val themeView = buildThemeView(marketSingleThemeFinal)

                                    list.addView(themeView)
                                    list.requestLayout()
                                    list.invalidate()

                                    testToast("Test Success. Created Theme View")

                                } catch (e: IllegalStateException) {
                                    e.printStackTrace()

                                    testToast("Test Failed. Error creating Theme View")
                                }
                            }
                        }
                        catch (e: Exception) {
                            Log.d("Firestore", "Error CREATING Theme Model!")
                            e.printStackTrace()

                            testToast("Test Failed. Error creating Theme Model")
                        }
                    }
                }
            })
            .addOnFailureListener {
            listener.onFailure()

                testToast("Test Failure. Create Theme Model/Views")
            }
            .addOnCompleteListener {
                listener.onComplete()
                if (list.childCount <= 1) { list.visibility = GONE }
                else { list.showLabel(VISIBLE) }

                testToast("Test Complete. Create Theme Model/Views")
            }
    }

    private fun addBundles(
        list: MarketplaceListLayout, listener: OnFirestoreProcessListener) {

        var query: Task<QuerySnapshot>? = null
        try { query = getBundleWhere() }
        catch (e: Exception) { e.printStackTrace() }

        if (query == null) {
            listener.onFailure()

            testToast("Test Failed. Bundle query null")

            return
        }

        query
            .addOnSuccessListener(OnSuccessListener { snapshot: QuerySnapshot ->
                listener.onSuccess()

                for (documentSnapshot in snapshot.documents) {
                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Bundle document snapshot DNE.")

                        testToast("Test Failed. Bundle doc DNE")

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

                        try {

                            val tempBundle = MarketBundleModel()
                            documentSnapshot.data?.let { map ->
                                tempBundle.name = map["name"] as String?
                                tempBundle.buyCredits = map["buyCredits"] as Long
                            }

                            testToast("Test Success. Created Temp Bundle Model")

                            val customThemes = ArrayList<ThemeModel>()
                            for (themeID in themeIDs) {
                                globalPreferencesViewModel?.colorThemeControl?.let { control ->
                                    customThemes.add(control.getThemeByUUID(themeID)) } }
                            val finalBundle = MarketBundleModel(docId, tempBundle, customThemes)

                            try {
                                val bundleView =
                                    buildBundleView(finalBundle)

                                bundleView?.let { view ->
                                    list.addView(view)
                                    list.requestLayout()
                                    list.invalidate()
                                }

                                testToast("Test Success. Created Bundle View")

                            } catch (e: IllegalStateException) {
                                e.printStackTrace()
                                testToast("Test Failed. Error creating Bundle View")
                            }

                        }
                        catch (e: Exception) {
                            Log.d("Firestore", "Error CREATING Bundle Modal!")
                            e.printStackTrace()
                            testToast("Test Failure. Create Bundle Model")
                        }
                    }
                }

            })
            .addOnFailureListener {
                listener.onFailure()

                testToast("Test Failure. Create Bundle Model/Views")

            }
            .addOnCompleteListener {
                if (list.childCount <= 1) { list.visibility = GONE }
                else { list.showLabel(VISIBLE) }
                listener.onComplete()

                testToast("Test Complete. Create Bundle Model/Views")
            }
    }

    private fun buildBundleView(bundleModel: MarketBundleModel): MarketBundleView? {

        val bundleView: MarketBundleView
        try {
            bundleView = MarketBundleView(requireContext(), null) }
        catch (e: IllegalStateException) { e.printStackTrace()
            return null
        }

        bundleView.bundle = bundleModel

        bundleView.onPurchaseListener = object:MarketItemOnPurchaseListener() {
            override fun onPurchase() {

                val buyButtonCallback: OnFirestoreProcessListener =

                    object : OnFirestoreProcessListener() {

                        override fun onSuccess() {
                            val uuids: ArrayList<String> = ArrayList()

                            bundleModel.themes?.forEach { bundleTheme ->
                                bundleTheme.iD?.let { iD -> uuids.add(iD) } }

                            try {
                                addUnlockedDocuments(uuids, "Theme Bundle",
                                    object : OnFirestoreProcessListener() {

                                        override fun onSuccess() {
                                            onPurchaseSuccess(bundleModel)

                                            testToast("Test Success 3")
                                        }

                                        override fun onFailure() {
                                            Log.d("Firestore",
                                                "Could not add/retrieve purchase document!")
                                            try {
                                                Toast.makeText(requireActivity(),
                                                    getString(R.string.alert_marketplace_transaction_failure),
                                                    Toast.LENGTH_SHORT).show()
                                            }
                                            catch (e: IllegalStateException) { e.printStackTrace() }
                                            testToast("Test Failure 3")
                                        }

                                    })
                            } catch (e: Exception) { e.printStackTrace() }

                            try {
                                Toast.makeText(requireActivity(),
                                    getString(R.string.alert_marketplace_purchase_success_skin), Toast.LENGTH_SHORT).show()
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }
                        }

                        override fun onFailure() {
                            try {
                                Toast.makeText(requireActivity(),
                                    getString(R.string.alert_marketplace_purchase_failure_skin_credits),
                                    Toast.LENGTH_SHORT).show()
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }
                        }

                        override fun onComplete() {
                            Log.d("Bundle", "Bundle process completed.")

                            testToast("Test Complete 3")
                        }
                    }

                try {
                    removeCredits(bundleView.creditCost, buyButtonCallback)
                }
                catch (e: Exception) { throw RuntimeException(e) }
            }
        }

        return bundleView
    }

    @Throws(IllegalStateException::class)
    private fun buildThemeView(singleThemeModel: MarketThemeModel): MarketThemeView {

        val themeView = MarketThemeView(
            ContextThemeWrapper(requireContext(), singleThemeModel.style),
            null, singleThemeModel.style)

        themeView.themeModel = singleThemeModel

        themeView.onPurchaseListener = object:MarketItemOnPurchaseListener() {
            override fun onPurchase() {
                val buyButtonCallback: OnFirestoreProcessListener =
                    object : OnFirestoreProcessListener() {

                        override fun onSuccess() {

                            val purchaseListener: OnFirestoreProcessListener =

                                object : OnFirestoreProcessListener() {
                                    override fun onSuccess() {
                                        onPurchaseSuccess(singleThemeModel)
                                        testToast("Test Success 4")
                                    }

                                    override fun onFailure() {
                                        Log.d("Firestore",
                                            "Could not add/retrieve purchase document!")
                                    }
                                }

                            try {
                                addUnlockDocument(singleThemeModel.uuid.toString(),
                                    "Single Theme", purchaseListener) }
                            catch (e: Exception) { e.printStackTrace() }

                            try {
                                Toast.makeText(
                                    requireActivity(), getString(R.string.alert_marketplace_purchase_success_skin), Toast.LENGTH_SHORT).show()
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }
                        }

                        override fun onFailure() {
                            try {
                                Toast.makeText(
                                    requireActivity(), getString(R.string.alert_marketplace_purchase_failure_skin_credits),
                                    Toast.LENGTH_SHORT).show()
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }
                        }

                        override fun onComplete() {
                            Log.d("Bundle", "Single theme process completed.")

                            testToast("Test Complete 4")
                        }
                    }
                try {
                    removeCredits(themeView.creditCost, buyButtonCallback)
                }
                catch (e: Exception) { throw RuntimeException(e) }
            }
        }

        return themeView
    }

    private fun onPurchaseSuccess(bundleModel: MarketBundleModel) {
        equipDialog?.let { dialog ->
            dialog.setContent {
                EquipConfirmationDialog(
                    onConfirm = { gotoSettingsMarketplace(dialog) },
                    targetTitle = String.format(
                        Locale.getDefault(), getString(R.string.marketplace_purchase_equip),
                        bundleModel.name),
                    timeout = 3000L
                )
            }
        }
    }

    private fun onPurchaseSuccess(themeModel: MarketThemeModel) {
        equipDialog?.let { dialog ->
            dialog.setContent {
                EquipConfirmationDialog(
                    onConfirm = { gotoSettingsMarketplace(dialog) },
                    targetTitle = String.format(
                        Locale.getDefault(), getString(R.string.marketplace_purchase_equip),
                        themeModel.name),
                    timeout = 3000L
                )
            }
        }
    }


    override fun onSignInAccountSuccess() {
        refreshFragment()

        // Generate a Firestore document for the User with default data if needed
        try { buildUserDocument().get().addOnCompleteListener {
            initAccountCreditListener() } }
        catch (e: Exception) { e.printStackTrace() }
    }

    override fun onSignOutAccountSuccess() {
        refreshFragment()
    }

    override fun onDeleteAccountSuccess() {
        TODO("Not yet implemented")
    }

    interface OnAdLoadedListener {
        fun onAdLoaded()
    }

    private fun loadRewardedAd(listener: OnAdLoadedListener?) {
        try {
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
        } catch (e: IllegalStateException) {
            testToast("Failed to load rewarded ad.")
        }
    }

    fun showRewardedAd() {
        rewardedAd?.let { rewardedAd ->
            rewardedAd.show(requireActivity()) { rewardItem: RewardItem ->
                // Handle the reward.
                Log.d("RewardedAd", "The user earned the reward.")

                // Accredit account
                try { addCredits(rewardItem.amount.toLong()) }
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
                    } else {
                        Toast.makeText(
                            requireActivity(), getString(R.string.alert_internet_unavailable), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    fun testToast(message: String) {
        val enabled = false
        if(enabled) {
            scopeMain?.launch {
                try {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }?.start()
        }
    }
}
