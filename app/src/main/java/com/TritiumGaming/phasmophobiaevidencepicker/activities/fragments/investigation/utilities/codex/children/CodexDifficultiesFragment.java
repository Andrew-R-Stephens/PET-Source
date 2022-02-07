package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class CodexDifficultiesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities_codex_aboutdifficulties, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView title = view.findViewById(R.id.textView_fragtitle);


        title.setText("About Difficulties");


        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());

        if (getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

    }
}