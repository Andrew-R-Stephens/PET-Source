package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.dto.toNetwork
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAccountRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreAuthRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote.FirestoreUserRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketAgreement
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.MarketCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.toNetwork
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreAccountRepository

class FirestoreAccountRepositoryImpl(
    private val authRemoteDataSource: FirestoreAuthRemoteDataSource,
    private val userRemoteDataSource: FirestoreUserRemoteDataSource,
    private val accountRemoteDataSource: FirestoreAccountRemoteDataSource
): FirestoreAccountRepository {

    override suspend fun addCredits(
        marketCredits: MarketCredits
    ): Result<MarketCredits> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.addCredits(creditAmount)

    }

    override suspend fun removeCredits(
        marketCredits: MarketCredits
    ): Result<MarketCredits> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        val result =
            accountRemoteDataSource.removeCredits(marketCredits.toNetwork())

        return result.map { dto ->
            dto.toDomain()
        }

    }

    //TODO: Get Credit Snapshot Observer

    override suspend fun setMarketplaceAgreementState(
        marketAgreement: MarketAgreement
    ): Result<MarketAgreement> {

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

    override suspend fun addPurchaseDocument(
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