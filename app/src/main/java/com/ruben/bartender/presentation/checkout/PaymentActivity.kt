package com.ruben.bartender.presentation.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.domain.model.CheckGooglePayRecord
import com.ruben.remote.utils.ApiConstants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.all_appbar_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@Suppress("PrivatePropertyName")
class PaymentActivity : BaseActivity() {

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

  private val TAG = javaClass.simpleName
  private lateinit var paymentViewModel: PaymentViewModel
  private var showCardView = false

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment)
    ButterKnife.bind(this)
    setupToolBar()
    setupViewModel()
    observeData()
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    toolbarTitle.text = payments
  }

  private fun setupViewModel() {
    paymentViewModel =
      ViewModelProviders.of(this, viewModelFactory).get(PaymentViewModel::class.java)
    ApplicationUtility.showProgress(progressBar, this)
    paymentViewModel.checkGooglePayAvailability(
      ApplicationConstants.GOOGLE_PAY_TYPE,
      listOf(ApplicationConstants.VISA, ApplicationConstants.MASTERCARD),
      listOf(ApplicationConstants.AUTH_PAN, ApplicationConstants.AUTH_CRYPTOGRAM),
      ApplicationConstants.GOOGLE_PAY_API_VERSION,
      ApplicationConstants.GOOGLE_PAY_API_MINOR_VERSION
    )
  }

  private fun parseCheckGoogleAvailabilityResponse(checkGooglePayRecord: CheckGooglePayRecord?) {
    ApplicationUtility.stopProgress(progressBar, this)
    innerPayment.visibility = View.VISIBLE
    if (checkGooglePayRecord != null) {
      when(checkGooglePayRecord.responseCode) {
        ApiConstants.HTTP_OK -> {
          if(checkGooglePayRecord.isAvailable == null || !checkGooglePayRecord.isAvailable!!) {
            hideGooglePay()
          }
        }
        else -> {
          hideGooglePay()
        }
      }
      Log.d(TAG, checkGooglePayRecord.responseMessage)
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

  private fun observeData() {
    paymentViewModel.getCheckGoogleAvailabilityResponse()
      .observe(this, Observer { it?.let { parseCheckGoogleAvailabilityResponse(it) } })
  }

  @OnClick(value = [R.id.cardIv, R.id.cardTv, R.id.cardNextIv])
  fun onClick(view: View) {
    when (view.id) {
      R.id.cardIv     -> {
        if (!showCardView) {
          cardView.visibility = View.VISIBLE
          showCardView = true
        } else {
          cardView.visibility = View.GONE
          showCardView = false
        }
      }
      R.id.cardTv     -> {
        if (!showCardView) {
          cardView.visibility = View.VISIBLE
          showCardView = true
        } else {
          cardView.visibility = View.GONE
          showCardView = false
        }
      }
      R.id.cardNextIv -> {
        if (!showCardView) {
          cardView.visibility = View.VISIBLE
          showCardView = true
        } else {
          cardView.visibility = View.GONE
          showCardView = false
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
