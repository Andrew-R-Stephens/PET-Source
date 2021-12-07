package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
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

    private NewsletterViewModel newsletterViewModel = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (newsletterViewModel == null) {
            newsletterViewModel =
                    new ViewModelProvider(requireActivity()).get(NewsletterViewModel.class);
        }
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

        // SCROLL BAR
        recyclerViewMessages.setScrollBarFadeDuration(-1);

        // SET VIEW TEXT
        label_title.setText(newsletterViewModel.getCurrentInboxType().getName(view.getContext()));

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // SET CONTENT
        if (newsletterViewModel != null && newsletterViewModel.getCurrentInbox() != null) {
            MessagesAdapterView adapter = new MessagesAdapterView(
                    newsletterViewModel.getCurrentInbox().getMessages(), position -> {
                newsletterViewModel.setCurrentMessageId(position);
                Navigation.findNavController(view).
                        navigate(R.id.action_inboxMessageListFragment_to_inboxMessageFragment);
            });
            recyclerViewMessages.setAdapter(adapter);
            recyclerViewMessages.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            if(newsletterViewModel != null && getContext() != null) {
                Log.d("InboxMessageListFrag", "Inbox does not exist!" +
                        newsletterViewModel.getCurrentInboxType().getName(getContext()));
            } else {
                Log.d("InboxMessageListFrag",
                        "Either MessageInboxViewModel does not exist " +
                                "or Context does not exist!");
            }
        }
    }

}