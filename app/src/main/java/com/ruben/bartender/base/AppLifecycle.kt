package com.ruben.bartender.base

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ruben.bartender.R
import com.ruben.bartender.utils.ApplicationUtility
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Ruben Quadros on 22/10/22
 **/
class AppLifecycle @Inject constructor(@ApplicationContext private val context: Context) : DefaultLifecycleObserver {

    private val exceptionHandler = Thread.UncaughtExceptionHandler { _, e -> handleUncaughtException(e) }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        //Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
    }

    override fun onStop(owner: LifecycleOwner) {
        //Thread.setDefaultUncaughtExceptionHandler(null)
        super.onStop(owner)
    }

    private fun handleUncaughtException(e: Throwable) {
        ApplicationUtility.showToast(context, context.resources.getString(R.string.all_unexpected_crash))
        logException(e)
        //val intent = Intent(this, HomeActivity::class.java)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //startActivity(intent)
    }
}