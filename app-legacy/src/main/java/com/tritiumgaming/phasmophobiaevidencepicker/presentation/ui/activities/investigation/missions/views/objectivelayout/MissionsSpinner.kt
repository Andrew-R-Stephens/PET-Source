package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.missions.views.objectivelayout

import android.content.Context
import android.content.res.Resources.Theme
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.missions.MissionsListModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.missions.MissionsListModel.Objective
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.ObjectivesViewModel

class MissionsSpinner : AppCompatSpinner {

    private var objectivesViewModel: ObjectivesViewModel? = null

    private var missionId: Int? = null
    private var currentMission: Objective? = null

    constructor(context: Context) : super(context) { initView() }

    constructor(context: Context, mode: Int) : super(context, mode) { initView() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, data: MissionsListModel?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) :
            super(context, attrs, defStyleAttr, mode) { initView() }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int, popupTheme: Theme?) :
            super(context, attrs, defStyleAttr, mode, popupTheme) { initView() }

    private fun initView() { }

    fun init(objectivesViewModel: ObjectivesViewModel?, missionId: Int) {
        this.objectivesViewModel = objectivesViewModel
        this.missionId = missionId

        updateAdapter()

        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

                if(currentMission == null) {
                    currentMission =
                        objectivesViewModel?.missionsListModel?.selectMatchOrFirstAvailable(missionId)
                    currentMission?.let { currentMission ->
                        adapter?.let { adapter ->
                            for(i in 0 ..< adapter.count) {
                                if((adapter.getItem(i) as AdapterWrapper).objective == currentMission) {
                                    setSelection(i)
                                }
                            }
                        }
                    }
                } else {
                    currentMission?.deselect()
                    val newMission = (parent.getItemAtPosition(position) as AdapterWrapper).objective
                    newMission.select(missionId)
                    currentMission = newMission
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    override fun performClick(): Boolean {
        val isClicked = super.performClick()
        updateAdapter()

        return isClicked
    }

    private fun updateAdapter() {
        val availableObjectives =
            objectivesViewModel?.missionsListModel?.getMissionsBy(false, missionId)

        val objectiveWrappers: ArrayList<AdapterWrapper> = arrayListOf()
        availableObjectives?.forEach { objective ->
            objectiveWrappers.add(AdapterWrapper(
                objective, context.getString(objective.contentRes)))
        }

        objectiveWrappers.let {
            val adapter = ArrayAdapter(context, R.layout.popup_spinner, objectiveWrappers)
            setAdapter(adapter)
        }
    }

    data class AdapterWrapper(val objective: Objective, val content: String) {
        override fun toString(): String {
            return content
        }
    }

}
