package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.listeners.firestore.OnFirestoreProcessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirestoreAccountCredit {

    public final static String
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

    @NonNull
    public static DocumentReference getCreditsDocument()
            throws Exception {
        return FirestoreAccount.Companion
                .getAccountCollection()
                .document(DOCUMENT_CREDITS);
    }

    public static void addCredits(long creditAmount) throws Exception {
        addCredits(creditAmount, null);
    }

    public static void addCredits(long creditAmount, @Nullable OnFirestoreProcessListener callback) throws Exception {
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

    public static void removeCredits(long creditAmount, @Nullable OnFirestoreProcessListener callback)
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
