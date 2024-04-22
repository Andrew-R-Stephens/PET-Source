package com.tritiumstudios.phasmophobiaevidencetool.android.views.account;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.FirestoreUser;
import com.google.firebase.auth.FirebaseUser;

public class AccountDetailsView extends ConstraintLayout {

    public AccountDetailsView(@NonNull Context context) {
        super(context);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context,
                              @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context,
                              @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext());
    }

    public AccountDetailsView(@NonNull Context context,
                              @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs,
                              int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext());
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_account_details_2, this);

        FirebaseUser user = FirestoreUser.getCurrentFirebaseUser();

        try {
            setUsernameInitials(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContainerVisibility(user);

    }

    private void setContainerVisibility(@Nullable FirebaseUser user) {
        if(user == null) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    private void setUsernameInitials(@Nullable FirebaseUser user)
            throws Exception {

        if(user == null) {
            throw new FirestoreUser.NullFirebaseUserException();
        }

        AccountIconView accountIcon = findViewById(R.id.account_icon);
        if(accountIcon != null) {
            accountIcon.createAccountInitials(user.getDisplayName());
        }
    }

}
