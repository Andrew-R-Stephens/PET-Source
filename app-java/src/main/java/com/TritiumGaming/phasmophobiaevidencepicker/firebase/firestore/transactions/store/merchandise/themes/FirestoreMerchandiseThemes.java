package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.themes;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.FirestoreMerchandise;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreMerchandiseThemes {

    private static final String COLLECTION_THEMES = "Themes";

    public static CollectionReference getThemeCollection() throws Exception {
        return FirestoreMerchandise.getMerchandiseDocument().collection(COLLECTION_THEMES);
    }

    public static Task<QuerySnapshot> getThemesWhere(
            String filterField, String value, String orderField, Query.Direction order)
            throws Exception {

        Query query = FirestoreMerchandiseThemes.getThemeCollection()
                .whereEqualTo(filterField, value);

        if(orderField == null || order == null) {
            return query.get();
        }

        return query
                .orderBy(orderField, order)
                .get();
    }

}
