package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseUtilities {

    private void recordToFirebase(FirebaseAnalytics firebaseAnalytics,
                                  String itemId,
                                  String itemName,
                                  String contentType) {

        Bundle bundle = new Bundle();

        if(itemId != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        }

        if(itemId != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        }

        if(itemId != null) {
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}
