package com.tritiumgaming.feature.marketplace.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.marketplace.app.container.MarketplaceContainerProvider
import com.tritiumgaming.feature.marketplace.ui.store.AccountUnlockedPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.store.AccountUnlockedTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogPalettesUiState
import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    private val signInAccountUseCase: SignInAccountUseCase,
    private val signOutAccountUseCase: SignOutAccountUseCase,
    private val deactivateAccountUseCase: DeactivateAccountUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    private val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,

    ): ViewModel() {

    private var observeCreditsJob: Job? = null
    private var observeUnlockedPalettesJob: Job? = null
    private var observeUnlockedTypographiesJob: Job? = null

    private val _marketCatalogPalettesUiState = MutableStateFlow(MarketCatalogPalettesUiState())
    val marketCatalogPalettesUiState = _marketCatalogPalettesUiState.asStateFlow()

    private fun initMarketCatalogPalettes() {
        viewModelScope.launch {
            try {
                val result = getMarketCatalogPalettesUseCase().getOrThrow()

                _marketCatalogPalettesUiState.update {
                    it.copy(
                        palettes = result
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _accountCreditsUiState = MutableStateFlow(AccountCreditsUiState())
    val accountCreditsUiState = _accountCreditsUiState.asStateFlow()
    private fun setAccountUiStateDefault() = _accountCreditsUiState.update { AccountCreditsUiState() }

    private val _accountUnlockedPalettesUiState = MutableStateFlow(AccountUnlockedPalettesUiState())
    val accountUnlockedPalettesUiState = _accountUnlockedPalettesUiState.asStateFlow()
    private fun setUnlockedPalettesUiStatDefault() =
        _accountUnlockedPalettesUiState.update { AccountUnlockedPalettesUiState() }

    private val _accountUnlockedTypographiesUiState = MutableStateFlow(
        AccountUnlockedTypographiesUiState()
    )
    val accountUnlockedTypographiesUiState = _accountUnlockedTypographiesUiState.asStateFlow()
    private fun setUnlockedTypographiesUiStatDefault() =
        _accountUnlockedTypographiesUiState.update { AccountUnlockedTypographiesUiState() }

    private fun startObservingCredits() {
        observeCreditsJob = viewModelScope.launch {
            observeAccountCreditsUseCase()
                .onCompletion {
                    Log.d("AccountViewModel", "observeCreditsJob completed")
                    observeCreditsJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<AccountCredits> ->
                    if(result.isSuccess) {
                        _accountCreditsUiState.update {
                            accountCreditsUiState.value.copy(
                                spentCredits = result.getOrNull()?.spentCredits?.toInt() ?: 0,
                                earnedCredits = result.getOrNull()?.earnedCredits?.toInt() ?: 0
                            )
                        }
                        Log.d("AccountViewModel", "observeCreditsJob updating accountUiState")
                    }
                }
        }
    }

    private fun stopObservingCredits() {
        observeCreditsJob?.cancel()

        setAccountUiStateDefault()

        Log.d("AccountViewModel", "observeCreditsJob stopping")
    }

    private fun startObservingUnlockedPalettes() {
        observeUnlockedPalettesJob = viewModelScope.launch {
            observeAccountUnlockedPalettesUseCase()
                .onCompletion {
                    Log.d("AccountViewModel", "observeCreditsJob completed")
                    observeUnlockedPalettesJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<List<AccountPalette>> ->
                    if(result.isSuccess) {
                        _accountUnlockedPalettesUiState.update {
                            it.copy(
                                unlockedPalettes = result.getOrNull() ?: emptyList()
                            )
                        }
                        Log.d("AccountViewModel", "observeUnlockedPalettesJob updating " +
                                "accountUnlockedPalettesUiState")
                    }
                }
        }
    }

    private fun stopObservingUnlockedPalettes() {
        observeUnlockedPalettesJob?.cancel()

        setUnlockedPalettesUiStatDefault()

        Log.d("AccountViewModel", "observeUnlockedPalettesJob stopping")
    }

    private fun startObservingUnlockedTypographies() {
        observeUnlockedTypographiesJob = viewModelScope.launch {
            observeAccountUnlockedTypographiesUseCase()
                .onCompletion {
                    Log.d("AccountViewModel", "observeCreditsJob completed")
                    observeUnlockedTypographiesJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<List<AccountTypography>> ->
                    if(result.isSuccess) {
                        _accountUnlockedTypographiesUiState.update {
                            accountUnlockedTypographiesUiState.value.copy(
                                unlockedTypographies = result.getOrNull() ?: emptyList()
                            )
                        }
                        Log.d("AccountViewModel", "observeUnlockedTypographiesJob updating " +
                                "accountUnlockedTypographiesUiState")
                    }
                }
        }
    }

    private fun stopObservingUnlockedTypographies() {
        observeUnlockedTypographiesJob?.cancel()

        setUnlockedTypographiesUiStatDefault()

        Log.d("AccountViewModel", "observeUnlockedTypographiesJob stopping")
    }

    private fun startObservingAccount() {
        startObservingCredits()
        startObservingUnlockedPalettes()
        startObservingUnlockedTypographies()
    }

    private fun stopObservingAccount() {
        stopObservingCredits()
        stopObservingUnlockedPalettes()
        stopObservingUnlockedTypographies()
    }

    init {
        startObservingAccount()

        initMarketCatalogPalettes()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MarketplaceContainerProvider).provideMarketplaceContainer()

                val getSignInCredentialsUseCase = container.getSignInCredentialsUseCase
                val signInAccountUseCase = container.signInAccountUseCase
                val signOutAccountUseCase = container.signOutAccountUseCase
                val deactivateAccountUseCase = container.deactivateAccountUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val getMarketCatalogPalettesUseCase = container.getMarketCatalogPalettesUseCase
                val getMarketCatalogTypographiesUseCase = container.getMarketCatalogTypographiesUseCase

                MarketplaceViewModel(
                    getSignInCredentialsUseCase = getSignInCredentialsUseCase,
                    signInAccountUseCase = signInAccountUseCase,
                    signOutAccountUseCase = signOutAccountUseCase,
                    deactivateAccountUseCase = deactivateAccountUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    getMarketCatalogPalettesUseCase = getMarketCatalogPalettesUseCase,
                    getMarketCatalogTypographiesUseCase = getMarketCatalogTypographiesUseCase
                )
            }
        }
    }

}