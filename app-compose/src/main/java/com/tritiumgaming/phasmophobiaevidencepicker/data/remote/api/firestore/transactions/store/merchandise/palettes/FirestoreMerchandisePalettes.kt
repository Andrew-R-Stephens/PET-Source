package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.merchandise.palettes

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.store.merchandise.FirestoreMerchandise.Companion.merchandiseDocument

class FirestoreMerchandisePalettes {

    companion object {
        private const val COLLECTION_THEMES = "Themes"

        private val themeCollection: CollectionReference
            get() = merchandiseDocument.collection(COLLECTION_THEMES)

        fun getThemesWhere(
            filterField: String = "group", value: String? = null,
            orderField: String? = null,
            order: Query.Direction? = Query.Direction.DESCENDING
        ): Task<QuerySnapshot> {
            val query = themeCollection
                .whereEqualTo(filterField, value)

            if (orderField == null || order == null) {
                return query.get()
            }

            return query
                .orderBy(orderField, order)
                .get()
        }

        fun getAllThemes(
            orderField: String? = null,
            order: Query.Direction? = Query.Direction.DESCENDING
        ): Task<QuerySnapshot> {
            val query = themeCollection

            if (orderField == null || order == null) {
                return query.get()
            }

            return query
                .orderBy(orderField, order)
                .get()
        }
    }

}
