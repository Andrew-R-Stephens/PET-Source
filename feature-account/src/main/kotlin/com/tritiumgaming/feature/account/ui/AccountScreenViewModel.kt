package com.tritiumgaming.feature.account.ui

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
import com.tritiumgaming.feature.account.app.container.AccountContainerProvider
import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.model.SignInOptions
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
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
    private val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
): ViewModel() {

    private val _accountCreditsUiState = observeAccountCreditsUseCase()
        .map { result ->
            if(result.isSuccess) {
                Log.d("AccountViewModel", "Credits observation updated successfully")
            }
            result.exceptionOrNull()?.let { error ->
                Log.e("AccountViewModel", "Error observing credits: ${error.message}")
            }

            result.getOrNull()?.let { credits ->
                AccountCreditsUiState(
                    spentCredits = credits.spentCredits.toInt(),
                    earnedCredits = credits.earnedCredits.toInt()
                )
            } ?: AccountCreditsUiState(-1, -1)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AccountCreditsUiState(-2, -2)
        )
    val accountCreditsUiState = _accountCreditsUiState

    private val _accountUnlockedPalettesUiState = observeAccountUnlockedPalettesUseCase()
        .map { result ->
            AccountUnlockedPalettesUiState(unlockedPalettes = result.getOrNull() ?: emptyList())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AccountUnlockedPalettesUiState()
        )
    val accountUnlockedPalettesUiState = _accountUnlockedPalettesUiState

    private val _accountUnlockedTypographiesUiState = observeAccountUnlockedTypographiesUseCase()
        .map { result ->
            AccountUnlockedTypographiesUiState(unlockedTypographies = result.getOrNull() ?: emptyList())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AccountUnlockedTypographiesUiState()
        )
    val accountUnlockedTypographiesUiState = _accountUnlockedTypographiesUiState

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
    }

    fun signOutAccount(
        onComplete: (Boolean) -> Unit
    ) = viewModelScope.launch {
        val result = signOutAccountUseCase().getOrDefault(false)
        onComplete(result)

        if(result) {
            saveCurrentPaletteUseCase(LocalDefaultPalette.asUuid())
        }
    }

    fun deactivateAccount(onComplete: (Boolean) -> Unit) =
        viewModelScope.launch {
            val result = deactivateAccountUseCase().getOrDefault(false)
            onComplete(result)

            if(result) {
                saveCurrentPaletteUseCase(LocalDefaultPalette.asUuid())
            }
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
        Log.d("AccountViewModel", "AccountScreenViewModel initialized")
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as AccountContainerProvider).provideAccountContainer()

                val getSignInCredentialsUseCase = container.getSignInCredentialsUseCase
                val signInAccountUseCase = container.signInAccountUseCase
                val signOutAccountUseCase = container.signOutAccountUseCase
                val deactivateAccountUseCase = container.deactivateAccountUseCase
                val observeAccountCreditsUseCase = container.observeAccountCreditsUseCase
                val observeAccountUnlockedPalettesUseCase = container.observeAccountUnlockedPalettesUseCase
                val observeAccountUnlockedTypographiesUseCase = container.observeAccountUnlockedTypographiesUseCase
                val saveCurrentPaletteUseCase = container.saveCurrentPaletteUseCase

                AccountScreenViewModel(
                    getSignInCredentialsUseCase = getSignInCredentialsUseCase,
                    signInAccountUseCase = signInAccountUseCase,
                    signOutAccountUseCase = signOutAccountUseCase,
                    deactivateAccountUseCase = deactivateAccountUseCase,
                    observeAccountCreditsUseCase = observeAccountCreditsUseCase,
                    observeAccountUnlockedPalettesUseCase = observeAccountUnlockedPalettesUseCase,
                    observeAccountUnlockedTypographiesUseCase = observeAccountUnlockedTypographiesUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase
                )
            }
        }
    }

}