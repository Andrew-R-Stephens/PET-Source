package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appinfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

public class AppInfoFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;

    private TitlescreenViewModel titleScreenViewModel = null;

    private Typeface bodyFont = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel =
                    new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
        }
        // INITIALIZE VIEW MODEL
        if (getContext() != null) {
            globalPreferencesViewModel.init(getContext());
        }

        if (titleScreenViewModel == null) {
            titleScreenViewModel =
                    new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_appinfo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TypedArray typedArray =
                getResources().obtainTypedArray(R.array.aboutinfo_specialthanks_list);
        AppCompatTextView[] names = new AppCompatTextView[typedArray.length()];

        // INITIALIZE FONT EMPHASIS COLOR
        TypedValue typedValue = new TypedValue();
        @ColorInt
        int color;
        if (getContext() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.light_inactive, typedValue, true);
        }
        color = typedValue.data;

        // SET FONT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bodyFont = getResources().getFont(R.font.eastseadokdo_regular);
        }
        else {
            if (getContext() != null) {
                bodyFont = ResourcesCompat.getFont(getContext(), R.font.eastseadokdo_regular);
            }
        }

        // INITIALIZE VIEWS
        AppCompatImageButton closeButton = view.findViewById(R.id.popup_close_button);

        AppCompatTextView title = view.findViewById(R.id.label_settingstitle);
        AppCompatTextView version = view.findViewById(R.id.label_version);
        AppCompatTextView aboutapp_info = view.findViewById(R.id.aboutinfo_aboutapp_info);
        AppCompatTextView developerInfo_title =
                view.findViewById(R.id.aboutinfo_developerinfo_title);
        AppCompatTextView discordLabel = view.findViewById(R.id.appinfo_joinDiscord);

        LinearLayout linearLayout_developerInfo_subtitles =
                view.findViewById(R.id.appinfo_developerinfo_subtitles);
        LinearLayout linearLayout_developerInfo_subinfo =
                view.findViewById(R.id.appinfo_developerinfo_subinfo);

        View appinfo_discordclickbutton = view.findViewById(R.id.appinfo_discordclickbox);

        AppCompatTextView specialThanks_title =
                view.findViewById(R.id.aboutinfo_specialthanks_title);
        LinearLayout linearLayout_specialThanks =
                view.findViewById(R.id.scrollview_list_specialthanks);


        // TEXT SIZE
        /*
        title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        version.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        aboutapp_info.setAutoSizeTextTypeUniformWithConfiguration(
                16, 100, 1,
                TypedValue.COMPLEX_UNIT_SP);
        developerInfo_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        specialThanks_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        discordLabel.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        */

        // LISTENERS
        closeButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        appinfo_discordclickbutton.setOnClickListener(v -> startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://discord.gg/" + getString(R.string.aboutinfo_discordInvite)))));

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(this,
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // ABOUT APP - TITLE
        String abouttitle = getResources().getString(R.string.aboutinfo_title_about);
        Spannable aboutPET = (Spannable) Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getResources().getString(R.string.aboutinfo_title_petstylized),
                "#FF0000", color + ""));
        title.setText(TextUtils.concat(abouttitle, " ", aboutPET));

        // ABOUT APP - VERSION
        String versionData = getResources().getString(R.string.aboutinfo_version) + ": ";
        try {
            if (getContext() != null) {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(
                        getContext().getPackageName(),
                        0);
                versionData += pInfo.versionName;
            }
        } catch (
                PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionData = "Version Unknown";
        }
        version.setText(versionData);

        // ABOUT APP - DESCRIPTION
        aboutapp_info.setText(Html.fromHtml(FontUtils.replaceHTMLFontColor(
                getResources().getString(R.string.aboutinfo_aboutapp_info),
                "#CC3C3C",
                color + "")));


        // DEVELOPER DATA
        @ColorInt int color_title, color_body;
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.titleFontColor, typedValue, true);
        color_title = typedValue.data;
        String colorHex = String.format("%06X", (0xFFFFFF & color_title));
        theme.resolveAttribute(R.attr.bodyFontColor, typedValue, true);
        color_body = typedValue.data;

        String[] subTitles =
                getResources().getStringArray(R.array.aboutinfo_developerinfo_subtitles);
        String[] subInfo =
                getResources().getStringArray(R.array.aboutinfo_developerinfo_subinfo);

        for (int i = 0; i < subTitles.length; i++) {

            AppCompatTextView developerInfo_subtitle = new AppCompatTextView(getContext());
            Spannable subtitle =
                    (Spannable) Html.fromHtml(FontUtils.setColor(subTitles[i], colorHex));
            Spannable subinfo = (Spannable) Html.fromHtml(subInfo[i]);

            developerInfo_subtitle.setText(TextUtils.concat(subtitle, ":"));
            developerInfo_subtitle.setTypeface(bodyFont);
            developerInfo_subtitle.setTextColor(color_body);
            developerInfo_subtitle.setMaxLines(1);
            developerInfo_subtitle.setEllipsize(null);
            developerInfo_subtitle.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            developerInfo_subtitle.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            if (getView() != null) {
                developerInfo_subtitle.setAutoSizeTextTypeUniformWithConfiguration(
                        1,
                        (int) (FontUtils.dpToSp(getView(),
                                developerInfo_title.getTextSize())),
                        1,
                        TypedValue.COMPLEX_UNIT_SP);
            }
            AppCompatTextView developerInfo_subinfo = new AppCompatTextView(getContext());
            developerInfo_subinfo.setText(subinfo);
            developerInfo_subinfo.setTypeface(bodyFont);
            developerInfo_subinfo.setTextColor(color_body);
            developerInfo_subinfo.setMaxLines(1);
            developerInfo_subinfo.setEllipsize(null);
            developerInfo_subinfo.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            developerInfo_subinfo.setGravity(Gravity.CENTER);
            if (getView() != null) {
                developerInfo_subinfo.setAutoSizeTextTypeUniformWithConfiguration(
                        1,
                        (int) (FontUtils.dpToSp(
                                getView(), developerInfo_subtitle.getTextSize()) * .95),
                        1,
                        TypedValue.COMPLEX_UNIT_SP);
            }
            linearLayout_developerInfo_subtitles.addView(developerInfo_subtitle);
            linearLayout_developerInfo_subinfo.addView(developerInfo_subinfo);
        }


        // DISCORD LABEL
        discordLabel.setMaxLines(1);
        discordLabel.setEllipsize(null);


        // SPECIAL THANKS
        for (int i = 0; i < names.length; i++) {
            names[i] = new AppCompatTextView(getContext());
            names[i].setText(typedArray.getString(i));
            names[i].setTypeface(bodyFont);
            names[i].setTextColor(color_body);
            names[i].setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            names[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            names[i].setTextSize(
                    (int) (FontUtils.dpToSp(view, specialThanks_title.getTextSize()) * .9));

            linearLayout_specialThanks.addView(names[i]);
        }
        typedArray.recycle();

    }

}
