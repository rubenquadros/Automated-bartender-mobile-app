package com.ruben.bartender.presentation.checkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.all_appbar_layout.*

class PaymentActivity : BaseActivity() {

  @BindView(R.id.cardIv)
  lateinit var cardIV: AppCompatImageView

  @BindView(R.id.cardTv)
  lateinit var cardTV: AppCompatTextView

  @BindView(R.id.cardNextIv)
  lateinit var cardNextBtn: AppCompatImageView

  @BindView(R.id.card_layout)
  lateinit var cardView: View

  @BindString(R.string.all_payments)
  lateinit var payments: String

  private var showCardView = false

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment)
    ButterKnife.bind(this)
    setupToolBar()
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    toolbarTitle.text = payments
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
