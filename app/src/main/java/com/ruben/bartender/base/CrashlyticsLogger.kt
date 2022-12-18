package com.ruben.bartender.base

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

/**
 * Created by Ruben Quadros on 22/10/22
 **/
fun logException(throwable: Throwable?) {
    throwable?.let {
        Firebase.crashlytics.recordException(it)
    }
}