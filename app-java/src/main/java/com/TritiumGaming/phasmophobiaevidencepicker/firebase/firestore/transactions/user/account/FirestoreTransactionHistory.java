package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction.FirestorePurchaseHistory;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction.FirestoreUnlockHistory;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreTransactionHistory {

    private final static String DOCUMENT_TRANSACTION_HISTORY = "TransactionHistory";

    public static void init() throws Exception {

        Map<Object, Object> emptyMap = new HashMap<>();

        getTransactionHistoryDocument()
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                documentSnapshot.getReference().set(emptyMap, SetOptions.merge())
                        .addOnSuccessListener(unused ->
                                Log.d("Firestore", DOCUMENT_TRANSACTION_HISTORY + " successfully INITIALIZED!"))
                        .addOnFailureListener(e -> {
                            Log.d("Firestore", DOCUMENT_TRANSACTION_HISTORY + " failed INITIALIZATION");
                            e.printStackTrace();
                        })
                        .addOnCompleteListener(task ->
                                Log.d("Firestore", DOCUMENT_TRANSACTION_HISTORY + " INITIALIZATION process complete!"));
            })
            .addOnFailureListener(Throwable::printStackTrace);

    }

    public static DocumentReference getTransactionHistoryDocument()
            throws Exception {

        return FirestoreAccount.getAccountCollection()
                .document(DOCUMENT_TRANSACTION_HISTORY);
    }

}
