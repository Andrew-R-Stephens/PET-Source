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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_Mirror;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_MusicBox;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_Ouija;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_Pentagram;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_Tarot;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.CodexPosessions_Voodoo;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions.PosessionFragment;

public class CodexPosessionsFragment extends Fragment {

    private int current = 0;

    private PosessionFragment[] frags = {
            new CodexPosessions_Mirror(),
            new CodexPosessions_MusicBox(),
            new CodexPosessions_Ouija(),
            new CodexPosessions_Pentagram(),
            new CodexPosessions_Tarot(),
            new CodexPosessions_Voodoo(),
    };

    private AppCompatTextView posessionsTitle;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities_codex_cursedposessions, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView title = view.findViewById(R.id.textView_fragtitle);
        AppCompatImageView prevCursed = view.findViewById(R.id.imageView_cursed_prev);
        AppCompatImageView nextCursed = view.findViewById(R.id.imageView_cursed_next);
        posessionsTitle = view.findViewById(R.id.posessionsItemTitle);

        title.setText("Cursed Posessions");

        prevCursed.setOnClickListener(v -> {
            current--;
            if(current < 0)
                current = frags.length-1;

            exchangePosessionFragment();
        });

        nextCursed.setOnClickListener(v -> {
            current++;
            if(current >= frags.length)
                current = 0;

            exchangePosessionFragment();
        });

        prev.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        if (getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        exchangePosessionFragment();

    }

    public void exchangePosessionFragment() {
        posessionsTitle.setText((frags[current]).getTitle());

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.childFragment, frags[current]);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();


    }

}