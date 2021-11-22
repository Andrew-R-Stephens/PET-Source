package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.views.MessagesAdapterView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsletterMessagesFragment extends Fragment {

    private NewsletterViewModel messageInboxViewModel = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        if (messageInboxViewModel == null)
            messageInboxViewModel =
                    new ViewModelProvider(requireActivity()).get(NewsletterViewModel.class);

        return inflater.inflate(
                R.layout.fragment_msginbox_listmessages,
                container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);
        RecyclerView recyclerViewMessages = view.findViewById(R.id.recyclerview_messageslist);
        // TEXT SIZE
        label_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        // SET VIEW TEXT
        label_title.setText(messageInboxViewModel.getCurrentInboxType().getName(view.getContext()));
        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        if (messageInboxViewModel != null && messageInboxViewModel.getCurrentInbox() != null) {
            MessagesAdapterView adapter = new MessagesAdapterView(
                    messageInboxViewModel.getCurrentInbox().getMessages(), position -> {
                messageInboxViewModel.setCurrentMessageId(position);
                Navigation.findNavController(view).
                        navigate(R.id.action_inboxMessageListFragment_to_inboxMessageFragment);
            });
            recyclerViewMessages.setAdapter(adapter);
            recyclerViewMessages.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            Log.d("InboxMessageListFrag", "Inbox does not exist!" +
                    messageInboxViewModel.getCurrentInboxType().getName(getContext()));
        }
    }

}