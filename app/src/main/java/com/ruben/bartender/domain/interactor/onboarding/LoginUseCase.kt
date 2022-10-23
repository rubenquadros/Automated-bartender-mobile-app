package com.ruben.bartender.domain.interactor.onboarding

import com.google.firebase.auth.PhoneAuthCredential
import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.LoginRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ruben Quadros on 23/10/22
 **/
class LoginUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    BaseFlowUseCase<LoginUseCase.Params, LoginRecord, ErrorRecord>() {

    override suspend fun execute(request: Params): Flow<BaseRecord<LoginRecord, ErrorRecord>> =
        flow {
            emit(BaseRecord.Loading)
            onBoardingRepository.login(
                phoneAuthCredential = request.credential,
                phoneNumber = request.phoneNumber
            )
        }

    data class Params(val credential: PhoneAuthCredential, val phoneNumber: String)
}