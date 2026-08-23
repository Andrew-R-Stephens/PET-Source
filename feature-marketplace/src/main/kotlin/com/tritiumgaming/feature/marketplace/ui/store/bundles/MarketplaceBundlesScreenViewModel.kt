package com.tritiumgaming.feature.marketplace.ui.store.bundles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.common.AccountCreditsUiState
import com.tritiumgaming.feature.marketplace.ui.common.AccountUnlockedPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.common.AccountUnlockedTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogScreenUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.ShopScreenUiItem
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MarketplaceBundlesScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase
): ViewModel() {

    private var observeCreditsJob: Job? = null
    private var observeUnlockedPalettesJob: Job? = null
    private var observeUnlockedTypographiesJob: Job? = null

    private val _accountUnlockedPalettes = observeAccountUnlockedPalettesUseCase()
        .map { it.getOrNull() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
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
                    Log.d(TAG, "Purchase failed: $errorMessage")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            onComplete()
        }
    }

    private val _accountCreditsUiState = MutableStateFlow(AccountCreditsUiState())
    val accountCreditsUiState = _accountCreditsUiState.asStateFlow()

    private val _accountUnlockedPalettesUiState = MutableStateFlow(AccountUnlockedPalettesUiState())

    private val _accountUnlockedTypographiesUiState = MutableStateFlow(
        AccountUnlockedTypographiesUiState()
    )
    val accountUnlockedTypographiesUiState = _accountUnlockedTypographiesUiState.asStateFlow()

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
        val unlocked: Boolean
    )

    private val _marketPaletteBundlesState = combine(
        _marketCatalogBundles,
        _marketAccountPaletteState
    ) { marketBundles, unlockedPalettes ->

        val bundleStates = marketBundles.map { marketBundle ->
            val localPalettes = unlockedPalettes.filter { palette ->
                palette.uuid in marketBundle.items.map { item -> item }
            }

            BundleState(
                uuid = marketBundle.uuid,
                bundle = marketBundle,
                items = localPalettes,
                unlocked = localPalettes.all { it.unlocked }
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
                    unlocked = bundleState.unlocked
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

    private fun startObservingCredits() {
        observeCreditsJob = viewModelScope.launch {
            observeAccountCreditsUseCase()
                .onCompletion {
                    Log.d(TAG, "observeCreditsJob completed")
                    observeCreditsJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<AccountCredits> ->
                    if(result.isSuccess) {
                        result.getOrNull()?.let { result ->
                            _accountCreditsUiState.update {
                                accountCreditsUiState.value.copy(
                                    spentCredits = result.spentCredits.toInt(),
                                    earnedCredits = result.earnedCredits.toInt()
                                )
                            }
                        }
                        Log.d(TAG, "observeCreditsJob updating accountUiState")
                    }
                }
        }
    }

    private fun startObservingUnlockedPalettes() {
        observeUnlockedPalettesJob = viewModelScope.launch {
            observeAccountUnlockedPalettesUseCase()
                .onCompletion {
                    Log.d(TAG, "observeCreditsJob completed")
                    observeUnlockedPalettesJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<List<AccountPalette>> ->
                    if(result.isSuccess) {
                        _accountUnlockedPalettesUiState.update {
                            it.copy(
                                unlockedPalettes = result.getOrNull() ?: emptyList()
                            )
                        }
                        Log.d(TAG, "observeUnlockedPalettesJob updating " +
                                "accountUnlockedPalettesUiState")
                    }
                }
        }
        observeUnlockedPalettesJob?.start()
    }

    private fun startObservingUnlockedTypographies() {
        observeUnlockedTypographiesJob = viewModelScope.launch {
            observeAccountUnlockedTypographiesUseCase()
                .onCompletion {
                    Log.d(TAG, "observeCreditsJob completed")
                    observeUnlockedTypographiesJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<List<AccountTypography>> ->
                    if(result.isSuccess) {
                        _accountUnlockedTypographiesUiState.update {
                            accountUnlockedTypographiesUiState.value.copy(
                                unlockedTypographies = result.getOrNull() ?: emptyList()
                            )
                        }
                        Log.d(TAG, "observeUnlockedTypographiesJob updating " +
                                "accountUnlockedTypographiesUiState")
                    }
                }
        }
    }

    private fun startObservingAccount() {
        startObservingCredits()
        startObservingUnlockedPalettes()
        startObservingUnlockedTypographies()
    }

    init {
        startObservingAccount()

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

                MarketplaceBundlesScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    purchaseMarketplaceItemUseCase = purchaseMarketplaceItemUseCase,
                    getMarketCatalogPalettesUseCase = getMarketCatalogPalettesUseCase,
                    getMarketCatalogTypographiesUseCase = getMarketCatalogTypographiesUseCase,
                    getMarketCatalogBundlesUseCase = getMarketCatalogBundlesUseCase
                )
            }
        }
    }

}