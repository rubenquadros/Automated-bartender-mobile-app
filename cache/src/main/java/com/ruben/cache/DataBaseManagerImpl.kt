package com.ruben.cache

import com.ruben.cache.dao.UserDao

/**
 * Created by ruben.quadros on 12/03/20.
 **/
class DataBaseManagerImpl(private var consumerDB: ConsumerDB): DatabaseManager {
  override fun user(): UserDao {
    return consumerDB.userDAO()
  }
}