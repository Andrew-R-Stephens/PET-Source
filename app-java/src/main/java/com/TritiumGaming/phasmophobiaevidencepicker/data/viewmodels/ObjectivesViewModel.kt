package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions.MissionsListModel

/**
 * ObjectivesViewModel class
 *
 * @author TritiumGamingStudios
 */
class ObjectivesViewModel : ViewModel() {

    /* Chosen spinner objectives */
    var objectivesSpinnerObjectives: Array<MissionsListModel.Objective>? = null

    /* Objective Completed Buttons */
    var objectiveCompletion: BooleanArray? = null

    /* Ghost name */
    var ghostName: String? = null

    /* Response */
    var responseState: Boolean = false // alone , group

    fun reset() {
        objectivesSpinnerObjectives = null
        objectiveCompletion = null
        ghostName = null
        responseState = false
    }
}