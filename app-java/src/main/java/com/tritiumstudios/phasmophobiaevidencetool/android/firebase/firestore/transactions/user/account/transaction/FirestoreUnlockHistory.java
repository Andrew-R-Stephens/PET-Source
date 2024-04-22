package com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.account.transaction;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.account.FirestoreTransactionHistory;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreUnlockHistory {

    private final static String COLLECTION_UNLOCK_HISTORY = "UnlockHistory";

    private final static String DOCUMENT_UNLOCKED_ITEM = "UnlockedItem";

    private final static String FIELD_DATE_UNLOCKED = "dateUnlocked";
    private final static String FIELD_TYPE = "type";

    public static void init()
            throws Exception {

        getUnlockedDocument().get()
                .addOnSuccessListener(unused ->
                        Log.d("Firestore", COLLECTION_UNLOCK_HISTORY + " successfully INITIALIZED!"))
                .addOnFailureListener(e -> {
                    Log.d("Firestore", COLLECTION_UNLOCK_HISTORY + "User Unlock History failed INITIALIZATION");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task ->
                        Log.d("Firestore", COLLECTION_UNLOCK_HISTORY + "User Unlock History INITIALIZATION process complete!"));

    }

    @NonNull
    public static CollectionReference getUnlockHistoryCollection()
            throws Exception {

        return FirestoreTransactionHistory.getTransactionHistoryDocument().collection(COLLECTION_UNLOCK_HISTORY);
    }

    @NonNull
    private static DocumentReference getUnlockedDocument()
            throws Exception {
        CollectionReference purchaseHistoryCollection = getUnlockHistoryCollection();

        return purchaseHistoryCollection.document(DOCUMENT_UNLOCKED_ITEM);
    }

    public static void addUnlockDocument(
            @Nullable String unlockUUID, String type, @NonNull OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference unlocksCollection = getUnlockHistoryCollection();

        if(unlockUUID == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_TYPE, type);
        documentData.put(FIELD_DATE_UNLOCKED, Timestamp.now());

        DocumentReference unlockDocument = unlocksCollection.document(unlockUUID);

        unlockDocument.set(documentData, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    callback.onSuccess();

                    Log.d("Firestore", "Unlocked document of " + unlockUUID + " GENERATED / LOCATED!");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure();

                    Log.d("Firestore", "Unlocked document of " + unlockUUID + " could NOT be GENERATED / LOCATED!");
                    e.printStackTrace();
                })
                .addOnCompleteListener(task -> {
                    callback.onComplete();

                    Log.d("Firestore", "Unlocked document of " + unlockUUID + " process complete.");
                });
    }

    public static void addUnlockedDocuments(
            @Nullable ArrayList<String> unlockUUIDs, String type, @NonNull OnFirestoreProcessListener callback)
            throws Exception {
        CollectionReference unlockedHistoryCollection = getUnlockHistoryCollection();

        if(unlockUUIDs == null) { return; }

        Map<String, Object> documentData = new HashMap<>();
        documentData.put(FIELD_TYPE, type);
        documentData.put(FIELD_DATE_UNLOCKED, Timestamp.now());

        for(String uuid : unlockUUIDs) {
            DocumentReference purchasedDocument = unlockedHistoryCollection.document(uuid);
            purchasedDocument.set(documentData, SetOptions.merge())
                    .addOnSuccessListener(unused -> {
                        callback.onSuccess();

                        Log.d("Firestore", "Unlocked document of " + uuid + " GENERATED / LOCATED!");
                    })
                    .addOnFailureListener(e -> {
                        callback.onFailure();

                        Log.d("Firestore", "Unlocked document of " + uuid + " could NOT be GENERATED / LOCATED!");
                        e.printStackTrace();
                    })
                    .addOnCompleteListener(task -> {
                        callback.onComplete();

                        Log.d("Firestore", "Unlocked document of " + uuid + " process complete.");
                    });
        }
    }
}
