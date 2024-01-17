package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class FirestorePurchaseHistory {

    public static void init(DocumentReference userDocument) {
        getPurchaseDocument(getUserPurchaseHistoryCollection(userDocument))
            .set(new HashMap<String, Object>(), SetOptions.merge())
            .addOnSuccessListener(unused ->
                    Log.d("Firestore", "User Purchase History successfully INITIALIZED!"))
            .addOnFailureListener(e -> {
                Log.d("Firestore", "User Purchase History failed INITIALIZATION");
                e.printStackTrace();
            })
            .addOnCompleteListener(task ->
                    Log.d("Firestore", "User Purchase History INITIALIZATION process complete!"));
    }

    public static CollectionReference getUserPurchaseHistoryCollection() throws RuntimeException {
        final String purchaseHistoryCollection = "PurchaseHistory";

        try {
            return FirestoreUser.getUserDocument().collection(purchaseHistoryCollection);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static CollectionReference getUserPurchaseHistoryCollection(DocumentReference userDocument) {
        final String purchaseHistoryCollection = "PurchaseHistory";

        return userDocument.collection(purchaseHistoryCollection);
    }

    public static DocumentReference getPurchaseDocument(CollectionReference purchaseHistoryCollection) {
        final String purchasesDocument = "PurchasedItem";

        return purchaseHistoryCollection.document(purchasesDocument);
    }

}
