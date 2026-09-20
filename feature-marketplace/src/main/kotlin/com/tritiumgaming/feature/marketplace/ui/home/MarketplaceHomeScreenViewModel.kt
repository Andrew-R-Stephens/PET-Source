package com.tritiumgaming.feature.marketplace.ui.home

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.common.AccountCreditsUiState
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceHomeScreenViewModel(
    private val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeMarketplaceAgreementStateUseCase: ObserveMarketplaceAgreementStateUseCase,
    private val setMarketplaceAgreementStateUseCase: SetMarketplaceAgreementStateUseCase,
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
                _showAgreementDialog.update { true }
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
            _showAgreementDialog.update { false }
        }
    }

    fun dismissAgreementDialog() {
        _showAgreementDialog.update { false }
    }

    fun onAttemptRewardedAd(
        activity: Activity,
        onSuccess: (quantity: Int, type: String) -> Unit = { _, _ -> },
        onFailure: (msg: String) -> Unit = {}
    ) {
        showRewardedAd(activity, onSuccess, onFailure)
    }

    fun onAttemptNavigate(
        route: String,
        onNavigate: (String) -> Unit
    ) {
        if (marketplaceAgreementUiState.value == false) {
            _showAgreementDialog.update { true }
        } else {
            onNavigate(route)
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

    companion object {

        const val TAG = "MarketplaceHomeScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val addAccountCreditsUseCase = container.addAccountCreditsUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeMarketplaceAgreementStateUseCase = container.observeAccountMarketplaceAgreementStateUseCase
                val setMarketplaceAgreementStateUseCase = container.setAccountMarketplaceAgreementStateUseCase
                val showRewardedAdsUseCase = container.showRewardedAdUseCase
                val getRewardedAdFlowUseCase = container.getRewardedAdFlowUseCase

                MarketplaceHomeScreenViewModel(
                    addAccountCreditsUseCase = addAccountCreditsUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeMarketplaceAgreementStateUseCase = observeMarketplaceAgreementStateUseCase,
                    setMarketplaceAgreementStateUseCase = setMarketplaceAgreementStateUseCase,
                    showRewardedAdsUseCase = showRewardedAdsUseCase,
                    getRewardedAdFlowUseCase = getRewardedAdFlowUseCase
                )
            }
        }
    }

}
