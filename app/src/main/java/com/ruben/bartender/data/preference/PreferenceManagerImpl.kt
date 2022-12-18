package com.ruben.bartender.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ruben.bartender.data.remote.utils.ApiConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class PreferenceManagerImpl @Inject constructor(@ApplicationContext context: Context) :
    PreferenceManager {

    companion object {
        private const val PREF_NAME = "elbarman.pref"
        private val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    }

    private val Context.elBarmanDataStore by preferencesDataStore(PREF_NAME)

    private val _preference = context.elBarmanDataStore

    override suspend fun isUserLoggedIn(): Boolean {
        return _preference.data.map { it[IS_USER_LOGGED_IN] ?: false }.first()
    }

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) {
        _preference.edit { it[IS_USER_LOGGED_IN] = isLoggedIn }
    }

}