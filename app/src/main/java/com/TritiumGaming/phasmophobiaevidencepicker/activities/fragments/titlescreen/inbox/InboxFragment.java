package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class InboxFragment extends Fragment {

    private TitleScreenViewModel titleScreenViewModel = null;

    private PopupWindow popup = null;

    private Typeface bodyFont = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(requireActivity()).get(TitleScreenViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            titleScreenViewModel.init(getContext());

        return inflater.inflate(R.layout.fragment_msginbox, container, false);
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
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatTextView label_extranews = view.findViewById(R.id.textview_extranews);
        AppCompatTextView label_petnews = view.findViewById(R.id.textview_petupdateinbox);
        AppCompatTextView label_phasnews = view.findViewById(R.id.textview_phasupdateinbox);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);
        ConstraintLayout button_extranews = view.findViewById(R.id.constraintLayout_inboxnews_listener);
        ConstraintLayout button_petnews = view.findViewById(R.id.constraintLayout_petnews_listener);
        ConstraintLayout button_phasnews = view.findViewById(R.id.constraintLayout_phasnews_listener);

        // TEXT SIZE
        label_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        label_extranews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
        label_petnews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
        label_phasnews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        button_extranews.setOnClickListener(v -> showGeneralNewsPopup());
        button_petnews.setOnClickListener(v -> showPetNewsPopup());
        button_phasnews.setOnClickListener(v -> showPhasNewsPopup(v));

    }


    /**
     * showExtraNewsPopup method
     */
    private void showGeneralNewsPopup() {

    }


    /**
     * showPetNewsPopup method
     */
    private void showPetNewsPopup() {

    }


    /**
     * showPhasNewsPopup method
     */
    public void showPhasNewsPopup(View v) {
        RSSParser rss = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            rss = new RSSParser(factory,"https://steamcommunity.com/games/739630/rss/");
            while(!rss.isReady()){}
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        if (popup != null)
            popup.dismiss();

        LayoutInflater inflater = (LayoutInflater) getView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_info, null);

        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        ImageButton closeButton = customView.findViewById(R.id.popup_close_button);
        closeButton.setOnClickListener(v1 -> popup.dismiss());

        AppCompatTextView name = customView.findViewById(R.id.label_queryName);
        name.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        name.setText(rss.getTitle(8));
        AppCompatTextView info = customView.findViewById(R.id.label_queryInfo);
        info.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
        info.setText(Html.fromHtml(rss.getDescription(8)));

        popup.setAnimationStyle(R.anim.nav_default_enter_anim);
        popup.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
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

        super.onPause();
    }

    /**
     * onResume method
     */
    @Override
    public void onResume() {
        //Log.d("Fragment", "Resuming");

        // STOP THREADS

        super.onResume();
    }

    /**
     * onDestroy method
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}