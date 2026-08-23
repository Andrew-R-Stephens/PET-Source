package com.tritiumgaming.feature.marketplace.ui.store.palettes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.common.AccountCreditsUiState
import com.tritiumgaming.feature.marketplace.ui.common.AccountUnlockedPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.common.AccountUnlockedTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogBillablesUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogScreenUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.ShopScreenUiItem
import com.tritiumgaming.feature.marketplace.ui.store.bundles.MarketplaceBundlesScreenViewModel
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.LEGAL_TENDER
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources
import com.tritiumgaming.shared.data.market.typography.mappers.asUuid
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
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
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.emptyList

class MarketplacePaletteScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
): ViewModel() {

    private var observeCreditsJob: Job? = null
    private var observeUnlockedPalettesJob: Job? = null

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
                    onFailure("Purchase failed: ${result.exceptionOrNull()?.message}")
                    Log.e(TAG, "Purchase failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            onComplete()
        }
    }

    private val _accountCreditsUiState = MutableStateFlow(AccountCreditsUiState())
    val accountCreditsUiState = _accountCreditsUiState.asStateFlow()
    private fun setAccountUiStateDefault() = _accountCreditsUiState.update { AccountCreditsUiState() }

    private val _accountUnlockedPalettesUiState = MutableStateFlow(AccountUnlockedPalettesUiState())
    private fun setUnlockedPalettesUiStatDefault() =
        _accountUnlockedPalettesUiState.update { AccountUnlockedPalettesUiState() }

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
    val marketPaletteBundlesState = _marketPaletteBundlesState

    private val _marketCatalogScreenUiState = MutableStateFlow(MarketCatalogScreenUiState())
    val marketCatalogScreenUiState = combine(
        _marketPaletteBundlesState,
        _marketAccountPaletteState
    ){ paletteBundles, unlockedPalettes ->

        val grouped = unlockedPalettes
            .filterNot { it.priority == -1L }
            .sortedBy { it.priority }
            .groupBy { it.group ?: "" }

        val items = mutableListOf<ShopScreenUiItem>()

        /*items.add(
            ShopScreenUiItem.Header("Theme Bundles")
        )
        paletteBundles.forEach { bundleState ->
            items.add(
                ShopScreenUiItem.PaletteBundle(
                    key = bundleState.uuid,
                    marketBundle = bundleState.bundle,
                    marketPalettes = bundleState.items,
                    unlocked = bundleState.unlocked
                )
            )
        }*/

        grouped.forEach { (groupName, groupPalettes) ->
            if (groupName.isNotEmpty()) {
                items.add(
                    ShopScreenUiItem.Header(groupName)
                )
            }
            groupPalettes.forEach { marketPalette ->
                marketPalette.group?.let { group ->
                    if(group.isNotBlank()) {
                        items.add(
                            ShopScreenUiItem.Palette(
                                marketPalette = marketPalette,
                                paletteResource = marketPalette.palette?.toPaletteResource()
                                    ?: ClassicPalette
                            )
                        )
                    }
                }
            }
        }

        MarketCatalogScreenUiState(items = items)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MarketCatalogScreenUiState()
    )

    fun updatePalette(
        palette: PaletteResources.PaletteType,
        onComplete: () -> Unit = {}
    ) {
        val uuid = palette.asUuid()
        Log.d(TAG, "updatePalette: $uuid")
        viewModelScope.launch {
            saveCurrentPaletteUseCase(uuid)
            onComplete()
        }
    }

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

    private fun stopObservingCredits() {
        observeCreditsJob?.cancel()

        setAccountUiStateDefault()

        Log.d(TAG, "observeCreditsJob stopping")
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

    private fun stopObservingUnlockedPalettes() {
        observeUnlockedPalettesJob?.cancel()

        setUnlockedPalettesUiStatDefault()

        Log.d(TAG, "observeUnlockedPalettesJob stopping")
    }

    private fun startObservingAccount() {
        startObservingCredits()
        startObservingUnlockedPalettes()
    }

    private fun stopObservingAccount() {
        stopObservingCredits()
        stopObservingUnlockedPalettes()
    }

    init {
        startObservingAccount()

        initMarketCatalogBundles()
        initMarketCatalogPalettes()
    }

    companion object {

        const val TAG = "MarketplacePaletteScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val addAccountCreditsUseCase = container.addAccountCreditsUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val purchaseMarketplaceItemUseCase = container.purchaseMarketplaceItemUseCase
                val getMarketCatalogPalettesUseCase = container.getMarketCatalogPalettesUseCase
                val getMarketCatalogBundlesUseCase = container.getMarketCatalogBundlesUseCase
                val saveCurrentPaletteUseCase = container.saveCurrentPaletteUseCase

                MarketplacePaletteScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    purchaseMarketplaceItemUseCase = purchaseMarketplaceItemUseCase,
                    getMarketCatalogPalettesUseCase = getMarketCatalogPalettesUseCase,
                    getMarketCatalogBundlesUseCase = getMarketCatalogBundlesUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase,
                )
            }
        }
    }

}