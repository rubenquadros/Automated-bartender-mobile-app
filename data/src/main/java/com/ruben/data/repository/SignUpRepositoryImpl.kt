package com.ruben.data.repository

import com.ruben.cache.entity.User
import com.ruben.data.DataSource
import com.ruben.data.mapper.BoardingMapper
import com.ruben.data.utils.DataConstants
import com.ruben.domain.model.SaveUserRecord
import com.ruben.domain.repository.SignUpRepository
import com.ruben.remote.model.request.SaveUserDetailsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class SignUpRepositoryImpl @Inject constructor(dataSource: DataSource): SignUpRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()
  private val db = dataSource.db()
  private val boardingMapper = BoardingMapper()

  override fun saveUserDetails(
    firstName: String,
    lastName: String,
    phoneNumber: String
  ): Flow<SaveUserRecord?> {
    return firebaseApi.saveUser(SaveUserDetailsRequest(firstName, lastName, phoneNumber))
      .map {
        if(it?.status == DataConstants.HTTP_OK) {
          db.user().saveUser(User(firstName, lastName, phoneNumber))
        }
        boardingMapper.mapSaveUserResponse(it)
      }
  }

}