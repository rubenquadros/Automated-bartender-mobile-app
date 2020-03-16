package com.ruben.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.ruben.remote.utils.ApiConstants
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class PreferenceManagerImpl(context: Context) : PreferenceManager {

  private val sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

  override var isLoggedIn: Boolean by BooleanPreference(
    sharedPreferences,
    ApiConstants.IS_LOGGED_IN,
    false
  )
  override var isRegistered: Boolean by BooleanPreference(
    sharedPreferences,
    ApiConstants.IS_REGISTERED,
    false
  )

  override var phone: String? by StringPreference(
    sharedPreferences,
    ApiConstants.PHONE_NUMBER,
    null
  )
}

class BooleanPreference(
  private var preferences: SharedPreferences,
  private var name: String,
  private var defaultValue: Boolean
) : ReadWriteProperty<Any, Boolean> {
  override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
    return preferences.getBoolean(name, defaultValue)
  }

  override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
    preferences.edit().putBoolean(name, value).apply()
  }
}

class StringPreference(
  private var preferences: SharedPreferences,
  private var name: String,
  private var defaultValue: String?
) : ReadWriteProperty<Any, String?> {

  override fun getValue(
    thisRef: Any, property: KProperty<*>
  ): String? {
    return preferences.getString(name, defaultValue)
  }

  override fun setValue(
    thisRef: Any, property: KProperty<*>, value: String?
  ) {
    preferences.edit().putString(name, value).apply()
  }
}