package com.ruben.bartender.data

import com.ruben.bartender.data.local.DatabaseManager
import com.ruben.bartender.data.preference.PreferenceManager
import com.ruben.bartender.data.remote.NetworkManager

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface DataSource {
  fun api(): NetworkManager
  fun preference(): PreferenceManager
  fun db(): DatabaseManager
}