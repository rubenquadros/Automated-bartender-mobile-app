package com.ruben.bartender.payments

/**
 * Created by ruben.quadros on 23/03/20.
 **/
object PaymentConstants {
  const val GOOGLE_PAY_API_VERSION = 2
  const val GOOGLE_PAY_API_MINOR_VERSION = 0
  const val GOOGLE_PAY_TYPE = "CARD"
  const val VISA = "VISA"
  const val MASTERCARD = "MASTERCARD"
  const val AUTH_PAN = "PAN_ONLY"
  const val AUTH_CRYPTOGRAM = "CRYPTOGRAM_3DS"
  const val TYPE = "type"
  const val GOOGLE_PAY_PARAMETERS = "parameters"
  const val GOOGLE_PAY_ALLOWED_CARD_NETWORKS = "allowedCardNetworks"
  const val GOOGLE_PAY_ALLOWED_AUTH_METHODS = "allowedAuthMethods"
  const val API_VERSION = "apiVersion"
  const val API_MINOR_VERSION = "apiVersionMinor"
  const val GOOGLE_PAY_ALLOWED_PAYMENT_METHODS = "allowedPaymentMethods"
  const val LOAD_PAYMENT_DATA_REQUEST_CODE = 1001
  const val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
  const val SCHEME = "upi"
  const val AUTHORITY = "pay"
  const val UPI_ID = "pa"
  const val GOOGLE_PAY_MERCHANT_NAME = "pn"
  const val GOOGLE_PAY_AMOUNT = "am"
  const val GOOGLE_PAY_TRANSACTION_NOTE = "tn"
  const val GOOGLE_PAY_CURRENCY = "cu"
  const val CURRENCY = "INR"
  const val MERCHANT_UPI_ID = "pavan.n.sap@okaxis"
  const val MERCHANT_NAME = "Ruben Quadros"
  const val NOTE = "Awesome Drink"
  const val GOOGLE_PAY_STATUS = "Status"
  const val GOOGLE_PAY_TXN_REF = "txnRef"
  const val GOOGLE_PAY_TXN_ID = "txnId"
  const val GOOGLE_PAY_SUCCESS = "SUCCESS"
  const val PHONE_PAY_PACKAGE_NAME = "com.phonepe.app"
  const val PHONE_PE_REQUEST_CODE = 1002
}