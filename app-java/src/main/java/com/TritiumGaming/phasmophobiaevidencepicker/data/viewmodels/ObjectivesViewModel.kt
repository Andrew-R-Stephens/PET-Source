package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.missions.data.MissionsData

/**
 * ObjectivesViewModel class
 *
 * @author TritiumGamingStudios
 */
class ObjectivesViewModel : ViewModel() {

    /* Chosen spinner objectives */
    @JvmField
    var objectivesSpinnerObjectives: Array<MissionsData.Objective>? = null

    /* Objective Completed Buttons */
    var objectiveCompletion: BooleanArray? = null

    /* Ghost name */
    @JvmField
    var ghostName: String? = null

    /* Response */
    @JvmField
    var responseState: Boolean = false // alone , group

    fun reset() {
        objectivesSpinnerObjectives = null
        objectiveCompletion = null
        ghostName = null
        responseState = false
    }
}