package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.AccountTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.toNetwork
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCreditTransaction
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketAgreement
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FirestoreAccountRepositoryImpl(
    private val authRemoteDataSource: FirestoreAuthRemoteDataSource,
    private val userRemoteDataSource: FirestoreUserRemoteDataSource,
    private val accountRemoteDataSource: FirestoreAccountRemoteDataSource
): FirestoreAccountRepository {

    val unlockedPalettes = MutableStateFlow<List<AccountPaletteDto>>(listOf())
    val unlockedTypographies = MutableStateFlow<List<AccountTypographyDto>>(listOf())

    override suspend fun addCredits(
        creditTransaction: AccountCreditTransaction
    ): Result<AccountCredits> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        val result = accountRemoteDataSource.addCredits(creditTransaction.toNetwork())

        return result.map { dto ->
            dto.toDomain()
        }

    }

    override suspend fun removeCredits(
        creditTransaction: AccountCreditTransaction
    ): Result<AccountCredits> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        val result = accountRemoteDataSource.removeCredits(creditTransaction.toNetwork())

        return result.map { dto ->
            dto.toDomain()
        }

    }

    override fun observeCredits(): Flow<Result<AccountCredits>> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return flowOf(Result.failure(Exception("The authorized user's data could not be located!")))

        return accountRemoteDataSource.observeCreditsDocument().map {
            flow -> flow.map { dto -> dto.toDomain() } }

    }

    override suspend fun setMarketplaceAgreementState(
        marketAgreement: AccountMarketAgreement
    ): Result<AccountMarketAgreement> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        val result =
            accountRemoteDataSource.setMarketplaceAgreementState(marketAgreement.toNetwork())

        return result.map { dto ->
            dto.toDomain()
        }

    }

    //TODO: Get Marketplace Agreement State

    /*override suspend fun addUnlockDocument(unlockUUID: String?, type: String): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        if(unlockUUID == null)
            return Result.failure(Exception("No UUID found!"))

        return accountRemoteDataSource.addUnlockedDocument(userDocumentRef, unlockUUID, type)

    }*/

    override suspend fun addUnlockedDocuments (
        unlockUUIDs: ArrayList<String>?,
        type: String
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        if(unlockUUIDs == null || unlockUUIDs.isEmpty())
            return Result.failure(Exception("No UUIDs found!"))

        return accountRemoteDataSource.addUnlockedDocuments(unlockUUIDs, type)

    }

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

    override fun observeUnlockedPalettes(): Flow<Result<List<AccountPalette>>> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return flowOf(Result.failure(Exception("The authorized user's data could not be located!")))

        return accountRemoteDataSource.observeUnlockedPaletteDocuments().map { flow ->
            flow.map { dto -> dto.toDomain() } }

    }

    override fun observeUnlockedTypographies(): Flow<Result<List<AccountTypography>>> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return flowOf(Result.failure(Exception("An authorized user is not currently logged in!")))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return flowOf(Result.failure(Exception("The authorized user's data could not be located!")))

        return accountRemoteDataSource.observeUnlockedTypographyDocuments().map { flow ->
            flow.map { dto -> dto.toDomain() } }

    }

    fun getUnlockedPalettes(): Result<List<AccountPalette>> =
        Result.success(unlockedPalettes.value.toDomain())


    fun getUnlockedTypographies(): Result<List<AccountTypography>> =
        Result.success(unlockedTypographies.value.toDomain())

    //TODO: Get Unlock History Snapshot Observer
    /*try {
        unlockHistoryCollection.addSnapshotListener(EventListener {
                value: QuerySnapshot?, _: FirebaseFirestoreException? ->
            if (value == null) { return@EventListener }

            for (documentSnapshot in value.documents) {
                val uuid = documentSnapshot.reference.id
                globalPreferencesViewModel.colorThemeControl.let { control ->
                    val customTheme = control.getThemeByUUID(uuid)
                    customTheme.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                revalidateBundles()
                revalidateThemes()
            }.start()
        })
    } catch (e: Exception) { e.printStackTrace() }*/

    override suspend fun addPurchasedDocument(
        orderID: String
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.addPurchaseDocument(orderID)

    }

}