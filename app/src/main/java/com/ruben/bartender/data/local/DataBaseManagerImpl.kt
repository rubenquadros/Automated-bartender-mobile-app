package com.ruben.bartender.data.local

import com.ruben.cache.ConsumerDB
import com.ruben.bartender.data.local.dao.UserDao

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class DataBaseManagerImpl(private var consumerDB: ConsumerDB): DatabaseManager {
  override fun user(): UserDao {
    return consumerDB.userDAO()
  }
}