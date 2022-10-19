package com.ruben.bartender.presentation.checkout

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.payments.CheckGooglePayResponse
import com.ruben.bartender.payments.GooglePayClient
import com.ruben.bartender.payments.PaymentConstants
import com.ruben.bartender.presentation.home.HomeViewModel
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.bartender.data.remote.utils.ApiConstants
import com.ruben.bartender.domain.model.MakeDrinkRecord
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.all_appbar_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@Suppress("PrivatePropertyName")
class PaymentActivity : BaseActivity(), LifecycleObserver {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @BindView(R.id.payment_parent)
  lateinit var parentView: ConstraintLayout

  @BindView(R.id.paymentsInnerParent)
  lateinit var innerPayment: ConstraintLayout

  @BindView(R.id.upiView1)
  lateinit var gpayView: View

  @BindView(R.id.gpayIv)
  lateinit var gpayIv: AppCompatImageView

  @BindView(R.id.gpayTv)
  lateinit var gpayTv: AppCompatTextView

  @BindView(R.id.gpayNextIv)
  lateinit var gpayNextIv: AppCompatImageView

  @BindView(R.id.card_layout)
  lateinit var cardView: View

  @BindView(R.id.paymentProgressBar)
  lateinit var progressBar: ProgressBar

  @BindString(R.string.all_payments)
  lateinit var payments: String

  @BindString(R.string.all_generic_err)
  lateinit var errorMessage: String

  @BindString(R.string.all_ok)
  lateinit var ok: String

  private val TAG = javaClass.simpleName
  private lateinit var homeViewModel: HomeViewModel
  private lateinit var googlePayClient: GooglePayClient
  private lateinit var drinkName: String
  private lateinit var price: String
  private var showCardView = false

  companion object {
    @JvmStatic
    fun newIntent(drinkName: String, price: String, context: Context): Intent {
      val intent = Intent(context, PaymentActivity::class.java)
      intent.putExtra(ApplicationConstants.DRINK_NAME, drinkName)
      intent.putExtra(ApplicationConstants.PRICE, price)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment)
    ButterKnife.bind(this)
    googlePayClient = GooglePayClient(this)
    setupToolBar()
    setupViewModel()
    readIntent()
    checkGooglePayAvailability()
    observeData()
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    toolbarTitle.text = payments
  }

  private fun setupViewModel() {
    homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
  }

  private fun readIntent() {
    if (intent.getStringExtra(ApplicationConstants.DRINK_NAME) != null) {
      this.drinkName = intent.getStringExtra(ApplicationConstants.DRINK_NAME)!!
    }
    if (intent.getStringExtra(ApplicationConstants.PRICE) != null) {
      this.price = intent.getStringExtra(ApplicationConstants.PRICE)!!
    }
  }

  private fun checkGooglePayAvailability() {
    ApplicationUtility.showProgress(progressBar, this)
    lifecycleScope.launch {
      googlePayClient.isGooglePayReady().flowOn(Dispatchers.IO).collect {
        parseCheckGooglePayResponse(it)
      }
    }
  }

  private fun observeData() {
    homeViewModel.observeMakeDrink()
      .observe(this, Observer { it?.let { parseMakeDrinkResponse(it) } })
  }

  private fun parseMakeDrinkResponse(makeDrinkRecord: MakeDrinkRecord?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if (makeDrinkRecord != null) {
      when (makeDrinkRecord.responseCode) {
        ApiConstants.HTTP_OK        -> {
          ApplicationUtility.showDialog(this, ApplicationConstants.DRINK_SUCCESS)
        }
        ApiConstants.HTTP_NEW_USER  -> {
          ApplicationUtility.showSnack(makeDrinkRecord.responseMessage, parentView, ok)
        }
        ApiConstants.HTTP_API_FAIL  -> {
          ApplicationUtility.showSnack(
            this.resources.getString(R.string.all_generic_err),
            parentView,
            this.resources.getString(R.string.all_ok)
          )
        }
        ApiConstants.HTTP_CON_ERROR -> {
          ApplicationUtility.showSnack(
            this.resources.getString(R.string.all_cannot_make_drink),
            parentView,
            this.resources.getString(R.string.all_ok)
          )
        }
        else                        -> {
          ApplicationUtility.showSnack(errorMessage, parentView, ok)
        }
      }
      Log.d(TAG, makeDrinkRecord.responseMessage)
    } else {
      ApplicationUtility.showSnack(
        this.resources.getString(R.string.all_generic_err),
        parentView,
        this.resources.getString(R.string.all_ok)
      )
    }
  }

  private fun parseCheckGooglePayResponse(checkGooglePayResponse: CheckGooglePayResponse?) {
    ApplicationUtility.stopProgress(progressBar, this)
    innerPayment.visibility = View.VISIBLE
    if (checkGooglePayResponse != null) {
      when (checkGooglePayResponse.status) {
        ApiConstants.HTTP_OK -> {
          if (checkGooglePayResponse.isAvailable != null) {
            if (!checkGooglePayResponse.isAvailable!!) {
              hideGooglePay()
            }
          } else {
            hideGooglePay()
          }
        }
        else                 -> {
          hideGooglePay()
        }
      }
    } else {
      hideGooglePay()
    }
  }

  private fun hideGooglePay() {
    gpayView.visibility = View.GONE
    gpayIv.visibility = View.GONE
    gpayTv.visibility = View.GONE
    gpayNextIv.visibility = View.GONE
  }

  private fun showCardView() {
    cardView.visibility = View.VISIBLE
    showCardView = true
  }

  private fun hideCardView() {
    cardView.visibility = View.GONE
    showCardView = false
  }

  private fun payWithUPI(appName: String) {
    ApplicationUtility.showProgress(progressBar, this)
    val uri =
      Uri.Builder()
        .scheme(PaymentConstants.SCHEME)
        .authority(PaymentConstants.AUTHORITY)
        .appendQueryParameter(PaymentConstants.UPI_ID, PaymentConstants.MERCHANT_UPI_ID)
        .appendQueryParameter(
          PaymentConstants.GOOGLE_PAY_MERCHANT_NAME,
          PaymentConstants.MERCHANT_NAME
        )
        .appendQueryParameter(
          PaymentConstants.GOOGLE_PAY_TRANSACTION_NOTE,
          PaymentConstants.NOTE
        )
        .appendQueryParameter(PaymentConstants.GOOGLE_PAY_AMOUNT, price)
        .appendQueryParameter(PaymentConstants.GOOGLE_PAY_CURRENCY, PaymentConstants.CURRENCY)
        .build()
    if (ApplicationConstants.GOOGLE_PAY == appName) {
      try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(PaymentConstants.GOOGLE_PAY_PACKAGE_NAME)
        startActivityForResult(intent, PaymentConstants.LOAD_PAYMENT_DATA_REQUEST_CODE)
      } catch (e: ActivityNotFoundException) {
        ApplicationUtility.showDialog(this, ApplicationConstants.GOOGLE_PAY_INSTALL)
      }
    } else {
      try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(PaymentConstants.PHONE_PAY_PACKAGE_NAME)
        startActivityForResult(intent, PaymentConstants.LOAD_PAYMENT_DATA_REQUEST_CODE)
      } catch (e: ActivityNotFoundException) {
        ApplicationUtility.showDialog(this, ApplicationConstants.PHONE_PE_INSTALL)
      }
    }
  }

  @OnClick(value = [R.id.cardIv, R.id.cardTv, R.id.cardNextIv, R.id.gpayIv, R.id.gpayTv, R.id.gpayNextIv, R.id.phonePeIv, R.id.phonePeTv, R.id.phonePeNextIv])
  fun onClick(view: View) {
    when (view.id) {
      R.id.cardIv        -> {
        if (!showCardView) {
          showCardView()
        } else {
          hideCardView()
        }
      }
      R.id.cardTv        -> {
        if (!showCardView) {
          showCardView()
        } else {
          hideCardView()
        }
      }
      R.id.cardNextIv    -> {
        if (!showCardView) {
          showCardView()
        } else {
          hideCardView()
        }
      }
      R.id.gpayIv        -> {
        payWithUPI(ApplicationConstants.GOOGLE_PAY)
      }
      R.id.gpayTv        -> {
        payWithUPI(ApplicationConstants.GOOGLE_PAY)
      }
      R.id.gpayNextIv    -> {
        payWithUPI(ApplicationConstants.GOOGLE_PAY)
      }
      R.id.phonePeIv     -> {
        payWithUPI(ApplicationConstants.PHONE_PE)
      }
      R.id.phonePeTv     -> {
        payWithUPI(ApplicationConstants.PHONE_PE)
      }
      R.id.phonePeNextIv -> {
        payWithUPI(ApplicationConstants.PHONE_PE)
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    ApplicationUtility.stopProgress(progressBar, this)
    when (requestCode) {
      PaymentConstants.LOAD_PAYMENT_DATA_REQUEST_CODE -> {
        when (resultCode) {
          Activity.RESULT_OK       -> {
            if (data != null && data.extras != null) {
              if (data.getStringExtra(PaymentConstants.GOOGLE_PAY_STATUS) != null && data.getStringExtra(
                  PaymentConstants.GOOGLE_PAY_STATUS
                ) == PaymentConstants.GOOGLE_PAY_SUCCESS
              ) {
                Log.d(TAG, " " + data.getStringExtra(PaymentConstants.GOOGLE_PAY_TXN_ID))
                Log.d(TAG, " " + data.getStringExtra(PaymentConstants.GOOGLE_PAY_TXN_REF))
                homeViewModel.makeDrink(drinkName)
              }
            } else {
              ApplicationUtility.showSnack(errorMessage, parentView, ok)
            }
          }
          Activity.RESULT_CANCELED -> {
            Log.d(TAG, "CANCELLED PAY")
          }
          else                     -> {
            ApplicationUtility.showSnack(errorMessage, parentView, ok)
          }
        }
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    super.onSupportNavigateUp()
    onBackPressed()
    return true
  }
}
