package com.ruben.bartender.data.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.data.DataSource
import com.ruben.bartender.data.local.entity.UserEntity
import com.ruben.bartender.data.remote.model.request.SaveUserDetailsRequest
import com.ruben.bartender.data.remote.model.request.SendOtpRequest
import com.ruben.bartender.data.remote.model.request.SignInRequest
import com.ruben.bartender.data.repository.mapper.toLoginBaseRecord
import com.ruben.bartender.data.repository.mapper.toSaveUserBaseRecord
import com.ruben.bartender.data.repository.mapper.toSendOtpBaseRecord
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.LoginRecord
import com.ruben.bartender.domain.record.SendOtpErrorRecord
import com.ruben.bartender.domain.record.SendOtpRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by ruben.quadros on 09/03/20.
 **/
class OnBoardingRepositoryImpl @Inject constructor(dataSource: DataSource) : OnBoardingRepository {

    private val firebaseApi = dataSource.api().firebaseApiHandler()
    private val preferences = dataSource.preference()
    private val db = dataSource.db()

    override suspend fun sendOtp(phoneNumber: String): Flow<BaseRecord<SendOtpRecord, SendOtpErrorRecord>> {
        return firebaseApi.sendOtp(SendOtpRequest(phoneNumber)).map {
            it.toSendOtpBaseRecord()
        }
    }

    override suspend fun login(
        phoneAuthCredential: PhoneAuthCredential,
        phoneNumber: String
    ): Flow<BaseRecord<LoginRecord, ErrorRecord>> {
        return firebaseApi.login(SignInRequest(phoneAuthCredential)).map {
            if (it.isSuccess()) {
                preferences.setUserLoggedIn(isLoggedIn = true)
                db.insertUser(
                    userEntity = UserEntity(
                        phoneNumber = phoneNumber,
                        firstName = "",
                        lastName = ""
                    )
                )
            }
            it.toLoginBaseRecord()
        }
    }

    override suspend fun saveUser(
        phoneNumber: String,
        firstName: String,
        lastName: String
    ): Flow<BaseRecord<Nothing, ErrorRecord>> {
        return firebaseApi.saveUser(
            SaveUserDetailsRequest(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        ).map {
            if (it.isSuccess()) {
                db.updateUser(
                    userEntity = UserEntity(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )
                )
            }
            it.toSaveUserBaseRecord()
        }
    }
}