package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.InvestigationSectionWrapper
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.sanity.SanityToolsLayout
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.InvestigationSection
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.EvidenceListView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists.GhostListView
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.CollapseButton
import io.realm.kotlin.Configuration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class EvidenceFragment(layout: Int) : InvestigationFragment(layout) {
    private var investigationSectionWrapper: InvestigationSectionWrapper? = null

    private var ghostSection: InvestigationSection? = null
    private var evidenceSection: InvestigationSection? = null

    private var ghostList: GhostListView? = null
    private var evidenceList: EvidenceListView? = null

    protected var sanityToolsLayout: SanityToolsLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_evidence, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sanityToolsLayout = view.findViewById(R.id.layout_sanity_tool)
        investigationViewModel?.let { investigationViewModel ->
            sanityToolsLayout?.init(investigationViewModel)
        }

        // GHOST / EVIDENCE CONTAINERS
        investigationSectionWrapper = view.findViewById(R.id.layout_evidence_tool_wrapper)
        val columnLeft = investigationSectionWrapper?.findViewById<FrameLayout>(R.id.column_left)
        val columnRight = investigationSectionWrapper?.findViewById<FrameLayout>(R.id.column_right)
        columnRight?.findViewById<View>(R.id.scrollview)?.verticalScrollbarPosition =
            View.SCROLLBAR_POSITION_RIGHT

        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            if (!globalPreferencesViewModel.isLeftHandSupportEnabled) {
                ghostSection = columnLeft?.getChildAt(0) as InvestigationSection
                evidenceSection = columnRight?.getChildAt(0) as InvestigationSection
            } else {
                evidenceSection = columnLeft?.getChildAt(0) as InvestigationSection
                ghostSection = columnRight?.getChildAt(0) as InvestigationSection
            }
        }

        ghostSection?.setLabel(getString(R.string.investigation_section_title_ghosts))
        evidenceSection?.setLabel(getString(R.string.investigation_section_title_evidence))

        ghostList = GhostListView(requireContext())
        evidenceList = EvidenceListView(requireContext())

        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            investigationViewModel?.let { investigationViewModel ->
                evidenceList?.init(globalPreferencesViewModel, investigationViewModel,
                    popupWindow, evidenceSection?.findViewById(R.id.progressbar),
                    adRequest, ghostList)
                ghostList?.init(globalPreferencesViewModel, investigationViewModel,
                    popupWindow, ghostSection?.findViewById(R.id.progressbar), adRequest)
            }
        }


        val ghostScrollview = ghostSection?.findViewById<ScrollView>(R.id.scrollview)
        val evidenceScrollview = evidenceSection?.findViewById<ScrollView>(R.id.scrollview)

        ghostScrollview?.addView(ghostList)
        evidenceScrollview?.addView(evidenceList)

        // SANITY COLLAPSIBLE
        val toggleCollapseButton: ComposeView? = view.findViewById(R.id.button_toggleSanity)
        toggleCollapseButton?.setOnClickListener {
            when (investigationViewModel?.investigationModel?.isSanityDrawerCollapsed?.value == true) {
                true ->
                    sanityToolsLayout?.animate()
                    ?.setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)
                            sanityToolsLayout?.visibility = View.GONE
                        }
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            sanityToolsLayout?.visibility = View.VISIBLE
                            investigationViewModel?.investigationModel?.setDrawerState(false)
                        }
                    })?.start()
                false -> {
                    sanityToolsLayout?.animate()
                    ?.setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)
                            sanityToolsLayout?.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationStart(animation)
                            sanityToolsLayout?.visibility = View.GONE
                            investigationViewModel?.investigationModel?.setDrawerState(true)
                        }
                    })?.start()
                }
            }
        }
        initCollapsible(toggleCollapseButton)

        popupWindow = PopupWindow(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        ghostList?.createPopupWindow(popupWindow)
        evidenceList?.createPopupWindow(popupWindow)
        Thread { ghostList?.createViews() }.start()
        Thread { evidenceList?.createViews() }.start()

    }

    private fun initCollapsible(toggleCollapseButton: ComposeView?) {
        investigationViewModel?.investigationModel?.let { investigationModel ->
            toggleCollapseButton?.setContent {
                CollapseButton(
                    isCollapsedState = investigationModel.isSanityDrawerCollapsed,
                    onClick = { investigationModel.toggleDrawerState() },
                    orientation = resources.configuration.orientation
                )
            }
        }

        lifecycleScope.launch {
            investigationViewModel?.investigationModel?.isSanityDrawerCollapsed
                ?.collectLatest { state ->
                sanityToolsLayout?.visibility = when (state) {
                    true -> View.GONE
                    false -> View.VISIBLE
                }
            }
        }
    }

    override fun reset() {
        investigationViewModel?.reset()
        objectivesViewModel?.reset()
        investigationViewModel?.investigationModel?.ghostOrderModel?.updateOrder()
        ghostList?.reset()
    }

    override fun onDestroyView() {
        popupWindow?.dismiss()
        popupWindow = null

        super.onDestroyView()
    }

    override fun saveStates() { }

}
