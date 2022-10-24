package com.ruben.bartender.data.repository

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.base.DispatcherProvider
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
import kotlinx.coroutines.withContext

/**
 * Created by ruben.quadros on 09/03/20.
 **/
class OnBoardingRepositoryImpl @Inject constructor(
    dataSource: DataSource,
    private val dispatcherProvider: DispatcherProvider
) : OnBoardingRepository {

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
    ): BaseRecord<LoginRecord, ErrorRecord> {
        val result = firebaseApi.login(SignInRequest(phoneAuthCredential))
        if (result.isSuccess()) {
            preferences.setUserLoggedIn(isLoggedIn = true)
            db.insertUser(
                userEntity = UserEntity(
                    phoneNumber = phoneNumber,
                    firstName = "",
                    lastName = ""
                )
            )
        }
        return withContext(dispatcherProvider.default) {
            result.toLoginBaseRecord()
        }
    }

    override suspend fun saveUser(
        phoneNumber: String,
        firstName: String,
        lastName: String
    ): BaseRecord<Nothing, ErrorRecord> {
        val result = firebaseApi.saveUser(
            SaveUserDetailsRequest(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        )

        if (result.isSuccess()) {
            db.updateUser(
                userEntity = UserEntity(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber
                )
            )
        }

        return withContext(dispatcherProvider.default) {
            result.toSaveUserBaseRecord()
        }
    }
}