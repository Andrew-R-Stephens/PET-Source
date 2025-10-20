package com.tritiumgaming.feature.home.ui.account

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.user.model.AccountCredits
import com.tritiumgaming.shared.core.domain.user.model.AccountPalette
import com.tritiumgaming.shared.core.domain.user.model.AccountTypography
import com.tritiumgaming.shared.core.domain.user.model.SignInOptions
import com.tritiumgaming.shared.core.domain.user.usecase.account.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.account.SignOutAccountUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.core.domain.user.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountScreenViewModel(
    private val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    private val signInAccountUseCase: SignInAccountUseCase,
    private val signOutAccountUseCase: SignOutAccountUseCase,
    private val deactivateAccountUseCase: DeactivateAccountUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    private val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase
): ViewModel() {

    private var observeCreditsJob: Job? = null
    private var observeUnlockedPalettesJob: Job? = null
    private var observeUnlockedTypographiesJob: Job? = null

    private val _accountCreditsUiState = MutableStateFlow(AccountCreditsUiState())
    val accountCreditsUiState = _accountCreditsUiState.asStateFlow()
    private fun setAccountUiStateDefault() = _accountCreditsUiState.update { AccountCreditsUiState() }

    private val _accountUnlockedPalettesUiState = MutableStateFlow(AccountUnlockedPalettesUiState())
    val accountUnlockedPalettesUiState = _accountUnlockedPalettesUiState.asStateFlow()
    private fun setUnlockedPalettesUiStatDefault() =
        _accountUnlockedPalettesUiState.update { AccountUnlockedPalettesUiState() }

    private val _accountUnlockedTypographiesUiState = MutableStateFlow(AccountUnlockedTypographiesUiState())
    val accountUnlockedTypographiesUiState = _accountUnlockedTypographiesUiState.asStateFlow()
    private fun setUnlockedTypographiesUiStatDefault() =
        _accountUnlockedTypographiesUiState.update { AccountUnlockedTypographiesUiState() }

    fun getSignInCredentials(
        signInOption: SignInOptions,
        onComplete: (GetCustomCredentialOption) -> Unit
    ) = viewModelScope.launch {
        getSignInCredentialsUseCase(signInOption).getOrNull()?.let { onComplete(it) } }

    suspend fun signInWithCredentials(
        activity: Activity,
        context: Context,
        credentialOption: GetCustomCredentialOption,
        onComplete: (Boolean) -> Unit
    ) {
        val credentialResponse =
            signInWithCredential(
                activity = activity,
                context = context,
                credentialOption = credentialOption
            ).getOrThrow()

        signInAccount(credentialResponse = credentialResponse) { result -> onComplete(result) }
    }

    fun signInAccount(
        credentialResponse: GetCredentialResponse,
        onComplete: (Boolean) -> Unit = {}
    ) = viewModelScope.launch {
        val result = signInAccountUseCase(credentialResponse).getOrDefault(false)
        onComplete(result)

        if(result) { startObservingAccount() }
    }

    fun signOutAccount(
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        val result = signOutAccountUseCase().getOrDefault(false)
        onComplete(result)

        if (result) { stopObservingAccount() }
    }

    fun deactivateAccount(onComplete: (Boolean) -> Unit) =
        viewModelScope.launch {
            val result = deactivateAccountUseCase().getOrDefault(false)
            onComplete(result)

            if (result) { stopObservingAccount() }
        }

    private fun startObservingCredits() {
        observeCreditsJob?.cancel("Ending old observeCreditsJob")
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
        observeCreditsJob?.cancel("Ending old observeCreditsJob")

        setAccountUiStateDefault()

        Log.d("AccountViewModel", "observeCreditsJob stopping")
    }

    private fun startObservingUnlockedPalettes() {
        observeUnlockedPalettesJob?.cancel("Ending old observeUnlockedPalettesJob")
        observeUnlockedPalettesJob = viewModelScope.launch {
            observeAccountUnlockedPalettesUseCase()
                .onCompletion {
                    Log.d("AccountViewModel", "observeCreditsJob completed")
                    observeUnlockedPalettesJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<List<AccountPalette>> ->
                    if(result.isSuccess) {
                        _accountUnlockedPalettesUiState.update {
                            accountUnlockedPalettesUiState.value.copy(
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
        observeUnlockedPalettesJob?.cancel("Ending old observeUnlockedPalettesJob")
        setUnlockedPalettesUiStatDefault()

        Log.d("AccountViewModel", "observeUnlockedPalettesJob stopping")
    }

    private fun startObservingUnlockedTypographies() {
        observeUnlockedTypographiesJob?.cancel("Ending old observeUnlockedTypographiesJob")
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
        observeUnlockedTypographiesJob?.cancel("Ending old observeUnlockedTypographiesJob")

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

    private suspend fun signInWithCredential(
        activity: Activity,
        context: Context,
        credentialOption: CredentialOption
    ): Result<GetCredentialResponse> = withContext(Dispatchers.IO) {

        Log.e("FirebaseAuth", "Attempting to obtain credentials.")

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()

        try {

            Log.d("FirebaseAuth", "Attempting create credentials manager.")
            val credentialManager = CredentialManager.create(context = context)

            Log.d("FirebaseAuth", "Attempting to obtain credentials.")
            val credentialResponse = credentialManager.getCredential(
                request = request,
                context = activity
            )
            Log.d("FirebaseAuth", "Obtaining credentials successful.")

            Result.success(credentialResponse)

        } catch (e: GetCredentialException) {
            e.printStackTrace()

            Result.failure(Exception("Failure obtaining credentials.", e))
        }

    }

    init {
        startObservingAccount()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                val getSignInCredentialsUseCase = container.getSignInCredentialsUseCase
                val signInAccountUseCase = container.signInAccountUseCase
                val signOutAccountUseCase = container.signOutAccountUseCase
                val deactivateAccountUseCase = container.deactivateAccountUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase

                AccountScreenViewModel(
                    getSignInCredentialsUseCase = getSignInCredentialsUseCase,
                    signInAccountUseCase = signInAccountUseCase,
                    signOutAccountUseCase = signOutAccountUseCase,
                    deactivateAccountUseCase = deactivateAccountUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase
                )
            }
        }
    }

}