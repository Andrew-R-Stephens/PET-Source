package com.tritiumgaming.feature.marketplace.ui.store.typographies

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
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
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

class MarketplaceTypographiesScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase
): ViewModel() {

    private var observeCreditsJob: Job? = null
    private var observeUnlockedTypographiesJob: Job? = null

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

    private fun startObservingAccount() {
        startObservingCredits()
    }

    init {
        startObservingAccount()
    }

    companion object {

        const val TAG = "MarketplaceTypographyScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val addAccountCreditsUseCase = container.addAccountCreditsUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val purchaseMarketplaceItemUseCase = container.purchaseMarketplaceItemUseCase
                val getMarketCatalogTypographiesUseCase = container.getMarketCatalogTypographiesUseCase
                val getMarketCatalogBundlesUseCase = container.getMarketCatalogBundlesUseCase
                val saveCurrentTypographyUseCase = container.saveCurrentTypographyUseCase

                MarketplaceTypographiesScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    purchaseMarketplaceItemUseCase = purchaseMarketplaceItemUseCase,
                    getMarketCatalogTypographiesUseCase = getMarketCatalogTypographiesUseCase,
                    getMarketCatalogBundlesUseCase = getMarketCatalogBundlesUseCase,
                    saveCurrentTypographyUseCase = saveCurrentTypographyUseCase
                )
            }
        }
    }

}