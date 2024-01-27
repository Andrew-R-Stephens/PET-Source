package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirestoreAccountCredit {

    public final static String
            COLLECTION_ACCOUNT = "Account",
            DOCUMENT_CREDITS = "Credits",
            FIELD_CREDITS_EARNED = "earnedCredits",
            FIELD_CREDITS_SPENT = "spentCredits";

    public static void init() throws Exception {

        Map<Object, Object> creditsMap = new HashMap<>();

        getCreditsDocument()
            .get()
            .addOnSuccessListener(documentSnapshot -> {

                if (documentSnapshot.get(FIELD_CREDITS_EARNED) == null) {
                    creditsMap.put(FIELD_CREDITS_EARNED, 0);
                }
                if (documentSnapshot.get(FIELD_CREDITS_SPENT) == null) {
                    creditsMap.put(FIELD_CREDITS_SPENT, 0);
                }

                documentSnapshot.getReference().set(creditsMap, SetOptions.merge())
                        .addOnSuccessListener(unused ->
                                Log.d("Firestore", "User Credits successfully INITIALIZED!"))
                        .addOnFailureListener(e -> {
                            Log.d("Firestore", "User Credits failed INITIALIZATION");
                            e.printStackTrace();
                        })
                        .addOnCompleteListener(task ->
                                Log.d("Firestore", "User Credits INITIALIZATION process complete!"));
            })
            .addOnFailureListener(Throwable::printStackTrace);
    }

    private static CollectionReference getAccountCollection()
            throws Exception {
        return FirestoreUser.getUserDocument().collection(COLLECTION_ACCOUNT);
    }

    public static DocumentReference getCreditsDocument()
            throws Exception {
        return getAccountCollection().document(DOCUMENT_CREDITS);
    }

    public static void addCredits(long creditAmount) throws Exception {
        addCredits(creditAmount, null);
    }

    public static void addCredits(long creditAmount, OnFirestoreProcessListener callback) throws Exception {
        DocumentReference creditDocument = getCreditsDocument();

        Map<String, Object> data = new HashMap<>();
        data.put(FIELD_CREDITS_EARNED, FieldValue.increment(creditAmount));

        creditDocument.update(data)
                .addOnSuccessListener(unused -> {
                    if (callback != null) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (callback != null) {
                        callback.onFailure();
                    }
                })
                .addOnCompleteListener(task -> {
                    if (callback != null) {
                        callback.onComplete();
                    }
                });
    }

    public static void removeCredits(long creditAmount) throws Exception {
        removeCredits(creditAmount, null);
    }

    public static void removeCredits(long creditAmount, OnFirestoreProcessListener callback)
            throws Exception {
        DocumentReference creditDocument = getCreditsDocument();

        creditDocument.get().addOnCompleteListener(task -> {

            Long storedCredits = task.getResult().get(FIELD_CREDITS_EARNED, Long.class);
            if(storedCredits != null && storedCredits < creditAmount) {
                if(callback != null) {
                    callback.onFailure();
                }

                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_CREDITS_EARNED, FieldValue.increment(-creditAmount));
            data.put(FIELD_CREDITS_SPENT, FieldValue.increment(creditAmount));

            task.getResult().getReference().update(data)
                    .addOnSuccessListener(result -> {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    })
                    .addOnFailureListener(error -> {
                        if (callback != null) {
                            callback.onFailure();
                        }
                        error.printStackTrace();
                    })
                    .addOnCompleteListener(result -> {
                        if (callback != null) {
                            callback.onComplete();
                        }
                    });

            if(callback != null) {
                callback.onComplete();
            }
        });

    }

}
