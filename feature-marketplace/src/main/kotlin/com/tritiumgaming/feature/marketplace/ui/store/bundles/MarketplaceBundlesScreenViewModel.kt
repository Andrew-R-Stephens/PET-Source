package com.tritiumgaming.feature.marketplace.ui.store.bundles

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.common.AccountCreditsUiState
import com.tritiumgaming.feature.marketplace.ui.common.BundlePricingUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogScreenUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.ShopScreenUiItem
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.map

class MarketplaceBundlesScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeMarketplaceAgreementStateUseCase: ObserveMarketplaceAgreementStateUseCase,
    private val setMarketplaceAgreementStateUseCase: SetMarketplaceAgreementStateUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    private val showRewardedAdsUseCase: ShowRewardedAdUseCase,
    getRewardedAdFlowUseCase: GetRewardedAdFlowUseCase
): ViewModel() {

    private val _showAgreementDialog = MutableStateFlow(false)
    val showAgreementDialog = _showAgreementDialog.asStateFlow()

    val marketplaceAgreementUiState: StateFlow<Boolean?> = observeMarketplaceAgreementStateUseCase()
        .map { result ->
            val isShown = result.fold(
                onSuccess = { agreement -> agreement.isAgreementShown },
                onFailure = { null }
            )
            if (isShown == false) {
                _showAgreementDialog.value = true
            }
            isShown
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun setMarketplaceAgreementAccepted() {
        viewModelScope.launch {
            setMarketplaceAgreementStateUseCase(true)
            _showAgreementDialog.value = false
        }
    }

    fun dismissAgreementDialog() {
        _showAgreementDialog.value = false
    }

    fun onAttemptRewardedAd(
        activity: Activity,
        onSuccess: (quantity: Int, type: String) -> Unit = { _, _ -> },
        onFailure: (msg: String) -> Unit = {}
    ) {
        if (marketplaceAgreementUiState.value == false) {
            _showAgreementDialog.value = true
        } else {
            showRewardedAd(activity, onSuccess, onFailure)
        }
    }

    val rewardedAdUiState = getRewardedAdFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RewardedAdState()
        )

    private fun showRewardedAd(
        activity: Activity,
        onSuccess: (quantity: Int, type: String) -> Unit = { _, _ -> },
        onFailure: (msg: String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                showRewardedAdsUseCase(
                    activity = activity,
                    onRewardEarned = { amount, type ->
                        onSuccess(amount, type)
                    },
                    onAdClosed = {
                        //onFailure("onAdClosed")
                    },
                    onAdFailedToShow = { error ->
                        onFailure("onAdFailedToShow: $error")
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure(e.message ?: "Unknown error")
            }
        }
    }

    val accountCreditsUiState: StateFlow<AccountCreditsUiState> = observeAccountCreditsUseCase()
        .map { result ->
            result.fold(
                onSuccess = { credits ->
                    AccountCreditsUiState(
                        credits.spentCredits.toInt(),
                        credits.earnedCredits.toInt()
                    )
                },
                onFailure = { error ->
                    Log.e(TAG, "Error observing account credits: $error")
                    AccountCreditsUiState(0, 0)
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AccountCreditsUiState(0, 0)
        )

    private val _marketCatalogPalettes = MutableStateFlow(emptyList<MarketPalette>())
    private fun initMarketCatalogPalettes() {
        Log.d(TAG, "initMarketCatalogPalettes")
        viewModelScope.launch {
            getMarketCatalogPalettesUseCase()
                .onSuccess { palettes ->
                    Log.d(TAG, "initMarketCatalogPalettes success: $palettes")
                    _marketCatalogPalettes.update { palettes }
                }
                .onFailure { it.printStackTrace() }
        }
    }

    private val _marketCatalogTypographies = MutableStateFlow(emptyList<MarketTypography>())
    private fun initMarketCatalogTypographies() {
        Log.d(TAG, "initMarketCatalogTypographies")
        viewModelScope.launch {
            getMarketCatalogTypographiesUseCase()
                .onSuccess { typographies ->
                    Log.d(TAG, "initMarketCatalogPalettes success: $typographies")
                    _marketCatalogTypographies.update { typographies }
                }
                .onFailure { it.printStackTrace() }
        }
    }

    private val _marketCatalogBundles = MutableStateFlow(emptyList<MarketBundle>())
    private fun initMarketCatalogBundles() {
        Log.d(TAG, "initMarketCatalogBundles")
        viewModelScope.launch {
            getMarketCatalogBundlesUseCase()
                .onSuccess { bundles ->
                    Log.d(TAG, "initMarketCatalogBundles success: $bundles")
                    _marketCatalogBundles.update { bundles }
                }
                .onFailure { it.printStackTrace() }
        }
    }

    fun addCredits(
        credits: Int,
        onSuccess: () -> Unit = {},
        onFailure: (msg: String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                addAccountCreditsUseCase(credits.toLong())
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure(e.message ?: "Unknown error")
            }
        }
    }

    fun obtainItemWithCredits(
        itemId: String,
        itemType: String,
        onSuccess: (msg: String) -> Unit = {},
        onFailure: (msg: String) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        if (marketplaceAgreementUiState.value == false) {
            _showAgreementDialog.value = true
            onComplete()
            return
        }
        viewModelScope.launch {
            try {
                val result = purchaseMarketplaceItemUseCase(
                    CREDITS,
                    itemId,
                    itemType
                )
                if (result.isSuccess) {
                    onSuccess("Purchase successful!")
                    Log.d(TAG, "Purchase successful!")
                } else {
                    val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                    onFailure("Purchase failed: $errorMessage")
                    Log.e(TAG, "Purchase failed: $errorMessage")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            onComplete()
        }
    }

    private val _accountUnlockedPalettes = observeAccountUnlockedPalettesUseCase()
        .map { it.getOrNull() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _accountUnlockedTypographies = observeAccountUnlockedTypographiesUseCase()
        .map { it.getOrNull() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _marketAccountTypographyState = combine(
        _marketCatalogTypographies,
        _accountUnlockedTypographies
    ) { marketTypographies, unlockedTypographies ->
        val unlockedUUIDs = unlockedTypographies?.map { it.uuid } ?: emptyList()
        unlockedUUIDs.forEach {
            Log.d(TAG, "unlockedTypography: $it")
        }

        val updatedPalettes = marketTypographies.map {
            val found = it.uuid in unlockedUUIDs
            Log.d(TAG, "marketTypography: $it | unlocked: $found")
            it.copy(
                unlocked = found
            )
        }

        updatedPalettes
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _marketAccountPaletteState = combine(
        _marketCatalogPalettes,
        _accountUnlockedPalettes
    ) { marketPalettes, unlockedPalettes ->
        val unlockedUUIDs = unlockedPalettes?.map { it.uuid } ?: emptyList()
        unlockedUUIDs.forEach {
            Log.d(TAG, "unlockedPalette: $it")
        }

        val updatedPalettes = marketPalettes.map {
            val found = it.uuid in unlockedUUIDs
            Log.d(TAG, "marketPalette: $it | unlocked: $found")
            it.copy(
                unlocked = found
            )
        }

        updatedPalettes
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    data class PaletteBundleState(
        val uuid: String,
        val bundle: MarketBundle,
        val items: List<MarketPalette>,
        val availability: Boolean,
        val pricing: BundlePricingUiState
    )

    data class TypographyBundleState(
        val uuid: String,
        val bundle: MarketBundle,
        val items: List<MarketTypography>,
        val availability: Boolean,
        val pricing: BundlePricingUiState
    )

    private fun calculateBundlePricing(
        bundleBuyCredits: Long,
        unlockedCount: Int,
        totalCount: Int,
        listPriceTotal: Long,
        lockedCount: Int,
        oneLockedItemPrice: Long?
    ): BundlePricingUiState {
        val isQualified = lockedCount > 1
        val hasDiscount = unlockedCount > 0

        val bundlePrice = if (lockedCount == 1) {
            oneLockedItemPrice ?: bundleBuyCredits
        } else {
            bundleBuyCredits
        }

        val bundleDiscount = listPriceTotal - bundlePrice
        val bundleDiscountRatio = if (listPriceTotal > 0) 1f - (bundlePrice / listPriceTotal.toFloat()) else 0f

        val proratedDiscountRatio = if (totalCount > 0) unlockedCount.toFloat() / totalCount else 0f
        val proratedDiscount = (bundlePrice * proratedDiscountRatio).toLong()
        val finalPrice = bundlePrice - proratedDiscount

        val discountPerItem = if (unlockedCount > 0) {
            (unlockedCount.toFloat() / totalCount) / unlockedCount
        } else 0f

        return BundlePricingUiState(
            listPriceTotal = listPriceTotal,
            bundlePrice = bundlePrice,
            bundleDiscount = bundleDiscount,
            bundleDiscountRatio = bundleDiscountRatio,
            proratedDiscount = proratedDiscount,
            proratedDiscountRatio = proratedDiscountRatio,
            finalPrice = finalPrice,
            isQualified = isQualified,
            hasDiscount = hasDiscount,
            discountPerItem = discountPerItem
        )
    }

    private val _marketPaletteBundlesState = combine(
        _marketCatalogBundles,
        _marketAccountPaletteState
    ) { marketBundles, updatedPalettes ->
        val bundleStates = marketBundles.mapNotNull { marketBundle ->
            val localPalettes = updatedPalettes.filter { palette ->
                palette.uuid in marketBundle.items
            }
            if (localPalettes.isEmpty()) return@mapNotNull null

            val defaultCost = marketBundle.buyCredits
            val totalCount = localPalettes.size
            val unlockedCount = localPalettes.count { it.unlocked }
            val lockedCount = totalCount - unlockedCount
            val listPriceTotal = localPalettes.sumOf { it.buyCredits }
            val oneLockedItemPrice = localPalettes.find { !it.unlocked }?.buyCredits

            val pricing = calculateBundlePricing(
                bundleBuyCredits = defaultCost,
                unlockedCount = unlockedCount,
                totalCount = totalCount,
                listPriceTotal = listPriceTotal,
                lockedCount = lockedCount,
                oneLockedItemPrice = oneLockedItemPrice
            )

            PaletteBundleState(
                uuid = marketBundle.uuid,
                bundle = marketBundle,
                items = localPalettes,
                availability = localPalettes.all { it.unlocked },
                pricing = pricing
            )
        }
        bundleStates
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _marketTypographyBundlesState = combine(
        _marketCatalogBundles,
        _marketAccountTypographyState
    ) { marketBundles, updatedTypographies ->
        val bundleStates = marketBundles.mapNotNull { marketBundle ->
            val localTypographies = updatedTypographies.filter { typography ->
                typography.uuid in marketBundle.items
            }
            if (localTypographies.isEmpty()) return@mapNotNull null

            val defaultCost = marketBundle.buyCredits
            val totalCount = localTypographies.size
            val unlockedCount = localTypographies.count { it.unlocked }
            val lockedCount = totalCount - unlockedCount
            val listPriceTotal = localTypographies.sumOf { it.buyCredits }
            val oneLockedItemPrice = localTypographies.find { !it.unlocked }?.buyCredits

            val pricing = calculateBundlePricing(
                bundleBuyCredits = defaultCost,
                unlockedCount = unlockedCount,
                totalCount = totalCount,
                listPriceTotal = listPriceTotal,
                lockedCount = lockedCount,
                oneLockedItemPrice = oneLockedItemPrice
            )

            TypographyBundleState(
                uuid = marketBundle.uuid,
                bundle = marketBundle,
                items = localTypographies,
                availability = localTypographies.all { it.unlocked },
                pricing = pricing
            )
        }
        bundleStates
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _marketCatalogScreenUiState = combine(
        _marketPaletteBundlesState,
        _marketTypographyBundlesState
    ) { paletteBundles, typographyBundles ->
        val items = mutableListOf<ShopScreenUiItem>()

        if (paletteBundles.isNotEmpty() || typographyBundles.isNotEmpty()) {
            items.add(ShopScreenUiItem.Header("Bundles"))
        }

        paletteBundles.forEach { bundleState ->
            items.add(
                ShopScreenUiItem.PaletteBundle(
                    key = bundleState.uuid,
                    marketBundle = bundleState.bundle,
                    marketPalettes = bundleState.items,
                    unlocked = bundleState.availability,
                    pricing = bundleState.pricing
                )
            )
        }

        typographyBundles.forEach { bundleState ->
            items.add(
                ShopScreenUiItem.TypographyBundle(
                    key = bundleState.uuid,
                    marketBundle = bundleState.bundle,
                    marketTypographies = bundleState.items,
                    unlocked = bundleState.availability,
                    pricing = bundleState.pricing
                )
            )
        }

        MarketCatalogScreenUiState(items = items)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MarketCatalogScreenUiState()
    )
    val marketCatalogScreenUiState = _marketCatalogScreenUiState

    init {
        initMarketCatalogBundles()
        initMarketCatalogPalettes()
        initMarketCatalogTypographies()
    }

    companion object {

        const val TAG = "MarketplaceBundlesScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val addAccountCreditsUseCase = container.addAccountCreditsUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeMarketplaceAgreementStateUseCase = container.observeAccountMarketplaceAgreementStateUseCase
                val setMarketplaceAgreementStateUseCase = container.setAccountMarketplaceAgreementStateUseCase
                val observeAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val purchaseMarketplaceItemUseCase = container.purchaseMarketplaceItemUseCase
                val getMarketCatalogPalettesUseCase = container.getMarketCatalogPalettesUseCase
                val getMarketCatalogTypographiesUseCase = container.getMarketCatalogTypographiesUseCase
                val getMarketCatalogBundlesUseCase = container.getMarketCatalogBundlesUseCase
                val showRewardedAdsUseCase = container.showRewardedAdUseCase
                val getRewardedAdFlowUseCase = container.getRewardedAdFlowUseCase


                MarketplaceBundlesScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeMarketplaceAgreementStateUseCase = observeMarketplaceAgreementStateUseCase,
                    setMarketplaceAgreementStateUseCase = setMarketplaceAgreementStateUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    purchaseMarketplaceItemUseCase = purchaseMarketplaceItemUseCase,
                    getMarketCatalogPalettesUseCase = getMarketCatalogPalettesUseCase,
                    getMarketCatalogTypographiesUseCase = getMarketCatalogTypographiesUseCase,
                    getMarketCatalogBundlesUseCase = getMarketCatalogBundlesUseCase,
                    showRewardedAdsUseCase = showRewardedAdsUseCase,
                    getRewardedAdFlowUseCase = getRewardedAdFlowUseCase
                )
            }
        }
    }

}