package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.data.mapper.UserDataMapper
import com.ruben.domain.model.UserRecord
import com.ruben.domain.repository.UserRepository
import com.ruben.remote.model.request.GetUserDataRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class UserRepositoryImpl @Inject constructor(dataSource: DataSource): UserRepository {

  private val preference = dataSource.preference()
  private val firebaseApi = dataSource.api().firebaseApiHandler()
  private val userDataMapper = UserDataMapper()

  override fun isLoggedIn(): Boolean {
    return preference.isLoggedIn
  }

  override fun phoneNumber(): String? {
    return preference.phone
  }

  override fun getUserData(phoneNumber: String): Flow<UserRecord?> {
    return firebaseApi.getUserData(GetUserDataRequest(phoneNumber)).map {
      userDataMapper.mapUserData(it)
    }
  }
}