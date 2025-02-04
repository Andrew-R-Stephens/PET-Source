package com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.codexmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.CodexFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.views.CodexGridCard

class CodexMenuFragment : CodexFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_utilities_codex, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridLayout>(R.id.grid_codex)

        val gotoEquipmentFragment = gridView.findViewById<CodexGridCard>(R.id.grid_codexmenu_option1)
        gotoEquipmentFragment.setOnClickListener { v: View? ->
            v?.let {
                try {
                    findNavController(v).navigate(R.id.action_codexFragment_to_equipmentStoreFragment)
                } catch (e: IllegalArgumentException) { e.printStackTrace() }
            }

        }

        val gotoPossessionsFragment = gridView.findViewById<CodexGridCard>(R.id.grid_codexmenu_option2)
        gotoPossessionsFragment?.setOnClickListener { v: View? ->
            v?.let {
                try {
                    findNavController(v).navigate(R.id.action_codexFragment_to_cursedPossessionsFragment)
                } catch (e: IllegalArgumentException) { e.printStackTrace() }
            }
        }

        /*
        val gotoAchievements =
            gridView.findViewById<CodexGridCard>(R.id.grid_codexmenu_option3)
        gotoAchievements?.setOnClickListener { v: View? ->
            v?.let { findNavController(v)
                    .navigate(R.id.action_codexFragment_to_achievementsFragment) } }
        */

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos))
    }

    override fun reset() { }

    /*override fun saveStates() { }*/
}
