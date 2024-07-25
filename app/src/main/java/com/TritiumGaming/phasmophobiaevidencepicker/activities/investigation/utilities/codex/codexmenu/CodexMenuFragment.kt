package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.codexmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.CodexFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.views.CodexGridCard

class CodexMenuFragment : CodexFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_utilities_codex, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridLayout>(R.id.grid_codex)
        val gotoCodex = gridView.findViewById<CodexGridCard>(R.id.grid_codexmenu_option1)
        gotoCodex.setOnClickListener { v: View? ->
            v?.let { findNavController(v)
                .navigate(R.id.action_codexFragment_to_equipmentStoreFragment) }
        }

        val gotoCursedPossessions =
            gridView.findViewById<CodexGridCard>(R.id.grid_codexmenu_option2)
        gotoCursedPossessions?.setOnClickListener { v: View? ->
            v?.let { findNavController(v)
                    .navigate(R.id.action_codexFragment_to_cursedPossessionsFragment) } }

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

    override fun saveStates() { }
}
