package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.account

import android.util.Log
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.SignInOptions
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    private val signInAccountUseCase: SignInAccountUseCase,
    private val signOutAccountUseCase: SignOutAccountUseCase,
    private val deactivateAccountUseCase: DeactivateAccountUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase
): ViewModel() {

    private var observeCreditsJob: Job? = null

    private val _accountUiState = MutableStateFlow(AccountCreditsUiState())
    val accountUiState = _accountUiState.asStateFlow()
    private fun setAccountUiStateDefault() = _accountUiState.update { AccountCreditsUiState() }

    fun getSignInCredentials(
        signInOption: SignInOptions,
        onComplete: (GetCustomCredentialOption) -> Unit
    ) = viewModelScope.launch {
        getSignInCredentialsUseCase(signInOption).getOrNull()?.let { onComplete(it) } }

    fun signInAccount(
        credentialResponse: GetCredentialResponse,
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        val result = signInAccountUseCase(credentialResponse).getOrDefault(false)
        onComplete(result)

        if(result) { startObservingCredits() }
    }

    fun signOutAccount(
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        val result = signOutAccountUseCase().getOrDefault(false)
        onComplete(result)

        if (result) { stopObservingCredits() }
    }

    fun deactivateAccount(onComplete: (Boolean) -> Unit) =
        viewModelScope.launch {
            val result = deactivateAccountUseCase().getOrDefault(false)
            onComplete(result)

            if (result) { stopObservingCredits() }
        }

    fun startObservingCredits() {
        observeCreditsJob = viewModelScope.launch {
            observeAccountCreditsUseCase()
                .onCompletion {
                    Log.d("AccountViewModel", "observeCreditsJob completed")
                    observeCreditsJob?.cancel() }
                .catch { it.printStackTrace() }
                .collect { result: Result<AccountCredits> ->
                    if(result.isSuccess) {
                        _accountUiState.update {
                            accountUiState.value.copy(
                                spentCredits = result.getOrNull()?.spentCredits?.toInt() ?: 0,
                                earnedCredits = result.getOrNull()?.earnedCredits?.toInt() ?: 0
                            )
                        }
                        Log.d("AccountViewModel", "observeCreditsJob updating accountUiState")
                    }
                }
        }
    }

    fun stopObservingCredits() {
        observeCreditsJob?.cancel()

        setAccountUiStateDefault()
    }

    init {
        startObservingCredits()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val container =
                    (this[APPLICATION_KEY] as PETApplication).coreContainer

                val getSignInCredentialsUseCase = container.getSignInCredentialsUseCase
                val signInAccountUseCase = container.signInAccountUseCase
                val signOutAccountUseCase = container.signOutAccountUseCase
                val deactivateAccountUseCase = container.deactivateAccountUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase

                AccountViewModel(
                    getSignInCredentialsUseCase = getSignInCredentialsUseCase,
                    signInAccountUseCase = signInAccountUseCase,
                    signOutAccountUseCase = signOutAccountUseCase,
                    deactivateAccountUseCase = deactivateAccountUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase
                )
            }
        }
    }

}