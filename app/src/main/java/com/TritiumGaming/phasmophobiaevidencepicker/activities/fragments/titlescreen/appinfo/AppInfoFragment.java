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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        // INITIALIZE FONT EMPHASIS COLOR
        TypedValue typedValue = new TypedValue();
        @ColorInt
        int color;
        if (getContext() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.light_inactive, typedValue, true);
        }
        color = typedValue.data;

        // INITIALIZE VIEWS
        AppCompatImageButton closeButton = view.findViewById(R.id.popup_close_button);

        AppCompatTextView title = view.findViewById(R.id.label_settingstitle);
        AppCompatTextView version = view.findViewById(R.id.label_version);
        AppCompatTextView aboutapp_info = view.findViewById(R.id.aboutinfo_aboutapp_info);

        AppCompatTextView discordLabel = view.findViewById(R.id.appinfo_joinDiscord);

        ConstraintLayout appinfo_discordclickbutton = view.findViewById(R.id.constraintLayout_discord);

        LinearLayoutCompat linearLayout_specialThanks =
                view.findViewById(R.id.scrollview_list_specialthanks);

        // LISTENERS
        closeButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        appinfo_discordclickbutton.setOnClickListener(v -> startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://discord.gg/" + getString(R.string.aboutinfo_discordInvite)))));

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
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


        // DISCORD LABEL
        discordLabel.setMaxLines(1);
        discordLabel.setEllipsize(null);


        // SPECIAL THANKS
        //AppCompatTextView[] names = new AppCompatTextView[typedArray.length()];
        int nameCount = typedArray.length();
        for (int i = 0; i < nameCount; i++) {

            if(getView() != null && getView().getContext() != null) {
                LayoutInflater inflater =
                        (LayoutInflater) getView().getContext().getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE);
                ConstraintLayout specialThanksItem_layout = (ConstraintLayout) inflater.inflate(R.layout.item_special_thanks_label, null);
                AppCompatTextView textView_username = specialThanksItem_layout.findViewById(R.id.specialThanks_username);
                textView_username.setText(typedArray.getString(i));
                linearLayout_specialThanks.addView(specialThanksItem_layout);
            }
        }

        typedArray.recycle();

    }

}
