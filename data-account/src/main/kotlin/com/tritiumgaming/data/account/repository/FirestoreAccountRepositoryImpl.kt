package com.tritiumgaming.data.account.repository

import android.util.Log
import com.tritiumgaming.data.account.dto.AccountCreditsDto
import com.tritiumgaming.data.account.dto.AccountMarketAgreementDto
import com.tritiumgaming.data.account.dto.AccountTypographyDto
import com.tritiumgaming.data.account.dto.toDomain
import com.tritiumgaming.data.account.dto.toNetwork
import com.tritiumgaming.data.account.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.data.account.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.model.AccountMarketAgreement
import com.tritiumgaming.shared.data.account.model.AccountPalette
import com.tritiumgaming.shared.data.account.model.AccountTypography
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

class FirestoreAccountRepositoryImpl(
    private val authRemoteDataSource: FirestoreAuthRemoteDataSource,
    private val userRemoteDataSource: FirestoreUserRemoteDataSource,
    private val accountRemoteDataSource: FirestoreAccountRemoteDataSource,
    private val scope: CoroutineScope
): FirestoreAccountRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val creditsFlow: Flow<Result<AccountCredits>> by lazy {
        authRemoteDataSource.observeAuthState()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))
                } else {
                    accountRemoteDataSource.observeCreditsDocument()
                        .map { flow: Result<AccountCreditsDto> ->
                            flow.map { dto -> dto.toDomain() }
                        }
                }
            }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val marketplaceAgreementFlow: Flow<Result<AccountMarketAgreement>> by lazy {
        authRemoteDataSource.observeAuthState()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))
                } else {
                    accountRemoteDataSource.observeMarketplaceAgreementDocument()
                        .map { flow: Result<AccountMarketAgreementDto> ->
                            flow.map { dto -> dto.toDomain() }
                        }
                }
            }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val unlockedPalettesFlow: Flow<Result<List<AccountPalette>>> by lazy {
        authRemoteDataSource.observeAuthState()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))
                } else {
                    accountRemoteDataSource.observeUnlockedPaletteDocuments().map { flow ->
                        flow.map { dto -> dto.toDomain() }
                    }
                }
            }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val unlockedTypographiesFlow: Flow<Result<List<AccountTypography>>> by lazy {
        authRemoteDataSource.observeAuthState()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))
                } else {
                    accountRemoteDataSource.observeUnlockedTypographyDocuments().map { flow ->
                        flow.map { dto -> dto.toDomain() }
                    }
                }
            }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                replay = 1
            )
    }

    override suspend fun addCredits(
        creditTransaction: AccountCreditTransaction
    ): Result<Boolean> {
        authRemoteDataSource.currentAuthUser?.uid ?: return Result.failure(
            Exception("An authorized user is not currently logged in!"))

        return accountRemoteDataSource.addCredits(creditTransaction.toNetwork())
    }

    override fun observeCredits(): Flow<Result<AccountCredits>> = creditsFlow

    override fun observeMarketplaceAgreementState(): Flow<Result<AccountMarketAgreement>> = marketplaceAgreementFlow

    override suspend fun purchaseItemWithCredits(
        itemId: String,
        itemType: String
    ): Result<Boolean> = accountRemoteDataSource.purchaseItemWithCredits(itemId, itemType)

    override suspend fun purchaseItemWithLegalTender(
        itemId: String,
        itemType: String
    ): Result<Boolean> {

        authRemoteDataSource.currentAuthUser?.uid ?: return Result.failure(
            Exception("An authorized user is not currently logged in!"))

        return accountRemoteDataSource.purchaseItemWithCredits(itemId, itemType)
    }

    override suspend fun setMarketplaceAgreementState(
        marketAgreement: AccountMarketAgreement
    ): Result<AccountMarketAgreement> {
        authRemoteDataSource.currentAuthUser?.uid ?: return Result.failure(
            Exception("An authorized user is not currently logged in!"))

        val result: Result<AccountMarketAgreementDto> =
            accountRemoteDataSource.setMarketplaceAgreementState(marketAgreement.toNetwork())

        return result.map { dto -> dto.toDomain() }
    }

    //TODO: Get Marketplace Agreement State

    override suspend fun fetchUnlockedPalettes(
        forceUpdate: Boolean
    ): Result<List<AccountPalette>> {

        val result = accountRemoteDataSource.fetchUnlockedPaletteDocuments()

        if(result.isFailure) {
            Log.e("Firestore", "Error fetching unlocked palettes", result.exceptionOrNull())
        } else {
            Log.d("Firestore", "Success fetching unlocked palettes (size: ${result.getOrNull()?.size})")
        }

        return result.map { dto -> dto.toDomain() }

    }

    override suspend fun fetchUnlockedTypographies(
        forceUpdate: Boolean
    ): Result<List<AccountTypography>> {

        val result = accountRemoteDataSource.fetchUnlockedTypographyDocuments()

        if(result.isFailure) {
            Log.e("Firestore", "Error fetching unlocked typographies", result.exceptionOrNull())
        } else {
            Log.d("Firestore", "Success fetching unlocked typographies")
        }

        return result.map { dto -> dto.toDomain() }

    }

    override fun observeUnlockedPalettes(): Flow<Result<List<AccountPalette>>> =
        unlockedPalettesFlow

    override fun observeUnlockedTypographies(): Flow<Result<List<AccountTypography>>> =
        unlockedTypographiesFlow
}