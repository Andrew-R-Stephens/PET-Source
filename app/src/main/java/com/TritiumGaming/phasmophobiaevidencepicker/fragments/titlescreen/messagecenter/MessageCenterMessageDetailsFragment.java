package com.TritiumGaming.phasmophobiaevidencepicker.fragments.titlescreen.messagecenter;

import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class MessageCenterMessageDetailsFragment extends Fragment {

    private TitlescreenViewModel titleScreenViewModel = null;
    private MessageCenterViewModel messageInboxViewModel = null;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            titleScreenViewModel.init(getContext());

        if (messageInboxViewModel == null)
            messageInboxViewModel = new ViewModelProvider(requireActivity()).get(MessageCenterViewModel.class);

        return inflater.inflate(R.layout.fragment_msginbox_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatTextView label_date = view.findViewById(R.id.textView_messageDate);
        AppCompatTextView label_content = view.findViewById(R.id.textView_messageContent);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);

        // TEXT SIZE
        label_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        label_date.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        //label_content.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // SET CONTENT
        label_title.setText(Html.fromHtml(messageInboxViewModel.getCurrentMessage().getTitle()));
        label_date.setText(Html.fromHtml(messageInboxViewModel.getCurrentMessage().getDate()));
        label_content.setText(Html.fromHtml(messageInboxViewModel.getCurrentMessage().getDescription()));

    }

}