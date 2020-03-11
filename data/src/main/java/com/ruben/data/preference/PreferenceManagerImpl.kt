package com.ruben.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.ruben.data.utils.DataConstants
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class PreferenceManagerImpl(context: Context): PreferenceManager {

  private val sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

  override var isLoggedIn: Boolean by BooleanPreference(sharedPreferences, DataConstants.IS_LOGGED_IN, false)
  override var isRegistered: Boolean by BooleanPreference(sharedPreferences, DataConstants.IS_REGISTERED, false)

  class BooleanPreference(
    private var preferences: SharedPreferences,
    private var name: String,
    private var defaultValue: Boolean
  ): ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
      return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
      preferences.edit().putBoolean(name,value).apply()
    }
  }
}