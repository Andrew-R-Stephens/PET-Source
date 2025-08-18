package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.appinfo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.NavHeaderLayout
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils.replaceHTMLFontColor
import androidx.core.net.toUri

class AppInfoFragment : MainMenuFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_appinfo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typedArray =
            resources.obtainTypedArray(R.array.aboutinfo_specialthanks_list)

        // INITIALIZE VIEWS
        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val closeButton = navHeaderLayout.findViewById<PETImageButton>(R.id.button_left)
        val title = navHeaderLayout.findViewById<AppCompatTextView>(R.id.textView_title)
        val version = view.findViewById<AppCompatTextView>(R.id.label_version)
        val aboutInfoTextView = view.findViewById<AppCompatTextView>(R.id.aboutinfo_aboutapp_info)
        val discordLabel = view.findViewById<AppCompatTextView>(R.id.appinfo_joinDiscord)
        val discordButton = view.findViewById<ConstraintLayout>(R.id.constraintLayout_discord)
        val specialThanksLayout =
            view.findViewById<LinearLayoutCompat>(R.id.scrollview_list_specialthanks)

        // LISTENERS
        closeButton.setOnClickListener { v: View? ->
            v?.let {
                try { findNavController(v).popBackStack() }
                catch (e: IllegalStateException) { e.printStackTrace() } }
        }
        discordButton.setOnClickListener { v: View? ->
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW,
                        ("https://discord.gg/" + getString(R.string.aboutinfo_discordInvite)).toUri()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // INITIALIZE FONT EMPHASIS COLOR
        @ColorInt val color =
            getColorFromAttribute(requireContext(), R.attr.textColorBodyEmphasis)
        // ABOUT APP - TITLE
        val abouttitle = resources.getString(R.string.aboutinfo_title_about)
        val aboutPET = Html.fromHtml(replaceHTMLFontColor(
                resources.getString(R.string.aboutinfo_title_petstylized),
                "#FF0000", color.toString())) as Spannable
        title.text = TextUtils.concat(abouttitle, " ", aboutPET)

        // ABOUT APP - VERSION
        val versionData: StringBuilder =
            StringBuilder("${resources.getString(R.string.aboutinfo_version)}: ")
        try { val pInfo =
                requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            versionData.append(pInfo.versionName)
        } catch (e: Exception) {
            when(e) { is IllegalStateException, is PackageManager.NameNotFoundException -> {
                    e.printStackTrace()
                    versionData.append("Unknown")
            } }
        }
        version.text = versionData

        // ABOUT APP - DESCRIPTION
        aboutInfoTextView.text = Html.fromHtml(
            replaceHTMLFontColor(
                resources.getString(R.string.aboutinfo_aboutapp_info),
                "#CC3C3C",
                color.toString())
        )

        // DISCORD LABEL
        discordLabel.maxLines = 1
        discordLabel.ellipsize = null


        // SPECIAL THANKS
        //AppCompatTextView[] names = new AppCompatTextView[typedArray.length()];
        val nameCount = typedArray.length()
        for (i in 0 until nameCount) {
            try {
                val inflater =
                    requireView().context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                    ) as LayoutInflater

                val specialThanksItem_layout =
                    inflater.inflate(
                        R.layout.item_special_thanks_label,
                        null
                    ) as ConstraintLayout
                val textView_username =
                    specialThanksItem_layout.findViewById<AppCompatTextView>(R.id.specialThanks_username)
                textView_username.text = typedArray.getString(i)

                specialThanksLayout.addView(specialThanksItem_layout)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }

        typedArray.recycle()
    }

    override fun initViewModels() {
    }
}
