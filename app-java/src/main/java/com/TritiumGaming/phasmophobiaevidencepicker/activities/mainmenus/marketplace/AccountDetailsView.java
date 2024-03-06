package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.views.OutlineTextView;
import com.google.firebase.auth.FirebaseUser;

public class AccountDetailsView extends ConstraintLayout {

    public AccountDetailsView(@NonNull Context context) {
        super(context);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext());
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_account_details_2, this);

        FirebaseUser user = null;
        try {
            user = FirestoreUser.getCurrentFirebaseUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setUsernameInitials(user);
        setContainerVisibility(user);
    }

    private void setContainerVisibility(FirebaseUser user) {
        if(user == null) {
            //setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    private void setUsernameInitials(FirebaseUser user) {
        if(user == null) {
            return;
        }

        OutlineTextView textView_initials = findViewById(R.id.label_username_initials);
        if(textView_initials == null) {
            return;
        }

        StringBuilder displayInitials = new StringBuilder();
        String displayName = user.getDisplayName();
        if(displayName != null) {
            String[] names = displayName.split(" ");

            for(String name: names) {

                String trimmedName = name.trim();

                if(trimmedName.length() > 0) {
                    char initial = trimmedName.charAt(0);

                    displayInitials.append(initial);

                    if(displayInitials.length() >= 2) {
                        break;
                    }
                }
            }

            textView_initials.setText(displayInitials);
        }
    }

}
