package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.google.firebase.firestore.DocumentReference;

public class FirestoreAccount {

    public static void init(DocumentReference userDoc) {
        FirestoreAccountCredit.init(userDoc);
    }


}
