package com.tritiumgaming.phasmophobiaevidencepicker.core.data.firestore.user

class NullFirebaseUserException : Exception {
    constructor() : super(ERROR_MESSAGE)

    constructor(extendedMessage: String) : super("$ERROR_MESSAGE $extendedMessage")

    companion object {
        private const val ERROR_MESSAGE = "FirebaseUser is null."
    }
}
