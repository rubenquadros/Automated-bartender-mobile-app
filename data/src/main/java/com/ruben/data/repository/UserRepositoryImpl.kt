package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class UserRepositoryImpl @Inject constructor(dataSource: DataSource): UserRepository {

  private val preference = dataSource.preference()

  override fun isLoggedIn(): Boolean {
    return preference.isLoggedIn
  }

  override fun isRegistered(): Boolean {
    return preference.isRegistered
  }
}