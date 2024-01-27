package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account;

import android.util.Log;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.bundle.MarketThemeBundle;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FirestorePurchaseHistory {

    private final static String COLLECTION_PURCHASE_HISTORY = "PurchaseHistory";

    private final static String DOCUMENT_PURCHASED_ITEM = "PurchasedItem";

    private final static String FIELD_DATE_PURCHASED = "datePurchased";
    private final static String FIELD_TYPE = "type";

    public static void init()
            throws Exception {

        getPurchaseDocument()
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

        return FirestoreUser.getUserDocument().collection(COLLECTION_PURCHASE_HISTORY);
    }

    private static DocumentReference getPurchaseDocument()
            throws Exception {
        CollectionReference purchaseHistoryCollection = getUserPurchaseHistoryCollection();

        return purchaseHistoryCollection.document(DOCUMENT_PURCHASED_ITEM);
    }

    public static void addPurchaseDocument(
            String purchaseUUID, String type, OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference purchaseCollection = getUserPurchaseHistoryCollection();

        if(purchaseUUID == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_TYPE, type);
        documentData.put(FIELD_DATE_PURCHASED, Timestamp.now());

        DocumentReference purchasedDocument = purchaseCollection.document(purchaseUUID);

        purchasedDocument.set(documentData, SetOptions.merge())
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

    public static void addPurchaseDocuments(
            ArrayList<String> purchasedUUIDs, String type, OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference purchaseCollection = getUserPurchaseHistoryCollection();

        if(purchasedUUIDs == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_TYPE, type);
        documentData.put(FIELD_DATE_PURCHASED, Timestamp.now());

        for(String uuid : purchasedUUIDs) {
            DocumentReference purchasedDocument = purchaseCollection.document(uuid);
            purchasedDocument.set(documentData, SetOptions.merge())
                    .addOnSuccessListener(unused -> {
                        callback.onSuccess();

                        Log.d("Firestore", "Purchase document of " + uuid + " GENERATED / LOCATED!");
                    })
                    .addOnFailureListener(e -> {
                        callback.onFailure();

                        Log.d("Firestore", "Purchase document of " + uuid + " could NOT be GENERATED / LOCATED!");
                        e.printStackTrace();
                    })
                    .addOnCompleteListener(task -> {
                        callback.onComplete();

                        Log.d("Firestore", "Purchase document of " + uuid + " process complete.");
                    });
        }
    }
}
