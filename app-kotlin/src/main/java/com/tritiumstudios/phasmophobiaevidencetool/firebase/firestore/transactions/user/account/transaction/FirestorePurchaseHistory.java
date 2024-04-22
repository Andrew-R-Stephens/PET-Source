package com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.user.account.transaction;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.tritiumstudios.phasmophobiaevidencetool.firebase.firestore.transactions.user.account.FirestoreTransactionHistory;
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
            FIELD_PURCHASE_REFERENCE = "product_ref",
            FIELD_ORDER_ID = "order_id";

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

    @NonNull
    public static CollectionReference getPurchaseHistoryCollection()
            throws Exception {

        return FirestoreTransactionHistory.getTransactionHistoryDocument().collection(COLLECTION_PURCHASE_HISTORY);
    }

    @NonNull
    private static DocumentReference getPurchaseDocument()
            throws Exception {
        CollectionReference purchaseHistoryCollection = getPurchaseHistoryCollection();

        return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM);
    }

    public static void addPurchaseDocument(
            @Nullable DocumentReference purchaseReferenceDoc, @Nullable String orderID, @Nullable OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference purchaseHistoryCollection = getPurchaseHistoryCollection();

        if(purchaseReferenceDoc == null || orderID == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_PURCHASE_REFERENCE, purchaseReferenceDoc);
        documentData.put(FIELD_ORDER_ID, orderID);
        documentData.put(FIELD_DATE_PURCHASED, Timestamp.now());

        purchaseHistoryCollection.add(documentData)
                .addOnSuccessListener(unused -> {
                    if(callback != null) {
                        callback.onSuccess();
                    }

                    Log.d("Firestore", "Purchase document of " +
                            purchaseReferenceDoc.getId() + " GENERATED / LOCATED!");
                })
                .addOnFailureListener(e -> {
                    if(callback != null) {
                        callback.onFailure();
                    }

                    Log.d("Firestore", "Purchase document of " +
                            purchaseReferenceDoc.getId() + " could NOT be GENERATED / LOCATED!");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> {
                    if (callback != null) {
                        callback.onComplete();
                    }

                    Log.d("Firestore", "Purchase document of " +
                            purchaseReferenceDoc.getId() + " process complete.");
                });
    }

}
