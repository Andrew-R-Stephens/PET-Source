package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.activity.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.activity.TitleScreenActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.TitleScreenAnimationView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.ColorThemesData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontStyler;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenFragment extends Fragment {

    private TitleScreenViewModel titleScreenViewModel = null;
    private MessageCenterViewModel messageCenterViewModel = null;

    private final BitmapUtils bitmapUtils = new BitmapUtils();

    private TitleScreenAnimationView animationView = null;
    private PopupWindow popup = null;

    private Typeface bodyFont = null;

    private boolean canRunAnim = true;
    private Thread animInitThread = null, initReadyThread = null, animTickThread = null, animDrawThread = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(requireActivity()).get(TitleScreenViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            titleScreenViewModel.init(getContext());

        if (messageCenterViewModel == null)
            messageCenterViewModel = new ViewModelProvider(requireActivity()).get(MessageCenterViewModel.class);


        return inflater.inflate(R.layout.fragment_titlescreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // SET FONT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            bodyFont = getResources().getFont(R.font.eastseadokdo_regular);
        else {
            if (getContext() != null)
                bodyFont = ResourcesCompat.getFont(getContext(), R.font.eastseadokdo_regular);
        }

        // INITIALIZE VIEWS
        animationView = view.findViewById(R.id.titlescreen_backgroundanimation);
        AppCompatTextView label_titledescription = view.findViewById(R.id.label_titledescription);
        AppCompatTextView label_languageName = view.findViewById(R.id.label_languageName);
        AppCompatTextView button_startSolo = view.findViewById(R.id.button_start_solo);
        AppCompatTextView button_startMult = view.findViewById(R.id.button_start_mult);
        AppCompatImageView icon_appIcon = view.findViewById(R.id.icon_appicon);
        ImageButton button_info = view.findViewById(R.id.button_info);
        ImageButton button_settings = view.findViewById(R.id.button_settings);
        ImageButton button_review = view.findViewById(R.id.button_review);
        ImageButton button_msgInbox = view.findViewById(R.id.button_inbox);
        View button_language = view.findViewById(R.id.listener_language);

        // SET APP-ICON
        bitmapUtils.clearResources();
        bitmapUtils.setResource(R.drawable.app_icon_sm);
        if (getContext() != null)
            icon_appIcon.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));

        // TEXT SIZE
        label_titledescription.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        label_languageName.setAutoSizeTextTypeUniformWithConfiguration(5, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        button_startSolo.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        button_startMult.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LANGUAGE
        String appendedLanguage = Locale.getDefault().getDisplayLanguage();
        if (titleScreenViewModel.getLanguageName().equalsIgnoreCase("fr"))
            appendedLanguage += " (" + getString(R.string.titlescreen_beta_label) + ")";
        label_languageName.setText(appendedLanguage);

        // LISTENERS
        button_info.setOnClickListener(v -> showInfoPopup());
        button_settings.setOnClickListener(v -> showSettingsPopup());
        button_language.setOnClickListener(v -> showLanguagesPopup());
        button_msgInbox.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment));
        button_startSolo.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), InvestigationActivity.class);
                    intent.putExtra("lobby", 0);
                    startActivity(intent);
                }
        );

        button_startMult.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), InvestigationActivity.class);
                    intent.putExtra("lobby", 1);
                    startActivity(intent);
                }
        );


        //TODO Create a button hyperlink to app review page
        // CREATE AD
        if (getActivity() != null) {
            MobileAds.initialize(getActivity(), initializationStatus -> {
            });
            AdView mAdView = view.findViewById(R.id.adView);
            if (!titleScreenViewModel.hasAdRequest()) {
                titleScreenViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            mAdView.loadAd(titleScreenViewModel.getAdRequest());
        }

        // REQUEST REVIEW LISTENER
        if (titleScreenViewModel != null && titleScreenViewModel.getReviewRequestData().getTimesOpened() > 2) {
            button_review.setOnClickListener(v -> showReviewPopup(getView()));
        } else {
            button_review.setEnabled(false);
            button_review.setVisibility(View.INVISIBLE);
        }
        // REQUEST REVIEW PROMPT
        requestReview();

        if(isNetworkAvailable())
            startInboxThread();
        else
            Log.d("registerMessageInboxes", "Could not load Document data");
    }

    private boolean isNetworkAvailable() {

        if(getActivity() != null) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                Log.d("isNetworkAvailable", "Network Available: Determining Network type...");

                if (!connectivityManager.isActiveNetworkMetered()) {

                    Log.d("isNetworkAvailable", "Connected to Wifi Network: Connection success!");
                    return true;

                } else {

                    Log.d("isNetworkAvailable", "Connected to Metered Network: Checking if allowed to use...");

                    if (!titleScreenViewModel.getNetworkPreference()) {
                        Log.d("isNetworkAvailable", "Metered Network Disallowed: Unable to connect.");
                        return false;
                    } else {
                        Log.d("isNetworkAvailable", "Metered Network Allowed: Connection success!");
                        return true;
                    }

                }
            } else {

                Log.d("isNetworkAvailable", "Network Unavailable: Unable to connect.");
                return false;

            }
        }

        Log.d("isNetworkAvailable", "Activity read as null. Connection forcefully rejected.");
        return false;
    }

    public void startInboxThread() {
        Thread messageCenterThread = new Thread(() -> {
            while (!messageCenterViewModel.isUpToDate()) {
                Log.d("registerMessageInboxes", "Attempting to load documents...");
                registerMessageInboxes();
                if (!messageCenterViewModel.isUpToDate()) {
                    Log.d("registerMessageInboxes", "Read attempts failed!");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d("registerMessageInboxes", "Document load completed.");
        });
        messageCenterThread.start();
    }

    public void registerMessageInboxes() {
        try {
            XmlPullParserFactory xmlPullParserFactory_gen = XmlPullParserFactory.newInstance();
            RSSParser rss_gen = new RSSParser(xmlPullParserFactory_gen,getResources().getString(R.string.preference_general_news_link), MessageCenterViewModel.InboxType.GENERAL, messageCenterViewModel);

            XmlPullParserFactory xmlPullParserFactory_pet = XmlPullParserFactory.newInstance();
            RSSParser rss_pet = new RSSParser(xmlPullParserFactory_pet,getResources().getString(R.string.preference_pet_changelog_link), MessageCenterViewModel.InboxType.PET, messageCenterViewModel);

            XmlPullParserFactory xmlPullParserFactory_phas = XmlPullParserFactory.newInstance();
            RSSParser rss_phas = new RSSParser(xmlPullParserFactory_phas, getResources().getString(R.string.preference_phasmophobia_changelog_link), MessageCenterViewModel.InboxType.PHASMOPHOBIA, messageCenterViewModel);

            messageCenterViewModel.setIsUpToDate(true);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * doReviewRequest method
     */
    public void requestReview() {
        //Log.d("Review", titleScreenViewModel.getReviewRequestData().canRequestReview() + " " + titleScreenViewModel.getReviewRequestData().getTimesOpened());

        if (titleScreenViewModel != null && titleScreenViewModel.getReviewRequestData().canRequestReview()) {
            //Log.d("Review", "Review Request Accepted");
            Thread tempThread = new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null && getContext() != null) {
                    titleScreenViewModel.getReviewRequestData().setWasRequested(true);
                    getActivity().runOnUiThread(() -> showReviewPopup(getView()));
                    if (getContext() != null)
                        titleScreenViewModel.saveToFile(getContext());
                }
            });
            tempThread.start();
        } else {
            // Log.d("Review", "Review Request Denied");
        }
    }


    /**
     * showInfoPopup method
     */
    private void showInfoPopup() {

        // DESTROY PREVIOUS POPUP
        if (popup != null)
            popup.dismiss();


        // INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_aboutinfo, null);


        // CREATE POPUP WINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );


        // INITIALIZE VIEWS
        AppCompatImageButton closeButton = customView.findViewById(R.id.popup_close_button);

        AppCompatTextView title = customView.findViewById(R.id.label_settingstitle);
        AppCompatTextView version = customView.findViewById(R.id.label_version);
        AppCompatTextView aboutapp_info = customView.findViewById(R.id.aboutinfo_aboutapp_info);
        AppCompatTextView developerInfo_title = customView.findViewById(R.id.aboutinfo_developerinfo_title);
        AppCompatTextView discordLabel = customView.findViewById(R.id.appinfo_joinDiscord);

        LinearLayout linearLayout_developerInfo_subtitles = customView.findViewById(R.id.appinfo_developerinfo_subtitles);
        LinearLayout linearLayout_developerInfo_subinfo = customView.findViewById(R.id.appinfo_developerinfo_subinfo);

        View appinfo_discordclickbutton = customView.findViewById(R.id.appinfo_discordclickbox);

        AppCompatTextView specialThanks_title = customView.findViewById(R.id.aboutinfo_specialthanks_title);
        LinearLayout linearLayout_specialThanks = customView.findViewById(R.id.scrollview_list_specialthanks);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.aboutinfo_specialthanks_list);
        AppCompatTextView[] names = new AppCompatTextView[typedArray.length()];


        // LISTENERS
        closeButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;
        });
        appinfo_discordclickbutton.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/" + getString(R.string.aboutinfo_discordInvite)))));


        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        version.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        aboutapp_info.setAutoSizeTextTypeUniformWithConfiguration(16, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        developerInfo_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        specialThanks_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        discordLabel.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // ABOUT APP - TITLE
        String abouttitle = getResources().getString(R.string.aboutinfo_title_about);
        Spannable aboutPET = (Spannable) Html.fromHtml(getResources().getString(R.string.aboutinfo_title_petstylized));
        title.setText(TextUtils.concat(abouttitle, " ", aboutPET));

        // ABOUT APP - VERSION
        String versionData = getResources().getString(R.string.aboutinfo_version) + ": ";
        try {
            if (getContext() != null) {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                versionData += pInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionData = "Version Unknown";
        }
        version.setText(versionData);

        // ABOUT APP - DESCRIPTION
        aboutapp_info.setText(Html.fromHtml(getResources().getString(R.string.aboutinfo_aboutapp_info)));


        // DEVELOPER DATA
        @ColorInt int color_title, color_body;
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.titleFontColor, typedValue, true);
        color_title = typedValue.data;
        String colorHex = String.format("%06X", (0xFFFFFF & color_title));
        theme.resolveAttribute(R.attr.bodyFontColor, typedValue, true);
        color_body = typedValue.data;

        String[] subTitles = getResources().getStringArray(R.array.aboutinfo_developerinfo_subtitles);
        String[] subInfo = getResources().getStringArray(R.array.aboutinfo_developerinfo_subinfo);

        for (int i = 0; i < subTitles.length; i++) {

            AppCompatTextView developerInfo_subtitle = new AppCompatTextView(getContext());
            Spannable subtitle = (Spannable) Html.fromHtml(FontStyler.setColor(subTitles[i], colorHex));
            Spannable subinfo = (Spannable) Html.fromHtml(subInfo[i]);

            developerInfo_subtitle.setText(TextUtils.concat(subtitle, ":"));
            developerInfo_subtitle.setTypeface(bodyFont);
            developerInfo_subtitle.setTextColor(color_body);
            developerInfo_subtitle.setMaxLines(1);
            developerInfo_subtitle.setEllipsize(null);
            developerInfo_subtitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            developerInfo_subtitle.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            if (getView() != null)
                developerInfo_subtitle.setAutoSizeTextTypeUniformWithConfiguration(1, (int) (FontStyler.dpToSp(getView(), developerInfo_title.getTextSize())), 1, TypedValue.COMPLEX_UNIT_SP);

            AppCompatTextView developerInfo_subinfo = new AppCompatTextView(getContext());
            developerInfo_subinfo.setText(subinfo);
            developerInfo_subinfo.setTypeface(bodyFont);
            developerInfo_subinfo.setTextColor(color_body);
            developerInfo_subinfo.setMaxLines(1);
            developerInfo_subinfo.setEllipsize(null);
            developerInfo_subinfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f));
            developerInfo_subinfo.setGravity(Gravity.CENTER);
            if (getView() != null)
                developerInfo_subinfo.setAutoSizeTextTypeUniformWithConfiguration(1, (int) (FontStyler.dpToSp(getView(), developerInfo_subtitle.getTextSize()) * .95), 1, TypedValue.COMPLEX_UNIT_SP);

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
            names[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            names[i].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            names[i].setTextSize((int) (FontStyler.dpToSp(customView, specialThanks_title.getTextSize()) * .9));

            linearLayout_specialThanks.addView(names[i]);
        }
        typedArray.recycle();


        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);
        popup.showAtLocation(getView(), Gravity.CENTER_VERTICAL, 0, 0);

    }


    /**
     * showSettingsPopup method
     */
    private void showSettingsPopup() {

        // DESTROY PREVIOUS POPUP
        if (popup != null)
            popup.dismiss();

        // INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_settings, null);

        // CREATE POPUPWINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        // INITIALIZE VIEWS
        AppCompatTextView primarytitle = customView.findViewById(R.id.label_settingstitle);
        AppCompatTextView generalsettings_title = customView.findViewById(R.id.settings_generalsettings_title);
        AppCompatTextView othersettings_title = customView.findViewById(R.id.settings_othersettings_title);

        AppCompatTextView switch_screenIsAlwaysOn_title = customView.findViewById(R.id.switch_alwayson_text);

        AppCompatTextView switch_network_title = customView.findViewById(R.id.switch_network_text);

        AppCompatTextView text_colorblindmode_title = customView.findViewById(R.id.colorblindmode_title);
        AppCompatTextView text_colorblindmode_selectedname = customView.findViewById(R.id.colorblindmode_selectedname);
        AppCompatTextView text_colorblindmode_sidenote = customView.findViewById(R.id.colorblindmode_note);

        AppCompatTextView switch_huntwarningaudio_text = customView.findViewById(R.id.switch_huntwarningaudio_text);
        AppCompatTextView switch_huntwarning_title = customView.findViewById(R.id.switch_huntwarningaudio_title);
        AppCompatTextView switch_huntwarning_timetext = customView.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        AppCompatTextView switch_huntwarning_othertext = customView.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        SwitchCompat switch_isAlwaysOn_switch = customView.findViewById(R.id.switch_alwayson_switch);
        SwitchCompat switch_network_switch = customView.findViewById(R.id.switch_network_switch);
        SwitchCompat switch_huntwarningaudio_switch = customView.findViewById(R.id.switch_huntwarningaudio_switch);

        SeekBar seekBar_huntwarningTimeout = customView.findViewById(R.id.settings_huntwarning_seekbar);

        ImageButton btn_colorblindMode_left = customView.findViewById(R.id.colorblindmode_leftbutton);
        ImageButton btn_colorblindMode_right = customView.findViewById(R.id.colorblindmode_rightbutton);
        ImageButton closeButton = customView.findViewById(R.id.popup_close_button);

        // TEXT SIZE
        primarytitle.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        generalsettings_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        othersettings_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        switch_screenIsAlwaysOn_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        switch_network_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        text_colorblindmode_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        text_colorblindmode_selectedname.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        text_colorblindmode_sidenote.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        switch_huntwarningaudio_text.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        switch_huntwarning_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        switch_huntwarning_timetext.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        switch_huntwarning_othertext.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // COLORBLIND DATA
        TypedArray typedArray = getResources().obtainTypedArray(R.array.settings_colorblindnessmode_array);
        String[] colorspaceNames = new String[typedArray.length()];
        for (int i = 0; i < colorspaceNames.length; i++)
            colorspaceNames[i] = typedArray.getString(i);
        typedArray.recycle();
        ColorThemesData colorSpaceData = new ColorThemesData(colorspaceNames);
        int oldIndex = 0;
        if (titleScreenViewModel != null)
            oldIndex = titleScreenViewModel.getColorSpace();
        colorSpaceData.setIndex(oldIndex);
        text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());

        // LISTENERS
        btn_colorblindMode_left.setOnClickListener(v -> {
            colorSpaceData.iterate(-1);
            text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());
            if (titleScreenViewModel != null)
                titleScreenViewModel.setColorSpace(colorSpaceData.getIndex());

            SharedPreferences sharedPref = requireActivity().getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.preference_colorSpace), titleScreenViewModel.getColorSpace());
            editor.apply();
            editor.commit();

            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).changeTheme(titleScreenViewModel.getColorSpace());
        });

        btn_colorblindMode_right.setOnClickListener(v -> {
            colorSpaceData.iterate(1);
            text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());
            if (titleScreenViewModel != null)
                titleScreenViewModel.setColorSpace(colorSpaceData.getIndex());

            SharedPreferences sharedPref = requireActivity().getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.preference_colorSpace), titleScreenViewModel.getColorSpace());
            editor.apply();
            editor.commit();

            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).changeTheme(titleScreenViewModel.getColorSpace());
        });


        // SWITCHES
        if (titleScreenViewModel != null) {
            // Screen Always On
            if (switch_isAlwaysOn_switch != null) {
                switch_isAlwaysOn_switch.setChecked(titleScreenViewModel.getIsAlwaysOn());
                switch_isAlwaysOn_switch.setOnClickListener(v -> {
                    if (titleScreenViewModel != null)
                        titleScreenViewModel.setIsAlwaysOn(switch_isAlwaysOn_switch.isChecked());
                    if (getView() != null)
                        getView().setKeepScreenOn(true);
                });
            }
            // Allow Mobile Data
            if (switch_network_switch != null) {
                switch_network_switch.setChecked(titleScreenViewModel.getNetworkPreference());
                switch_network_switch.setOnClickListener(v -> {
                    if (titleScreenViewModel != null)
                        titleScreenViewModel.setNetworkPreference(switch_network_switch.isChecked());
                });
            }
            // Allow Hunt Warning Audio
            if (switch_huntwarningaudio_switch != null) {
                switch_huntwarningaudio_switch.setChecked(titleScreenViewModel.getIsHuntAudioAllowed());
                switch_huntwarningaudio_switch.setOnClickListener(v -> {
                    if (titleScreenViewModel != null)
                        titleScreenViewModel.setAllowHuntWarningAudio(switch_huntwarningaudio_switch.isChecked());
                });
            }
        }


        // TIMEOUT SEEKBAR
        if (titleScreenViewModel != null) {
            if (seekBar_huntwarningTimeout != null) {
                seekBar_huntwarningTimeout.setMax(300001);
                if (titleScreenViewModel.getHuntWarningFlashTimeout() < 0)
                    seekBar_huntwarningTimeout.setProgress(seekBar_huntwarningTimeout.getMax());
                else
                    seekBar_huntwarningTimeout.setProgress(titleScreenViewModel.getHuntWarningFlashTimeout());

                seekBar_huntwarningTimeout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {

                            titleScreenViewModel.setHuntWarningFlashTimeout(progress);

                            double progressMax = 300000 / (double) seekBar_huntwarningTimeout.getMax();

                            if (/*progress > 0 &&*/ progress < seekBar_huntwarningTimeout.getMax()) {
                                long breakdown = (long) (progressMax * progress / 1000L);
                                long minutes = breakdown / 60L;
                                long seconds = breakdown % 60L;
                                String text = String.format(
                                        "%sm %ss",
                                        new DecimalFormat("0").format(minutes),
                                        new DecimalFormat("00").format(seconds));

                                switch_huntwarning_timetext.setText(text);
                                switch_huntwarning_othertext.setText("");
                            } else if (progress == seekBar_huntwarningTimeout.getMax()) {
                                switch_huntwarning_timetext.setText("");
                                switch_huntwarning_othertext.setText(R.string.settings_huntwarningflashtimeout_never);
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                });

                double progressMax = 300000 / (double) seekBar_huntwarningTimeout.getMax();

                if (seekBar_huntwarningTimeout.getProgress() >= 0 && seekBar_huntwarningTimeout.getProgress() < seekBar_huntwarningTimeout.getMax()) {
                    long breakdown = (long) (progressMax * seekBar_huntwarningTimeout.getProgress() / 1000L);
                    long minutes = breakdown / 60L;
                    long seconds = breakdown % 60L;
                    String text = String.format(
                            "%s:%s",
                            new DecimalFormat("0").format(minutes),
                            new DecimalFormat("00").format(seconds));

                    switch_huntwarning_othertext.setText("");
                    switch_huntwarning_timetext.setText(text);
                } else {
                    switch_huntwarning_othertext.setText(R.string.settings_huntwarningflashtimeout_never);
                    switch_huntwarning_timetext.setText("");
                }
            }
        }

        // CLOSE BUTTON
        int finalOldIndex = oldIndex;
        if (closeButton != null)
            closeButton.setOnClickListener(v -> {
                popup.dismiss();
                popup = null;

                if (titleScreenViewModel != null && finalOldIndex != titleScreenViewModel.getColorSpace()) {
                    titleScreenViewModel.setCanRefresh(true);
                    refreshFragment();
                }
            });
        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);
        popup.showAtLocation(getView(), Gravity.CENTER_VERTICAL, 0, 0);
    }


    /**
     * showReviewPopup method
     */
    public void showReviewPopup(View parentView) {

        //DESTROY PREVIOUS POPUP
        if (popup != null)
            popup.dismiss();

        //INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_requestreview, null);

        // INITIALIZE POPUPWINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        popup.setOutsideTouchable(false);

        AppCompatTextView title = customView.findViewById(R.id.label_ratingstitle);
        AppCompatTextView acceptButton = customView.findViewById(R.id.label_accept);
        AppCompatTextView declineButton = customView.findViewById(R.id.label_decline);

        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        acceptButton.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        declineButton.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        acceptButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;

            //THIS IS FOR THE IN APP REVIEW
            if (getContext() != null) {
                ReviewManager manager =
                        //new FakeReviewManager(getContext());
                        ReviewManagerFactory.create(getContext());
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(requestTask -> {
                    if (requestTask.isSuccessful()) {
                        Log.e("ReviewManager", "Task Successful");
                        // We can get the ReviewInfo object
                        ReviewInfo reviewInfo = requestTask.getResult();
                        if (getActivity() != null) {
                            Task<Void> flow = manager.launchReviewFlow(getActivity(), reviewInfo);
                            flow.addOnCompleteListener(flowTask -> {
                                // The flow has finished. The API does not indicate whether the user
                                // reviewed or not, or even whether the review dialog was shown. Thus, no
                                // matter the result, we continue our app flow.
                            });
                        }
                    } else {
                        //Log.e("ReviewManager", "Task Failed");
                        if (requestTask.getException() != null)
                            (requestTask.getException()).printStackTrace();

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getResources().getString(R.string.review_storelink)));
                        intent.setPackage("com.android.vending");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        declineButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;
        });

        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);
        //Log.d("Review", "Preparing to display");
        boolean success = parentView.post(() -> {
            //Log.d("Review", "Is displaying");
            popup.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0);
        });
        //Log.d("Review", (success ? "SUCCESSFUL" : "UNSUCCESSFUL"));
    }

    /**
     * showLanguagesPopup method
     */
    public void showLanguagesPopup() {

        // DESTROY PREVIOUS POPUP
        if (popup != null)
            popup.dismiss();

        // INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_languages, null);

        // CREATE POPUPWINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        // INITIALIZE VIEWS
        AppCompatTextView title = customView.findViewById(R.id.label_languages);
        ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
        LinearLayout languages_layout = customView.findViewById(R.id.languages_verticallayout);

        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        closeButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;
        });

        // DATA
        String[] langNames = getResources().getStringArray(R.array.languages_name);
        if (getContext() != null) {
            for (int i = 0; i < langNames.length; i++) {
                AppCompatTextView name = new AppCompatTextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                name.setGravity(Gravity.CENTER);
                name.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    name.setTypeface(getResources().getFont(R.font.norse_regular), Typeface.NORMAL);
                else
                    name.setTypeface(ResourcesCompat.getFont(getContext(), R.font.norse_regular));
                name.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
                name.setTextColor(Color.WHITE);
                name.setText(langNames[i]);

                int loc = i;
                name.setOnClickListener(v -> {
                    if (titleScreenViewModel != null) {
                        titleScreenViewModel.setLanguage(loc, getResources().getStringArray(R.array.languages_abbreviation));
                        titleScreenViewModel.setCanRefresh(true);
                    }
                    configureLanguage();
                    popup.dismiss();
                    popup = null;
                    refreshFragment();
                });
                languages_layout.addView(name);
            }
        }

        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);
        popup.showAtLocation(getView(), Gravity.CENTER_VERTICAL, 0, 0);
    }

    /**
     * startAnimInitThreads method
     */
    private void startAnimInitThreads() {

        if (animInitThread == null) {
            animInitThread = new Thread() {
                public void run() {
                    animationView.init(titleScreenViewModel, bitmapUtils);
                    animationView.buildImages();
                    animationView.buildData();
                }
            };
            animInitThread.start();
        }
        if (initReadyThread == null) {
            initReadyThread = new Thread(() -> {
                while (!canRunAnim) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startAnimThreads();
            });
            initReadyThread.start();
        }
    }

    /**
     * startAnimTickThread method
     */
    private void startAnimTickThread() {
        if (animTickThread == null) {
            canRunAnim = true;
            animTickThread = new Thread() {
                public void run() {
                    while (canRunAnim) {
                        try {
                            animationView.tick();

                            long now = System.nanoTime();
                            long updateTime = System.nanoTime() - now;
                            double TARGET_FPS = 30;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && (getContext() != null && getContext().getDisplay() != null)) {
                                TARGET_FPS = getContext().getDisplay().getRefreshRate();
                                if (TARGET_FPS > 60)
                                    TARGET_FPS = 60;
                            }
                            long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                            long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                            if (wait < 0)
                                wait = 1;

                            Thread.sleep(wait);

                        } catch (InterruptedException e) {
                            //Log.e("TitleScreenFragment", "InterruptedException error handled.");
                        }
                    }
                }
            };
            animTickThread.start();
        }
    }

    /**
     * startAnimDrawThread method
     */
    private void startAnimDrawThread() {
        if (animDrawThread == null) {
            animDrawThread = new Thread() {
                public void run() {
                    while (canRunAnim) {
                        try {
                            if (getActivity() != null)
                                getActivity().runOnUiThread(() -> animationView.invalidate());

                            long now = System.nanoTime();
                            long updateTime = System.nanoTime() - now;
                            double TARGET_FPS = 30;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && (getContext() != null && getContext().getDisplay() != null)) {
                                TARGET_FPS = getContext().getDisplay().getRefreshRate();
                                if (TARGET_FPS > 60)
                                    TARGET_FPS = 60;
                            }
                            long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                            long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                            if (wait < 0)
                                wait = 1;

                            Thread.sleep(wait);

                        } catch (InterruptedException e) {
                            //Log.e("TitleScreenFragment", "InterruptedException error handled.");
                        }
                    }
                }
            };
            animDrawThread.start();
        }
    }

    /**
     * stopAnimInitThreads method
     */
    public void stopAnimInitThreads() {
        if (animInitThread != null) {
            animInitThread.interrupt();
            animInitThread = null;
        }
        if (initReadyThread != null) {
            initReadyThread.interrupt();
            initReadyThread = null;
        }
    }

    /**
     * stopAnimTickThread method
     */
    public void stopAnimTickThread() {
        if (animTickThread != null) {
            animTickThread.interrupt();
            animTickThread = null;
        }
    }

    /**
     * stopAnimDrawThread method
     */
    public void stopAnimDrawThread() {
        if (animDrawThread != null) {
            animDrawThread.interrupt();
            animDrawThread = null;
        }
    }

    /**
     * startAnimThreads method
     */
    public void startAnimThreads() {
        startAnimTickThread();
        startAnimDrawThread();
    }

    /**
     * stopAnimThreads method
     */

    public void stopAnimThreads() {
        stopAnimDrawThread();
        stopAnimTickThread();
    }

    /**
     * configureLanguage
     */
    public void configureLanguage() {
        if (titleScreenViewModel != null) {
            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).setLanguage(titleScreenViewModel.getLanguageName());
        }
    }

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        //Log.d("Fragment", "Refreshing");
        if (titleScreenViewModel != null && titleScreenViewModel.canRefresh()) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26)
                ft.setReorderingAllowed(false);
            ft.detach(TitleScreenFragment.this).commitNow();
            ft.attach(TitleScreenFragment.this).commitNow();
            titleScreenViewModel.setCanRefresh(false);
        }
    }

    /**
     * saveStates method
     */
    public void saveStates() {
        if (titleScreenViewModel != null && getContext() != null)
            titleScreenViewModel.saveToFile(getContext());
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {
        //Log.d("Fragment", "Pausing");

        // DESTROY POPUP
        if (popup != null) {
            popup.dismiss();
            popup = null;
        }

        // SAVE PERSISTENT DATA
        saveStates();

        // STOP THREADS
        stopAnimThreads();
        stopAnimInitThreads();
        canRunAnim = false;

        // RECYCLE ANIMATION VIEW
        if (animationView != null)
            animationView.recycleBitmaps();

        super.onPause();
    }

    /**
     * onResume method
     */
    @Override
    public void onResume() {
        //Log.d("Fragment", "Resuming");

        // STOP THREADS
        startAnimInitThreads();
        startAnimThreads();

        super.onResume();
    }

    /**
     * onDestroy method
     */
    @Override
    public void onDestroy() {
        //Log.d("Fragment", "Destroying");

        // DESTROY AD-REQUEST
        if (titleScreenViewModel != null)
            titleScreenViewModel.setAdRequest(null);

        super.onDestroy();
    }

    /**
     * onLowMemory method
     */
    @Override
    public void onLowMemory() {
        //Log.d("Fragment", "Low Memory!");

        // RECYCLE ANIMATION VIEW BITMAPS
        if (animationView != null)
            animationView.recycleBitmaps();

        super.onLowMemory();
    }
}