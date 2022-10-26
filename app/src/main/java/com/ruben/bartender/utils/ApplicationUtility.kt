package com.ruben.bartender.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.ruben.bartender.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by ruben.quadros on 01/03/20.
 **/
object ApplicationUtility {

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

  @ExperimentalCoroutinesApi
  @SuppressLint("InflateParams")
  fun showDialog(context: Context, dialogName: String) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder.setCancelable(false)
    val view = LayoutInflater.from(context).inflate(R.layout.all_dialog_layout, null)
    alertDialogBuilder.setView(view)
    val alertDialog = alertDialogBuilder.create()
    val okButton = view.findViewById<AppCompatTextView>(R.id.successDialogBtn)
    val label = view.findViewById<AppCompatTextView>(R.id.successDialogTv)
    val image = view.findViewById<AppCompatImageView>(R.id.successDialogIv)
    when(dialogName) {
      ApplicationConstants.DRINK_SUCCESS -> {
        label.text = context.resources.getString(R.string.all_drink_success)
        image.setImageResource(R.drawable.drink_success)
      }
      ApplicationConstants.GOOGLE_PAY_INSTALL -> {
        label.text = context.resources.getString(R.string.all_google_pay_install)
        image.setImageResource(R.drawable.googlepay_button_content)
      }
      ApplicationConstants.PHONE_PE_INSTALL -> {
        label.text = context.resources.getString(R.string.all_phone_pe_install)
        //image.setImageResource(R.drawable.phone_pe_button)
      }
    }
    okButton.setOnClickListener {
      when(dialogName) {
        ApplicationConstants.DRINK_SUCCESS -> {
          //val intent = Intent(context, HomeActivity::class.java)
          //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          //context.startActivity(intent)
          alertDialog.dismiss()
        }
        ApplicationConstants.GOOGLE_PAY_INSTALL -> {
          try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ApplicationConstants.GOOGLE_PAY_PACKAGE)))
            alertDialog.dismiss()
          }catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ApplicationConstants.GOOGLE_PAY_PACKAGE)))
            alertDialog.dismiss()
          }
        }
        ApplicationConstants.PHONE_PE_INSTALL -> {
          try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ApplicationConstants.PHONE_PE_PACKAGE)))
            alertDialog.dismiss()
          }catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ApplicationConstants.PHONE_PE_PACKAGE)))
            alertDialog.dismiss()
          }
        }
      }
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