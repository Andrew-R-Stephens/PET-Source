package com.tritiumgaming.feature.marketplace.ui.store.typographies

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.common.AccountCreditsUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.model.MarketplaceExchangeMedium.CREDITS
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentTypographyUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceTypographiesScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeMarketplaceAgreementStateUseCase: ObserveMarketplaceAgreementStateUseCase,
    private val setMarketplaceAgreementStateUseCase: SetMarketplaceAgreementStateUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    private val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    private val showRewardedAdsUseCase: ShowRewardedAdUseCase? = null,
    getRewardedAdFlowUseCase: GetRewardedAdFlowUseCase? = null
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

    val rewardedAdUiState = getRewardedAdFlowUseCase?.invoke()
        ?.stateIn(
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
                showRewardedAdsUseCase?.invoke(
                    activity = activity,
                    onRewardEarned = { amount, type ->
                        onSuccess(amount, type)
                    },
                    onAdClosed = { },
                    onAdFailedToShow = { error ->
                        onFailure(error.toString())
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure(e.message ?: "")
            }
        }
    }

    private var observeCreditsJob: Job? = null

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
                onFailure(e.message ?: "")
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
                    val errorMessage = result.exceptionOrNull()?.message ?: ""
                    onFailure(errorMessage)
                    Log.e(TAG, "Purchase failed: $errorMessage")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure(e.message ?: "")
            }
            onComplete()
        }
    }

    private val _accountUnlockedTypographies = observeAccountUnlockedTypographiesUseCase()
        .map { it.getOrNull() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _marketCatalogTypographies = MutableStateFlow(emptyList<MarketTypography>())
    private fun initMarketCatalogTypographies() {
        viewModelScope.launch {
            getMarketCatalogTypographiesUseCase()
                .onSuccess { typographies ->
                    _marketCatalogTypographies.update { typographies }
                }
                .onFailure { it.printStackTrace() }
        }
    }

    val marketCatalogTypographiesUiState: StateFlow<MarketCatalogTypographiesUiState> = combine(
        _marketCatalogTypographies,
        _accountUnlockedTypographies
    ) { catalog, unlockedList ->
        val unlockedUUIDs = unlockedList?.map { it.uuid } ?: emptyList()
        val updated = catalog.map { item ->
            item.copy(unlocked = item.uuid in unlockedUUIDs)
        }
        MarketCatalogTypographiesUiState(typographies = updated)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MarketCatalogTypographiesUiState()
    )

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
                        result.getOrNull()?.let { res ->
                            _accountCreditsUiState.update {
                                accountCreditsUiState.value.copy(
                                    spentCredits = res.spentCredits.toInt(),
                                    earnedCredits = res.earnedCredits.toInt()
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
        initMarketCatalogTypographies()
    }

    companion object {

        const val TAG = "MarketplaceTypographiesScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val addAccountCreditsUseCase = container.addAccountCreditsUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeMarketplaceAgreementStateUseCase = container.observeAccountMarketplaceAgreementStateUseCase
                val setMarketplaceAgreementStateUseCase = container.setAccountMarketplaceAgreementStateUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val purchaseMarketplaceItemUseCase = container.purchaseMarketplaceItemUseCase
                val getMarketCatalogTypographiesUseCase = container.getMarketCatalogTypographiesUseCase
                val getMarketCatalogBundlesUseCase = container.getMarketCatalogBundlesUseCase
                val saveCurrentTypographyUseCase = container.saveCurrentTypographyUseCase
                val showRewardedAdsUseCase = container.showRewardedAdUseCase
                val getRewardedAdFlowUseCase = container.getRewardedAdFlowUseCase

                MarketplaceTypographiesScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeMarketplaceAgreementStateUseCase = observeMarketplaceAgreementStateUseCase,
                    setMarketplaceAgreementStateUseCase = setMarketplaceAgreementStateUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    purchaseMarketplaceItemUseCase = purchaseMarketplaceItemUseCase,
                    getMarketCatalogTypographiesUseCase = getMarketCatalogTypographiesUseCase,
                    getMarketCatalogBundlesUseCase = getMarketCatalogBundlesUseCase,
                    saveCurrentTypographyUseCase = saveCurrentTypographyUseCase,
                    showRewardedAdsUseCase = showRewardedAdsUseCase,
                    getRewardedAdFlowUseCase = getRewardedAdFlowUseCase
                )
            }
        }
    }

}
