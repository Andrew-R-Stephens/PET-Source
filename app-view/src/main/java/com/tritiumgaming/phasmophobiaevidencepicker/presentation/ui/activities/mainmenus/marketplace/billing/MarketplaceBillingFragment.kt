package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.marketplace.billing

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.billable.MarketMicroTransactionModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.microtransactions.billables.FirestoreMicrotransactionBillables
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.microtransactions.billables.FirestoreMicrotransactionBillables.Companion.getBillablesWhere
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.addCredits
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.creditsDocument
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.transactions.types.FirestorePurchaseHistory.Companion.addPurchaseDocument
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.OnFirestoreProcessListener
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.NavHeaderLayout
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.common.collect.ImmutableList
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.marketplace.billing.view.MarketBillableView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.marketplace.views.MarketplaceListLayout

class MarketplaceBillingFragment : MainMenuFirebaseFragment() {
    private var billingClient: BillingClient? = null

    private var accountCreditsTextView: AppCompatTextView? = null
    private var requestLoginLayout: ConstraintLayout? = null

    private var marketItemsMasterList: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_marketplace_billables, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val backButton = navHeaderLayout.findViewById<AppCompatImageView>(R.id.button_left)

        requestLoginLayout = view.findViewById(R.id.constraint_requestlogin)
        accountCreditsTextView = view.findViewById(R.id.label_credits_actual)
        marketItemsMasterList = view.findViewById(R.id.list_marketplace_items)

        initAccountView()

        marketItemsMasterList?.visibility = View.VISIBLE

        backButton.setOnClickListener { v: View? ->
            try { v?.let { findNavController(v).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        requireActivity().runOnUiThread { Thread { this.initAccountCreditListener() }.start() }

        initBillingClient()
    }

    override fun initViewModels() {
    }

    private fun initAccountView() {
        try {
            requestLoginLayout?.visibility = currentFirebaseUser?.let { View.GONE } ?: View.VISIBLE
        } catch (e: Exception) { e.printStackTrace()}
    }

    private fun initAccountCreditListener() {
        try {
            val creditDoc = creditsDocument
            creditDoc.get()
                .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    val creditsEarned = task.result.get(
                        FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                    accountCreditsTextView?.text = creditsEarned.toString() }

            creditDoc.addSnapshotListener {
                documentSnapshot: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                documentSnapshot?.let {
                    val creditsEarned = documentSnapshot.get(
                        FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                    accountCreditsTextView?.text = creditsEarned.toString() }
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun initBillingClient() {
        createBillingClient()
        connectToGooglePlayBilling()
    }

    private fun createBillingClient() {
        billingClient = BillingClient.newBuilder(requireContext())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        Log.d("Billing", "Pending purchases will be handled.")
    }

    private fun connectToGooglePlayBilling() {
        Log.d("Billing", "Attempting to setup Billing...")

        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("Billing", "Billing setup finished successfully!")
                    // The BillingClient is ready. You can query purchases here.
                    try { queryMicroTransactionItems() }
                    catch (e: Exception) { e.printStackTrace() }
                } else { Log.d("Billing", ("Billing setup unsuccessful. " +
                            "Code: ${billingResult.responseCode} " +
                            "Debug: ${billingResult.debugMessage}").trimIndent()) }

                billingClient?.queryPurchasesAsync(QueryPurchasesParams.newBuilder()
                        .setProductType(BillingClient.ProductType.INAPP).build()
                ) { result: BillingResult, list: List<Purchase>? ->
                    handlePendingPurchases(result, list) }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("Billing", "Billing service disconnected.")

                connectToGooglePlayBilling()
            }
        })
    }

    // Google Billing Library
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult: BillingResult, list: List<Purchase>? ->
            this.handlePendingPurchases(billingResult, list) }

    private fun handlePendingPurchases(billingResult: BillingResult, list: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK &&
            list?.isNotEmpty() == true) {

            confirmPurchases(list)

        } else { Log.d("Billing", "Purchase error: Purchases list is empty") }
    }

    private fun confirmPurchases(list: List<Purchase>) {
        Log.d("Billing", "Processing OK purchase")

        for (purchase in list) {
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED &&
                !purchase.isAcknowledged
            ) {
                try {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireActivity(),
                            getString(R.string.alert_marketplace_purchase_success_generic),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: IllegalStateException) { e.printStackTrace() }

                val productString = StringBuilder()
                for (product in purchase.products) {
                    productString.append(product).append("\n")
                }

                Log.d("Billing", "Purchase successful: $productString")

                //Consume item process
                consumePurchase(purchase)

            } else {
                Log.d("Billing", "Pending error: " + purchase.purchaseState)
            }
        }
    }

    private fun consumePurchase(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        val listener =
            ConsumeResponseListener { billingResult: BillingResult, _: String? ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                    var billableCollection: CollectionReference? = null
                    try {
                        billableCollection = FirestoreMicrotransactionBillables.billableCollection }
                    catch (e: Exception) { e.printStackTrace() }

                    //if (billableCollection == null) { return@ConsumeResponseListener }

                    val purchasesString = StringBuilder()
                    for (p in purchase.products) {
                        try {
                            billableCollection?.let {
                                billableCollection.whereEqualTo("product_id", p)
                                    .addSnapshotListener {
                                        snapshot: QuerySnapshot?, _: FirebaseFirestoreException? ->

                                        val firestoreProcessListener: OnFirestoreProcessListener =
                                            object : OnFirestoreProcessListener() {
                                            override fun onSuccess() {
                                                Log.d("Billable",
                                                    "Reward added successfully!") }
                                            override fun onFailure() {
                                                Log.d("Billable",
                                                    "Reward adding failed!") } }

                                        snapshot?.documents?.let { snapshotDocuments ->
                                        for (documentSnapshot in snapshotDocuments) {
                                            val rewardItem = documentSnapshot.get(
                                                "reward_item", String::class.java)
                                            val rewardAmount = documentSnapshot.get(
                                                "reward_amount", Long::class.java)

                                            if (rewardItem == null || rewardAmount == null) {
                                                return@addSnapshotListener }

                                            when (rewardItem) {
                                                "credit" -> {
                                                    try {
                                                        addCredits(rewardAmount, null)
                                                        addPurchaseDocument(
                                                            documentSnapshot.reference,
                                                            purchase.orderId,
                                                            firestoreProcessListener)
                                                    } catch (e: Exception) { e.printStackTrace() }
                                                }

                                                "" -> {
                                                    Log.d("Billable", "Reward type is empty")
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        } catch (e: Exception) { e.printStackTrace() }
                        purchasesString.append(p).append(" ")
                    }

                    Log.d("Billing", "Purchase consumed: $purchasesString")
                } else {
                    Log.d("Billing",
                        "Purchase error: ${purchase.products[0]} -> " +
                                "Error: [${billingResult.responseCode}] " +
                                "${billingResult.debugMessage} -> ")
                }
            }

        billingClient?.consumeAsync(consumeParams, listener)
    }

    @Throws(Exception::class)
    fun queryMicroTransactionItems() {
        Log.d("Billing", "Obtaining list of Marketplace items from database...")

        var billableQuery: Task<QuerySnapshot>? = null
        try { billableQuery = getBillablesWhere(
            "type", "credits", "tier", Query.Direction.ASCENDING)
        } catch (e: Exception) { e.printStackTrace() }

        if (billableQuery == null) {
            Log.d("Billing", "Microtransaction query snapshot DNE.")
            return
        }

        billableQuery
            .addOnSuccessListener(OnSuccessListener { snapshot: QuerySnapshot ->
                val productsQueryList: MutableList<QueryProductDetailsParams.Product> = ArrayList()
                for (documentSnapshot in snapshot.documents) {
                    if (!documentSnapshot.exists()) { continue }

                    val purchaseId =
                        documentSnapshot.get("product_id", String::class.java) ?: continue

                    val activeStatus =
                        documentSnapshot.get("active_status", Boolean::class.java)
                    if (activeStatus == null || !activeStatus) { continue }

                    Log.d("Billing", "Building ProductDetail for $purchaseId")
                    val product =
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(purchaseId)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()

                    productsQueryList.add(product)
                }

                Log.d("Billing",
                    "Finished querying database for MTX Billable items. " +
                            "${productsQueryList.size} results. Now querying Play Console for " +
                            "matching in-app products.")

                val queryProductDetailsParams =
                    QueryProductDetailsParams.newBuilder()
                        .setProductList(productsQueryList)
                        .build()
                billingClient?.queryProductDetailsAsync(queryProductDetailsParams) {
                    billingResult: BillingResult, productDetailsList: List<ProductDetails?> ->
                    // check if billingResult process returned productDetailsList
                    if (billingResult.responseCode ==
                        BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                        Log.d("Billing",
                            "Finished querying Play Console in-app products. " +
                                    "${productDetailsList.size} results.")
                        try {
                            requireActivity().runOnUiThread {
                                buildMtxProductsList(productDetailsList) }
                        } catch (e: Exception) { e.printStackTrace() }
                    }
                }
            })
            .addOnFailureListener { e: Exception ->
                Log.d("Billing", "Microtransaction query failed!")
                e.printStackTrace()
            }.addOnCompleteListener { task: Task<QuerySnapshot>? -> stopProgressBarLoop() }
    }

    private fun stopProgressBarLoop() {
        try { val progressbar = requireView().findViewById<ProgressBar>(R.id.market_progressbar)
            progressbar.visibility = View.GONE
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun buildMtxProductsList(productDetailsList: List<ProductDetails?>) {
        val marketplaceList =
            MarketplaceListLayout(context, null)

        for (productDetails in productDetailsList) {
            val mtxItem =
                MarketMicroTransactionModel(productDetails!!)
            Log.d("Billing", "Adding $mtxItem")

            try {
                val marketplaceMtxView = buildMicroTransactionView(mtxItem)
                marketItemsMasterList?.addView(marketplaceMtxView)
            } catch (e: Exception) {
                Log.d("Billing", "Failed! ")
                e.printStackTrace()
            }
        }

        marketItemsMasterList?.let { marketItemsMasterList ->
            if (marketItemsMasterList.childCount > 1) {
                marketplaceList.visibility = View.VISIBLE
                marketItemsMasterList.addView(marketplaceList)
            }
        }
    }

    private fun buildMicroTransactionView(mtxItem: MarketMicroTransactionModel): MarketBillableView {
        val microTransationView = MarketBillableView(requireContext(), null)

        microTransationView.setBillableItem(mtxItem)
        microTransationView.setBuyButtonListener {
            val item = microTransationView.getBillableItem() ?: return@setBuyButtonListener
            val productDetail = item.productDetails

            val productDetailsParamsList = ImmutableList.of(
                ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetail)
                    .build())

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            // Launch the billing flow
            billingClient?.launchBillingFlow(requireActivity(), billingFlowParams)
        }
        return microTransationView
    }

    /*override fun onSignInAccountSuccess() {
        TODO("Not yet implemented")
    }

    override fun onSignOutAccountSuccess() {
        TODO("Not yet implemented")
    }

    override fun onDeleteAccountSuccess() {
        TODO("Not yet implemented")
    }*/

}


