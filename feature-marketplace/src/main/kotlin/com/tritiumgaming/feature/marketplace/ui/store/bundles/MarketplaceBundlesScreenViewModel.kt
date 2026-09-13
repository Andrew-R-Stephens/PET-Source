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
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogScreenUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.ShopScreenUiItem
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceBundlesScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    private val showRewardedAdsUseCase: ShowRewardedAdUseCase,
    getRewardedAdFlowUseCase: GetRewardedAdFlowUseCase
): ViewModel() {

    val rewardedAdUiState = getRewardedAdFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RewardedAdState()
        )

    fun showRewardedAd(
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

    private val _marketCatalogTypographiesUiState = MutableStateFlow(MarketCatalogTypographiesUiState())
    private fun initMarketCatalogTypographies() {
        viewModelScope.launch {
            try {
                val result = getMarketCatalogTypographiesUseCase()

                _marketCatalogTypographiesUiState.update {
                    it.copy(
                        typographies = result
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    data class BundleState(
        val uuid: String,
        val bundle: MarketBundle,
        val items: List<MarketPalette>,
        val availability: Boolean,
        val buyCost: Long = bundle.buyCredits,
        val originalCost: Long,
        val discountRatio: Float,
        val discount: Long = 0L,
        val discountedCost: Long = 0L,
    )

    private val _marketPaletteBundlesState = combine(
        _marketCatalogBundles,
        _marketAccountPaletteState
    ) { marketBundles, unlockedPalettes ->
        val bundleStates = marketBundles.map { marketBundle ->
            val defaultCost = marketBundle.buyCredits

            val localPalettes = unlockedPalettes.filter { palette ->
                palette.uuid in marketBundle.items.map { item -> item }
            }

            val originalCost = localPalettes.sumOf { it.buyCredits }
            val originalDiscountRatio = (originalCost.toFloat() / defaultCost)

            val palettesLocked = localPalettes.filterNot { palette -> palette.unlocked }
            val amountLocked = palettesLocked.size

            val amountUnlocked = localPalettes.count { it.unlocked }
            val ratioUnlocked = if(amountLocked > 1) {
                amountUnlocked.toFloat() / marketBundle.items.size.toFloat()
            } else 0f
            val unlockedDiscount = (ratioUnlocked * defaultCost).toLong()

            BundleState(
                uuid = marketBundle.uuid,
                bundle = marketBundle,
                items = localPalettes,
                availability = localPalettes.all { it.unlocked },
                originalCost = originalCost,
                buyCost =
                    if(palettesLocked.size == 1) palettesLocked.firstOrNull()?.buyCredits?: 0
                    else defaultCost,
                discountRatio = originalDiscountRatio,
                discount = unlockedDiscount,
                discountedCost = defaultCost - unlockedDiscount,
            )
        }

        bundleStates

    }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _marketCatalogScreenUiState = _marketPaletteBundlesState.map { paletteBundles ->
        val items = mutableListOf<ShopScreenUiItem>()

        if(paletteBundles.isNotEmpty()) {
            items.add(
                ShopScreenUiItem.Header("Bundles")
            )
        }
        paletteBundles.forEach { bundleState ->
            items.add(
                ShopScreenUiItem.PaletteBundle(
                    key = bundleState.uuid,
                    marketBundle = bundleState.bundle,
                    marketPalettes = bundleState.items,
                    unlocked = bundleState.availability,
                    buyCost = bundleState.buyCost,
                    originalCost = bundleState.originalCost,
                    discountRatio = bundleState.discountRatio,
                    discount = bundleState.discount,
                    discountedCost = bundleState.discountedCost
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