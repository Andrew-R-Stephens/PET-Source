package com.tritiumgaming.data.account.common

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.data.account.R

@StringRes
fun CredentialsResources.CredentialKey.asIdentifier(context: Context): Int = when(this) {
    CredentialsResources.CredentialKey.WEB_CLIENT_ID -> R.string.web_client_id
}