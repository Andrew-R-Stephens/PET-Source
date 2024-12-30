package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.missions

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.ObjectivesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.missions.views.MissionsItemLayout
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

/**
 * ObjectivesFragment class
 *
 * @author TritiumGamingStudios
 */
class MissionsFragment : InvestigationFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_objectives, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.init()

        // MISSIONS
        val missionLayouts = ArrayList<MissionsItemLayout?>()
        missionLayouts.add(view.findViewById(R.id.objective1))
        missionLayouts.add(view.findViewById(R.id.objective2))
        missionLayouts.add(view.findViewById(R.id.objective3))
        for (i in missionLayouts.indices) {
            objectivesViewModel.let { missionLayouts[i]?.init(it, i) }
        }

        // GHOST NAME
        val nameInput = view.findViewById<EditText>(R.id.textInput_ghostName)
        nameInput?.let {
            nameInput.setText(objectivesViewModel.ghostName)
            nameInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(
                    s: CharSequence, start: Int, before: Int, count: Int) { }
                override fun afterTextChanged(s: Editable) {
                    objectivesViewModel.ghostName = s.toString()
                }
            })
        }

        // RESPONSE TYPE
        val buttonAlone = view.findViewById<AppCompatImageButton>(R.id.button_alone)
        val buttonGroup = view.findViewById<AppCompatImageButton>(R.id.button_everyone)
        val responseBlocker = view.findViewById<ConstraintLayout>(R.id.blocking_group)

        val onResponseChangeListener: OnResponseChangeListener =
            object : OnResponseChangeListener() {
                override fun onChange() {
                    @ColorInt val unSelColor =
                        getColorFromAttribute(requireContext(), R.attr.unselectedColor)
                    @ColorInt val selColor =
                        getColorFromAttribute(requireContext(), R.attr.selectedColor)

                    if (investigationViewModel.difficultyCarouselModel?.responseTypeKnown == true) {
                        responseBlocker.visibility = View.GONE

                        when (objectivesViewModel.responseState) {
                            ObjectivesViewModel.ALONE -> {
                                buttonAlone?.setColorFilter(selColor)
                                buttonGroup?.setColorFilter(unSelColor)
                            }

                            ObjectivesViewModel.GROUP -> {
                                buttonAlone?.setColorFilter(unSelColor)
                                buttonGroup?.setColorFilter(selColor)
                            }

                            ObjectivesViewModel.UNKNOWN -> {
                                buttonAlone?.setColorFilter(unSelColor)
                                buttonGroup?.setColorFilter(unSelColor)
                            }
                        }
                    } else {
                        responseBlocker.visibility = View.VISIBLE
                    }
                }
            }
        buttonAlone?.setOnClickListener {
            objectivesViewModel.responseState = ObjectivesViewModel.ALONE
            onResponseChangeListener.onChange()
        }
        buttonGroup?.setOnClickListener {
            objectivesViewModel.responseState = ObjectivesViewModel.GROUP
            onResponseChangeListener.onChange()
        }

        onResponseChangeListener.onChange()

        try {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        findNavController(view).popBackStack()
                    }
                })
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    /*override fun saveStates() { }*/

    override fun reset() { }

    internal abstract inner class OnResponseChangeListener {
        abstract fun onChange()
    }

}
