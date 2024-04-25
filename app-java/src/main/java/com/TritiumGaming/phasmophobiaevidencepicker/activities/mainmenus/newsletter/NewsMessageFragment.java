package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessageData;
import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.views.PETImageButton;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsMessageFragment extends MainMenuFragment {

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        super.init();

        return inflater.inflate(R.layout.fragment_news_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        PETImageButton button_back = view.findViewById(R.id.button_left);
        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_messageTitle);
        AppCompatTextView label_date = view.findViewById(R.id.textView_messageDate);
        AppCompatTextView label_content = view.findViewById(R.id.textView_messageContent);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // SET CONTENT
        NewsletterMessageData message = newsLetterViewModel.getCurrentMessage();
        if (message != null) {
            label_title.setText(Html.fromHtml(message.getTitle()));
            label_date.setText(Html.fromHtml(message.getDate()));
            label_content.setText(Html.fromHtml(message.getDescription()));
        } else {
            label_title.setText(Html.fromHtml("Data unavailable"));
            label_date.setText(Html.fromHtml("Data unavailable"));
            label_content.setText(Html.fromHtml("Data unavailable"));
        }

        super.initAdView(view.findViewById(R.id.adView));
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
        initMainMenuViewModel();
        initNewsletterViewModel();
    }

}