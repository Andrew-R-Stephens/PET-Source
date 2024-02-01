package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreTransactionHistory;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreAccount {

    public final static String COLLECTION_ACCOUNT = "Account";

    public static void init()
            throws Exception {
        FirestoreAccountCredit.init();
        FirestoreTransactionHistory.init();
    }

    public static CollectionReference getAccountCollection()
            throws Exception {
        return FirestoreUser.getUserDocument().collection(COLLECTION_ACCOUNT);
    }

}
