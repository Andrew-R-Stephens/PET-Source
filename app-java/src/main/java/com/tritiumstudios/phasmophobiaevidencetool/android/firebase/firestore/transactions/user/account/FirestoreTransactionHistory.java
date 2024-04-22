package com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.FirestoreAccount;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

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
                                Log.d("Firestore",
                                        DOCUMENT_TRANSACTION_HISTORY +
                                                " successfully INITIALIZED!")
                        )
                        .addOnFailureListener(e -> {
                            Log.d("Firestore",
                                    DOCUMENT_TRANSACTION_HISTORY +
                                            " failed INITIALIZATION");
                            e.printStackTrace();
                        })
                        .addOnCompleteListener(task ->
                                Log.d("Firestore",
                                        DOCUMENT_TRANSACTION_HISTORY +
                                                " INITIALIZATION process complete!")
                        );
            })
            .addOnFailureListener(Throwable::printStackTrace);

    }

    @NonNull
    public static DocumentReference getTransactionHistoryDocument()
            throws Exception {

        return FirestoreAccount.getAccountCollection()
                .document(DOCUMENT_TRANSACTION_HISTORY);
    }

}
