package com.ruben.bartender.data.remote.di

import android.content.Context
import com.ruben.bartender.BuildConfig
import com.ruben.bartender.data.preference.PreferenceManager
import com.ruben.bartender.data.preference.PreferenceManagerImpl
import com.ruben.bartender.data.remote.NetworkManager
import com.ruben.bartender.data.remote.NetworkManagerImpl
import com.ruben.bartender.data.remote.RetrofitApi
import com.ruben.bartender.data.remote.firebase.FirebaseApi
import com.ruben.bartender.data.remote.firebase.FirebaseApiImpl
import com.ruben.bartender.data.remote.rest.RestApi
import com.ruben.bartender.data.remote.rest.RestApiImpl
import com.ruben.bartender.data.remote.utils.ApiConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@[Module InstallIn(SingletonComponent::class)]
internal interface RemoteModule {

    @[Binds Singleton]
    fun bindNetworkManager(networkManager: NetworkManagerImpl): NetworkManager

    @[Binds Singleton]
    fun bindFirebaseApi(firebaseApi: FirebaseApiImpl): FirebaseApi

    @[Binds Singleton]
    fun bindPreferenceManager(preferenceManager: PreferenceManagerImpl): PreferenceManager

    @[Binds Singleton]
    fun bindRestApi(restApi: RestApiImpl): RestApi

}

@[Module InstallIn(SingletonComponent::class)]
internal object RetrofitModule {

    @[Provides Singleton]
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi {
        return retrofit.create(RetrofitApi::class.java)
    }

    @[Provides Singleton]
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @[Provides Singleton]
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.readTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.connectTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApiConstants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @[Provides Singleton]
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() //10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }
}