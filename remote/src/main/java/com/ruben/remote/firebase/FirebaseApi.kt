package com.ruben.remote.firebase

import com.ruben.remote.model.request.GetUserDataRequest
import com.ruben.remote.model.request.SendOtpRequest
import com.ruben.remote.model.request.SignInRequest
import com.ruben.remote.model.request.SaveUserDetailsRequest
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.remote.model.response.onBoardingResponse.SaveUserDetailsResponse
import com.ruben.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.remote.model.response.onBoardingResponse.SignInResponse
import com.ruben.remote.model.response.userDataResponse.UserDataResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface FirebaseApi {
  fun sendOTP(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse?>
  fun signIn(signInRequest: SignInRequest): Flow<SignInResponse?>
  fun saveUser(saveUserDetailsRequest: SaveUserDetailsRequest): Flow<SaveUserDetailsResponse?>
  fun getUserData(getUserDataRequest: GetUserDataRequest): Flow<UserDataResponse?>
  fun getBasicMenu(): Flow<BasicMenuResponse?>
  fun getMenuCategories(): Flow<CategoryResponse?>
}