package com.TritiumGaming.phasmophobiaevidencepicker.views.account;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.google.firebase.auth.FirebaseUser;

public class AccountLoginRequestView extends ConstraintLayout {

    public AccountLoginRequestView(@NonNull Context context) {
        super(context);

        init(getContext());
    }

    public AccountLoginRequestView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext());
    }

    public AccountLoginRequestView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext());
    }

    public AccountLoginRequestView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext());
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_account_login_request, this);

        FirebaseUser user = null;
        try {
            user = FirestoreUser.getCurrentFirebaseUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContainerVisibility(user);
    }

    public void setMessage(@StringRes int message) {
        setMessage(getContext().getString(message));
    }

    public void setMessage(String message) {
        AppCompatTextView textView = findViewById(R.id.settings_requestlogin_title);
        textView.setText(message);
    }

    private void setContainerVisibility(@Nullable FirebaseUser user) {
        if(user == null) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

}