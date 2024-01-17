package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirestoreAccountCredit {

    public final static String
            FIELD_CREDITS_EARNED = "earnedCredits",
            FIELD_CREDITS_SPENT = "spentCredits";

    public static void init(DocumentReference userDocument) {

        Map<Object, Object> creditsMap = new HashMap<>();

        getCreditsDocument(userDocument)
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

    public static CollectionReference getAccountCollection(DocumentReference userDocument) {
        final String accountCollection = "Account";

        return userDocument.collection(accountCollection);
    }

    public static DocumentReference getCreditsDocument(DocumentReference userDocument) {
        final String creditsDocument = "Credits";

        return getAccountCollection(userDocument).document(creditsDocument);
    }

    public static DocumentReference getCreditsDocument(CollectionReference purchaseHistoryCollection) {
        final String creditsDocument = "Credits";

        return purchaseHistoryCollection.document(creditsDocument);
    }

}
