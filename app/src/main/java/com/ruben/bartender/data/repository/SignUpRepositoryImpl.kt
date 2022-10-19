package com.ruben.bartender.data.repository

import com.ruben.bartender.data.local.entity.User
import com.ruben.bartender.data.DataSource
import com.ruben.bartender.domain.model.SaveUserRecord
import com.ruben.bartender.domain.repository.SignUpRepository
import com.ruben.bartender.data.mapper.BoardingMapper
import com.ruben.bartender.data.remote.model.request.SaveUserDetailsRequest
import com.ruben.bartender.data.remote.utils.ApiConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 11/03/20.
 **/
class SignUpRepositoryImpl @Inject constructor(dataSource: DataSource): SignUpRepository {

  private val firebaseApi = dataSource.api().firebaseApiHandler()
  private val preferences = dataSource.preference()
  private val db = dataSource.db()
  private val boardingMapper = BoardingMapper()

  override fun saveUserDetails(
    firstName: String,
    lastName: String,
    phoneNumber: String
  ): Flow<SaveUserRecord?> {
    return firebaseApi.saveUser(SaveUserDetailsRequest(firstName, lastName, phoneNumber))
      .map {
        if(it?.status == ApiConstants.HTTP_OK) {
          preferences.isLoggedIn = true
          preferences.isRegistered = true
          db.user().saveUser(User(firstName, lastName, phoneNumber))
        }
        boardingMapper.mapSaveUserResponse(it)
      }
  }

}