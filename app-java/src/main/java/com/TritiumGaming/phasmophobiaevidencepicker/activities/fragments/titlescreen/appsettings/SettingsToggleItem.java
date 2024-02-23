package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.views.OutlineTextView;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class SettingsToggleItem extends ConstraintLayout {

    public SettingsToggleItem(@NonNull Context context) {
        super(context);

        init(getContext(), null);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext(), attrs);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext(), attrs);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext(), attrs);
    }

    public void init(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        inflate(context, R.layout.item_settings_toggle, this);

        String description = "";
        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.SettingsToggleItem);

            description =
                    typedArray.getString(R.styleable.SettingsToggleItem_settings_toggle_description);
            setDescription(description);

            typedArray.recycle();
        }

    }

    public void setDescription(String text) {
        AppCompatTextView label = findViewById(R.id.switch_description);
        if(label != null) {
            label.setText(text);
        }
    }

    public void setSwitchClickListener(OnClickListener listener) {
        SwitchCompat toggleSwitch = findViewById(R.id.switch_toggle);

        if(toggleSwitch != null) {
            toggleSwitch.setOnClickListener(listener);
        }
    }

    public void setChecked(boolean isChecked) {
        SwitchCompat toggleSwitch = findViewById(R.id.switch_toggle);

        if(toggleSwitch != null) {
            toggleSwitch.setChecked(isChecked);
        }
    }

    public boolean isChecked() {
        SwitchCompat toggleSwitch = findViewById(R.id.switch_toggle);

        if(toggleSwitch != null) {
            return toggleSwitch.isChecked();
        }
        return false;
    }

}
