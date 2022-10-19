package com.ruben.bartender.data.local

import com.ruben.bartender.data.local.dao.UserDao

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface DatabaseManager {
  fun user(): UserDao
}