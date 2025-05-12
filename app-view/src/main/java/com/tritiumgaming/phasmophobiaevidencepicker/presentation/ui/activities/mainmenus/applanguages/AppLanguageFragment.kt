package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.applanguages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.applanguages.views.LanguagesAdapterView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.views.NavHeaderLayout

class AppLanguageFragment : MainMenuFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_languages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // INITIALIZE VIEWS
        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val backButton = navHeaderLayout.findViewById<View>(R.id.button_left)
        //val confirmButton = navHeaderLayout.findViewById<View>(R.id.button_right)

        val recyclerViewLanguages = view.findViewById<RecyclerView>(R.id.recyclerview_languageslist)

        // LISTENERS
        backButton.setOnClickListener { v: View? ->
            try { v?.let { findNavController(v).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        // DATA
        globalPreferencesViewModel.languageList.let { languageList ->
            val selectedIndex = languageList.indexOfFirst {
                it.abbreviation == globalPreferencesViewModel.currentLanguageCode.value }

            languageList.forEach {
                val adapter = LanguagesAdapterView(
                    languages = languageList,
                    defaultPosition = selectedIndex,
                    onLanguageListener = object: LanguagesAdapterView.OnLanguageListener {
                        override fun onNoteClick(position: Int) {
                            globalPreferencesViewModel.setCurrentLanguageCode(
                                languageList[position].abbreviation)

                            /*try {
                                (activity as PETActivity).setLanguage(
                                    globalPreferencesViewModel.currentLanguageCode.value)
                            }
                            catch (e: IllegalStateException) { e.printStackTrace() }*/

                            recyclerViewLanguages.invalidate()
                        }
                    })

                recyclerViewLanguages.adapter = adapter
                try { recyclerViewLanguages.layoutManager = LinearLayoutManager(requireContext()) }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }
        }
    }

    override fun onPause() {
        // SAVE PERSISTENT DATA
        //saveStates()
        super.onPause()
    }
}
