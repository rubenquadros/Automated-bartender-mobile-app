package com.ruben.bartender.data.local

import com.ruben.bartender.data.local.dao.UserDao
import javax.inject.Inject

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class DataBaseManagerImpl @Inject constructor(private var appDb: AppDatabase): DatabaseManager {
  override fun user(): UserDao {
    return appDb.userDAO()
  }
}