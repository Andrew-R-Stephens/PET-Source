package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreAccount;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FirestorePurchaseHistory {

    private final static String COLLECTION_PURCHASE_HISTORY = "PurchaseHistory";

    private final static String DOCUMENT_PURCHASED_ITEM = "PurchasedItem";

    private final static String FIELD_DATE_PURCHASED = "datePurchased";

    public static void init()
            throws Exception {
        DocumentReference userDocument = FirestoreUser.getUserDocument();

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

    public static CollectionReference getUserPurchaseHistoryCollection()
            throws Exception {
        try {
            return FirestoreUser.getUserDocument().collection(COLLECTION_PURCHASE_HISTORY);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static CollectionReference getUserPurchaseHistoryCollection(DocumentReference userDocument)
            throws Exception {
        return userDocument.collection(COLLECTION_PURCHASE_HISTORY);
    }

    public static DocumentReference getPurchaseDocument(CollectionReference purchaseHistoryCollection)
            throws Exception {
        return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM);
    }

    public static void addPurchaseDocument(OnSuccessListener<DocumentSnapshot> callback, String uuid)
            throws Exception {
        CollectionReference purchaseCollection = getUserPurchaseHistoryCollection();

        if(purchaseCollection == null || uuid == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_DATE_PURCHASED, Timestamp.now());

        DocumentReference purchasedDocument = purchaseCollection.document(uuid);
        purchasedDocument.get().addOnSuccessListener(callback);

        purchasedDocument.set(documentData, SetOptions.merge())
                .addOnSuccessListener(unused -> Log.d("Firestore", "Purchase document " + uuid + " ADDED!"))
                .addOnFailureListener(e -> {
                    Log.d("Firestore", "Purchase document " + uuid + " could NOT be GENERATED / LOCATED!");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> Log.d("Firestore", "Purchase document " + uuid + " process complete."));
    }
}
