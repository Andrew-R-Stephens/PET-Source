package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.MainMenusFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;

public class AppInfoFragment extends MainMenusFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        super.init();

        return inflater.inflate(R.layout.fragment_appinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        TypedArray typedArray =
                getResources().obtainTypedArray(R.array.aboutinfo_specialthanks_list);

        // INITIALIZE FONT EMPHASIS COLOR
        TypedValue typedValue = new TypedValue();
        @ColorInt
        int color = getResources().getColor(R.color.white);
        try {
            Resources.Theme theme = requireContext().getTheme();
            theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
            color = typedValue.data;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        // INITIALIZE VIEWS
        AppCompatImageButton closeButton = view.findViewById(R.id.popup_close_button);

        AppCompatTextView title = view.findViewById(R.id.label_settingstitle);
        AppCompatTextView version = view.findViewById(R.id.label_version);
        AppCompatTextView aboutapp_info = view.findViewById(R.id.aboutinfo_aboutapp_info);

        AppCompatTextView discordLabel = view.findViewById(R.id.appinfo_joinDiscord);

        ConstraintLayout appinfo_discordButton = view.findViewById(R.id.constraintLayout_discord);

        LinearLayoutCompat linearLayout_specialThanks =
                view.findViewById(R.id.scrollview_list_specialthanks);

        // LISTENERS
        closeButton.setOnClickListener(v -> {
            try {
                Navigation.findNavController(v).popBackStack();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
        appinfo_discordButton.setOnClickListener(v -> startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://discord.gg/" + getString(R.string.aboutinfo_discordInvite)))));

        // ABOUT APP - TITLE
        String abouttitle = getResources().getString(R.string.aboutinfo_title_about);
        Spannable aboutPET = (Spannable) Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getResources().getString(R.string.aboutinfo_title_petstylized),
                "#FF0000", String.valueOf(color)));
        title.setText(TextUtils.concat(abouttitle, " ", aboutPET));

        // ABOUT APP - VERSION
        String versionData = getResources().getString(R.string.aboutinfo_version) + ": ";
        try {
                PackageInfo pInfo = requireContext().getPackageManager().getPackageInfo(
                        requireContext().getPackageName(),
                        0);
                versionData += pInfo.versionName;
        } catch (IllegalStateException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionData = "Version Unknown";
        }
        version.setText(versionData);

        // ABOUT APP - DESCRIPTION
        aboutapp_info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getResources().getString(R.string.aboutinfo_aboutapp_info),
                "#CC3C3C",
                String.valueOf(color))));


        // DISCORD LABEL
        discordLabel.setMaxLines(1);
        discordLabel.setEllipsize(null);


        // SPECIAL THANKS
        //AppCompatTextView[] names = new AppCompatTextView[typedArray.length()];
        int nameCount = typedArray.length();
        for (int i = 0; i < nameCount; i++) {

            try {
                LayoutInflater inflater =
                        (LayoutInflater) requireView().getContext().getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE);

                ConstraintLayout specialThanksItem_layout =
                        (ConstraintLayout) inflater.inflate(
                                R.layout.item_special_thanks_label,
                                null);
                AppCompatTextView textView_username =
                        specialThanksItem_layout.findViewById(R.id.specialThanks_username);
                textView_username.setText(typedArray.getString(i));

                linearLayout_specialThanks.addView(specialThanksItem_layout);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        typedArray.recycle();

    }

    @Override
    protected void initViewModels() {
    }

}
