package com.ruben.remote

import android.content.Context
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ruben.remote.firebase.FirebaseApi
import com.ruben.remote.firebase.FirebaseApiImpl
import com.ruben.remote.payment.ExternalPayment
import com.ruben.remote.payment.ExternalPaymentImpl
import com.ruben.remote.rest.RestApi
import com.ruben.remote.rest.RestApiImpl
import com.ruben.remote.utils.ApiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.*

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@ExperimentalCoroutinesApi
class NetworkManagerImpl(private val context: Context) : NetworkManager {

  private val retrofitApi = getRetrofitApi()
  private val handler: RestApiImpl = RestApiImpl(retrofitApi)
  private val firebaseApi: FirebaseApiImpl = FirebaseApiImpl()
  private val paymentsClient = getPaymentsClient()
  private val paymentHandler: ExternalPaymentImpl = ExternalPaymentImpl(paymentsClient)

  override fun apiHandler(): RestApi {
    return handler
  }

  override fun firebaseApiHandler(): FirebaseApi {
    return firebaseApi
  }

  override fun paymentApi(): ExternalPayment {
    return paymentHandler
  }

  private fun getRetrofitApi(): RetrofitApi {
    return Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(getGson()))
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .client(getHttpClient()).build()
      .create(RetrofitApi::class.java)
  }

  private fun getPaymentsClient(): PaymentsClient {
    val walletOptions =
      Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
    return Wallet.getPaymentsClient(context.applicationContext, walletOptions)
  }

  private fun getGson(): Gson {
    val gsonBuilder = GsonBuilder()
    return gsonBuilder.create()
  }

  private fun getCache(): Cache {
    val cacheSize = (10 * 1024 * 1024).toLong() //10 MB
    val httpCacheDirectory = File(context.cacheDir, "http-cache")
    return Cache(httpCacheDirectory, cacheSize)
  }

  private fun getHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.cache(getCache())
    httpClient.readTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
    httpClient.connectTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
    httpClient.writeTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
    return httpClient.build()
  }
}