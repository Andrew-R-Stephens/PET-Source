package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore.ReviewTrackerPreferences

interface ReviewTrackerDatastore: DatastoreInterface<ReviewTrackerPreferences> {

    data class ReviewTrackerPreferences(
        val allowRequestReview: Boolean,
        val timeActive: Long,
        val timesOpened: Int
    )

    companion object PreferenceKeys {
        lateinit var KEY_ALLOW_REQUEST_REVIEW: Preferences.Key<Boolean>
        lateinit var KEY_TIME_ACTIVE: Preferences.Key<Long>
        lateinit var KEY_TIMES_OPENED: Preferences.Key<Int>
    }


}