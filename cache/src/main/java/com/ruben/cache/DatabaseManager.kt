package com.ruben.cache

import com.ruben.cache.dao.UserDao

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface DatabaseManager {
  fun user(): UserDao
}