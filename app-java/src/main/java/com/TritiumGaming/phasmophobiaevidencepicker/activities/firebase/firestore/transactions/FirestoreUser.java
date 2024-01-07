package com.TritiumGaming.phasmophobiaevidencepicker.activities.firebase.firestore.transactions;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreUser {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final String usersCollection = "Users";

    private final String accountCollection = "Account";
    private final String purchaseHistoryCollection = "PurchaseHistory";

    private final String creditsCollection = "Credits";
    private final String purchasesCollection = "PurchaseHistory";

    private final String fieldUID = "uid";

    public FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void createUserRecord() {
        /*
        db.collection("Users")
                .document(getCurrentFirebaseUser().getUid())
                .collection(accountCollection)
                .document(creditsCollection)
                .set(accountCredits, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error writing document", e);
                    }
                });
        */

        db.collection("Users")
                .document(getCurrentFirebaseUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            Log.d("Firestore", "Document " + getCurrentFirebaseUser().getUid() + " successfully exists!");
                        } else {
                            Log.d("Firestore", "Document " + getCurrentFirebaseUser().getUid() + " successfully created!");
                            initUserDoc(documentSnapshot.getReference());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firestore", "Document" + getCurrentFirebaseUser().getUid() + " already exists!");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.d("Firestore", "Process completed.");
                    }
                });
    }

    public void initUserDoc(DocumentReference doc) {
        Map<String, Object> accountCredits = new HashMap<>();
        Map<String, Object> purchaseHistory = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put(creditsCollection, accountCredits);
        data.put(purchasesCollection, purchaseHistory);

        doc.collection(accountCollection)
            .document(creditsCollection)
                .getParent().document(purchaseHistoryCollection);
    }

    public DocumentReference getUserDoc() {
        if(getCurrentFirebaseUser() == null) {
            return null;
        }

        //return db.collection(userTable).document(getCurrentFirebaseUser().getUid());
        return db.collection(usersCollection).document(getCurrentFirebaseUser().getUid());
    }

    public CollectionReference getUserCollection() {
        if(getCurrentFirebaseUser() == null) {
            return null;
        }

        //return db.collection(userTable).document(getCurrentFirebaseUser().getUid());
        return db.collection(usersCollection);
    }

}
