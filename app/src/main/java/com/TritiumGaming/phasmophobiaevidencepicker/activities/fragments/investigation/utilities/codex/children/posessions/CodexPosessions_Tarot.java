package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class CodexPosessions_Tarot extends PosessionFragment {

    public CodexPosessions_Tarot() {
        super(R.layout.item_utilities_codex_cursedposessions_tarotcards);

        title = "Tarot Cards";
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        AppCompatImageView death = view.findViewById(R.id.tarot_death);
        AppCompatImageView devil = view.findViewById(R.id.tarot_devil);
        AppCompatImageView fortune = view.findViewById(R.id.tarot_fortune);
        AppCompatImageView hangman = view.findViewById(R.id.tarot_hangman);
        AppCompatImageView hermit = view.findViewById(R.id.tarot_hermit);
        AppCompatImageView joker = view.findViewById(R.id.tarot_joker);
        AppCompatImageView moon = view.findViewById(R.id.tarot_moon);
        AppCompatImageView sun = view.findViewById(R.id.tarot_sun);
        AppCompatImageView preistess = view.findViewById(R.id.tarot_preistess);
        AppCompatImageView tower = view.findViewById(R.id.tarot_tower);

        death.setOnClickListener(cardView -> {
            Context context = getContext();
            CharSequence text = "Death Card";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
    }
}
