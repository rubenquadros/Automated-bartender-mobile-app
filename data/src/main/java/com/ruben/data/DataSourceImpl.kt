package com.ruben.data

import android.content.Context
import com.ruben.cache.ConsumerDB
import com.ruben.cache.DataBaseManagerImpl
import com.ruben.cache.DatabaseManager
import com.ruben.data.preference.PreferenceManager
import com.ruben.data.preference.PreferenceManagerImpl
import com.ruben.remote.NetworkManager
import com.ruben.remote.NetworkManagerImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by ruben.quadros on 01/03/20.
 **/
@ExperimentalCoroutinesApi
class DataSourceImpl @Inject constructor(context: Context) : DataSource {

  private val networkManager: NetworkManager = NetworkManagerImpl(context)
  private val preferenceManager: PreferenceManager = PreferenceManagerImpl(context)
  private val databaseManager: DatabaseManager = DataBaseManagerImpl(ConsumerDB.getInstance(context.applicationContext))

  companion object : SingletonHolder<DataSource, Context>(::DataSourceImpl)

  override fun api(): NetworkManager {
    return networkManager
  }

  override fun preference(): PreferenceManager {
    return preferenceManager
  }

  override fun db(): DatabaseManager {
    return databaseManager
  }
}