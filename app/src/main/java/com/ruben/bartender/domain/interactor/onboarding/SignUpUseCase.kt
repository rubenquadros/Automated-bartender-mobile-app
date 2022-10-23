package com.ruben.bartender.domain.interactor.onboarding

import com.ruben.bartender.domain.BaseFlowUseCase
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.repository.OnBoardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Ruben Quadros on 23/10/22
 **/
class SignUpUseCase @Inject constructor(private val onBoardingRepository: OnBoardingRepository) :
    BaseFlowUseCase<SignUpUseCase.Params, Nothing, ErrorRecord>() {

    override suspend fun execute(request: Params): Flow<BaseRecord<Nothing, ErrorRecord>> = flow {
        emit(BaseRecord.Loading)
        onBoardingRepository.saveUser(
            phoneNumber = request.phoneNumber,
            firstName = request.firstName,
            lastName = request.lastName
        )
    }

    data class Params(
        val phoneNumber: String,
        val firstName: String,
        val lastName: String
    )
}