package com.tritiumgaming.data.account.common

fun CredentialsResources.CredentialKey.asIdentifier(): String = when(this) {
    CredentialsResources.CredentialKey.WEB_CLIENT_ID -> "default_web_client_id"
}