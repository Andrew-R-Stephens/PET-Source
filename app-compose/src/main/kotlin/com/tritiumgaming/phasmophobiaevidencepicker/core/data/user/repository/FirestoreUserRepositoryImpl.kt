package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote2.FirestoreAccountRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote2.FirestoreAuthRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.source.remote2.FirestoreUserRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.repository.FirestoreUserRepository

class FirestoreUserRepositoryImpl(
    private val authRemoteDataSource: FirestoreAuthRemoteDataSource,
    private val userRemoteDataSource: FirestoreUserRemoteDataSource,
    private val accountRemoteDataSource: FirestoreAccountRemoteDataSource
): FirestoreUserRepository {

    override suspend fun addCredits(
        creditAmount: Long
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.addCredits(userDocumentRef, creditAmount)

    }

    override suspend fun removeCredits(
        creditAmount: Long
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.removeCredits(userDocumentRef, creditAmount)

    }

    override suspend fun setMarketplaceAgreementState(
        shown: Boolean
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.setMarketplaceAgreementState(userDocumentRef, shown)

    }

    override suspend fun addUnlockDocument(unlockUUID: String?, type: String): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        if(unlockUUID == null)
            return Result.failure(Exception("No UUID found!"))

        return accountRemoteDataSource.addUnlockedDocument(userDocumentRef, unlockUUID, type)

    }


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

        return accountRemoteDataSource.addUnlockedDocuments(userDocumentRef, unlockUUIDs, type)

    }

    override suspend fun addPurchaseDocument(
        orderID: String
    ): Result<String> {

        val uid: String? = authRemoteDataSource.currentAuthUser?.uid
        if(uid == null)
            return Result.failure(Exception("An authorized user is not currently logged in!"))

        val userDocumentRef = userRemoteDataSource.getUserDocumentRef(uid)
        if(userDocumentRef == null)
            return Result.failure(Exception("The authorized user's data could not be located!"))

        return accountRemoteDataSource.addPurchaseDocument(userDocumentRef, orderID)

    }


}