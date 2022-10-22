package com.ruben.bartender.data

import com.ruben.bartender.data.local.DatabaseManager
import com.ruben.bartender.data.preference.PreferenceManager
import com.ruben.bartender.data.remote.NetworkManager
import javax.inject.Inject

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class DataSourceImpl @Inject constructor(
  private val networkManager: NetworkManager,
  private val preferenceManager: PreferenceManager,
  private val databaseManager: DatabaseManager
) : DataSource {

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