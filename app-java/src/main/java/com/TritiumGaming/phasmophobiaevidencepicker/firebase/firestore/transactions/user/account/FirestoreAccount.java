package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.FirestoreTransactionHistory;
import com.google.firebase.firestore.CollectionReference;

public class FirestoreAccount {

    public final static String COLLECTION_ACCOUNT = "Account";

    public static void init()
            throws Exception {
        FirestoreAccountCredit.init();
        FirestoreTransactionHistory.init();
    }

    @NonNull
    public static CollectionReference getAccountCollection()
            throws Exception {
        return FirestoreUser.getUserDocument().collection(COLLECTION_ACCOUNT);
    }

}
