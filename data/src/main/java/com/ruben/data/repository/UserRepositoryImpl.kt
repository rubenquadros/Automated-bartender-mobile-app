package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class UserRepositoryImpl @Inject constructor(dataSource: DataSource): UserRepository {
  override fun isLoggedIn(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isRegistered(): Boolean {
    TODO("Not yet implemented")
  }
}