package com.ruben.bartender.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.ruben.bartender.R

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class ApplicationUtility {
  companion object {

    fun showToast(context: Context, message: String) {
      Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showSnack(msg: String, view: View, action: String) {
      val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
      snackBar.setAction(action) {
        snackBar.dismiss()
      }
      snackBar.show()
    }

    @SuppressLint("InflateParams")
    fun showDrinkSuccessDialog(context: Context) {
      val alertDialogBuilder = AlertDialog.Builder(context)
      alertDialogBuilder.setCancelable(false)
      val view = LayoutInflater.from(context).inflate(R.layout.all_drink_success, null)
      alertDialogBuilder.setView(view)
      val alertDialog = alertDialogBuilder.create()
      val okButton = view.findViewById<AppCompatTextView>(R.id.successDialogBtn)
      okButton.setOnClickListener {
        alertDialog.dismiss()
      }
      alertDialog.show()
    }

    fun showProgress(progressBar: ProgressBar, activity: Activity) {
      progressBar.visibility = View.VISIBLE
      activity.window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
      )
    }

    fun stopProgress(progressBar: ProgressBar, activity: Activity) {
      progressBar.visibility = View.GONE
      activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideKeyboard(activity: Activity, view: View) {
      val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showFragment(
      fragment: Fragment,
      isAddToBackStack: Boolean,
      tag: String?,
      bundle: Bundle?,
      fragmentManager: FragmentManager
    ) {
      val fragmentTransaction =
        fragmentManager.beginTransaction()
      fragmentTransaction.add(R.id.boarding_container, fragment, tag)
      if (isAddToBackStack) {
        fragmentTransaction.addToBackStack(null)
      }
      if (bundle != null) {
        fragment.arguments = bundle
      }
      fragmentTransaction.commit()
    }
  }
}