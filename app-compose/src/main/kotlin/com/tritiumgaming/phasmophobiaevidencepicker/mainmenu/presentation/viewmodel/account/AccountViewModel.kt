package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.account

import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    private val signInAccountUseCase: SignInAccountUseCase,
    private val signOutAccountUseCase: SignOutAccountUseCase,
    private val deactivateAccountUseCase: DeactivateAccountUseCase,
    private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase
): ViewModel() {

    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState = _accountUiState.asStateFlow()
    fun getAccountUiState() = accountUiState.value

    fun getSignInCredentials(
        signInOption: SignInOptions,
        onComplete: (GetCustomCredentialOption) -> Unit
    ) = viewModelScope.launch {
        getSignInCredentialsUseCase(signInOption).getOrNull()?.let { onComplete(it) } }

    fun signInAccount(
        credentialResponse: GetCredentialResponse,
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        onComplete(signInAccountUseCase(credentialResponse).getOrDefault(false)) }

    fun signOutAccount(onComplete: (Boolean) -> Unit) =
        viewModelScope.launch {
            onComplete(signOutAccountUseCase().getOrDefault(false)) }

    fun deactivateAccount(onComplete: (Boolean) -> Unit) =
        viewModelScope.launch {
            onComplete(deactivateAccountUseCase().getOrDefault(false)) }

    init {
        viewModelScope.launch {
            observeAccountCreditsUseCase().collect { result: Result<AccountCredits> ->
                if(result.isSuccess) {
                    _accountUiState.update {
                        accountUiState.value.copy(
                            spentCredits = result.getOrNull()?.spentCredits?.toInt() ?: 0,
                            earnedCredits = result.getOrNull()?.earnedCredits?.toInt() ?: 0
                        )
                    }
                }
            }
        }
    }

    class OnboardingFactory(
        private val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
        private val signInAccountUseCase: SignInAccountUseCase,
        private val signOutAccountUseCase: SignOutAccountUseCase,
        private val deactivateAccountUseCase: DeactivateAccountUseCase,
        private val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AccountViewModel(
                    getSignInCredentialsUseCase = getSignInCredentialsUseCase,
                    signInAccountUseCase = signInAccountUseCase,
                    signOutAccountUseCase = signOutAccountUseCase,
                    deactivateAccountUseCase = deactivateAccountUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val container =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).coreContainer

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

    data class AccountUiState(
        val spentCredits: Int = 0,
        val earnedCredits: Int = 0
    )

}