package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.missions.views.objectivelayout

import android.content.Context
import android.content.res.Resources.Theme
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions.MissionsListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.missions.MissionsListModel.Objective

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
                                if(adapter.getItem(i) as Objective == currentMission) {
                                    setSelection(i)
                                }
                            }
                        }
                    }
                } else {
                    currentMission?.deselect()
                    val newMission = parent.getItemAtPosition(position) as Objective
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

        availableObjectives?.let {
            val adapter = ArrayAdapter(context, R.layout.popup_spinner, availableObjectives)
            setAdapter(adapter)
        }
    }

}