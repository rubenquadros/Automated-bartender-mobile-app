package com.ruben.data

import com.ruben.data.preference.PreferenceManager
import com.ruben.remote.NetworkManager

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface DataSource {
  fun api(): NetworkManager
  fun preference(): PreferenceManager
}