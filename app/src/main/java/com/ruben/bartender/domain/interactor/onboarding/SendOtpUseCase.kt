package com.ruben.bartender.domain.interactor.onboarding

import com.google.firebase.auth.PhoneAuthProvider
import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.SendOtpErrorRecord
import com.ruben.bartender.domain.record.SendOtpRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

/**
 * Created by Ruben Quadros on 23/10/22
 **/
class SendOtpUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    BaseFlowUseCase<SendOtpUseCase.Params, SendOtpRecord, SendOtpErrorRecord>() {

    override suspend fun execute(request: Params): Flow<BaseRecord<SendOtpRecord, SendOtpErrorRecord>> =
        flow {
            emit(BaseRecord.Loading)
            emitAll(onBoardingRepository.sendOtp(phoneNumber = request.phoneNumber, request.resendToken))
        }

    data class Params(
        val phoneNumber: String,
        val resendToken: PhoneAuthProvider.ForceResendingToken?
    )
}