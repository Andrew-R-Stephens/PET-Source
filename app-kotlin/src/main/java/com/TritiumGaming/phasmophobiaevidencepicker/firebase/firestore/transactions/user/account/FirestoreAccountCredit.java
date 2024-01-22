package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public static CollectionReference getAccountCollection()
            throws Exception {
        return FirestoreUser.getUserDocument().collection(COLLECTION_ACCOUNT);
    }

    public static DocumentReference getCreditsDocument()
            throws Exception {
        return getAccountCollection().document(DOCUMENT_CREDITS);
    }

    public static void addCredits(long creditAmount) throws Exception {
        getCreditsDocument().update(FIELD_CREDITS_EARNED, FieldValue.increment(creditAmount));
    }

    public static void removeCredits(long creditAmount) throws Exception {
        getCreditsDocument().update(FIELD_CREDITS_EARNED, FieldValue.increment(-creditAmount));
        getCreditsDocument().update(FIELD_CREDITS_SPENT, FieldValue.increment(creditAmount));
    }

}
