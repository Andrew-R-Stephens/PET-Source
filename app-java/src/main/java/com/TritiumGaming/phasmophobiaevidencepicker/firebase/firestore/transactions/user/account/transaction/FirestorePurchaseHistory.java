package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction;

import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreTransactionHistory;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirestorePurchaseHistory {

    private final static String COLLECTION_PURCHASE_HISTORY = "PurchaseHistory";
    private final static String DOCUMENT_PURCHASED_ITEM = "PurchaseItem";

    private final static String FIELD_DATE_PURCHASED = "datePurchased";

    private final static String
            FIELD_TYPE = "type",
            FIELD_PURCHASE_REFERENCE = "uuid";

    public static void init()
            throws Exception {

        getPurchaseDocument()
                .set(new HashMap<String, Object>(), SetOptions.merge())
                .addOnSuccessListener(unused ->
                        Log.d("Firestore", COLLECTION_PURCHASE_HISTORY + " successfully INITIALIZED!"))
                .addOnFailureListener(e -> {
                    Log.d("Firestore", COLLECTION_PURCHASE_HISTORY + "User Unlock History failed INITIALIZATION");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task ->
                        Log.d("Firestore", COLLECTION_PURCHASE_HISTORY + "User Unlock History INITIALIZATION process complete!"));

    }

    public static CollectionReference getPurchaseHistoryCollection()
            throws Exception {

        return FirestoreTransactionHistory.getTransactionHistoryDocument().collection(COLLECTION_PURCHASE_HISTORY);
    }

    private static DocumentReference getPurchaseDocument()
            throws Exception {
        CollectionReference purchaseHistoryCollection = getPurchaseHistoryCollection();

        return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM);
    }

    private static void addPurchaseDocument(
            String purchaseUUID, OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference purchaseHistoryCollection = getPurchaseHistoryCollection();

        if(purchaseUUID == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_PURCHASE_REFERENCE, purchaseUUID);
        documentData.put(FIELD_DATE_PURCHASED, Timestamp.now());

        purchaseHistoryCollection.add(documentData)
                .addOnSuccessListener(unused -> {
                    callback.onSuccess();

                    Log.d("Firestore", "Purchase document of " + purchaseUUID + " GENERATED / LOCATED!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure();

                    Log.d("Firestore", "Purchase document of " + purchaseUUID + " could NOT be GENERATED / LOCATED!");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> {
                    callback.onComplete();

                    Log.d("Firestore", "Purchase document of " + purchaseUUID + " process complete.");
                });
    }

    public static void purchaseCredits(String purchaseUUID, OnFirestoreProcessListener callback)
            throws Exception {
        addPurchaseDocument(purchaseUUID, callback);
    }

}
