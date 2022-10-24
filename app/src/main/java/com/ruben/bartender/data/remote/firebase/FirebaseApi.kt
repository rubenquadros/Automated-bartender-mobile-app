package com.ruben.bartender.data.remote.firebase

import com.ruben.bartender.data.remote.model.request.GetUserDataRequest
import com.ruben.bartender.data.remote.model.request.SendOtpRequest
import com.ruben.bartender.data.remote.model.request.SignInRequest
import com.ruben.bartender.data.remote.model.request.SaveUserDetailsRequest
import com.ruben.bartender.data.remote.model.response.basicMenuResponse.MainMenuResponse
import com.ruben.bartender.data.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.CheckUserResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SaveUserDetailsResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.SendOtpResponse
import com.ruben.bartender.data.remote.model.response.onBoardingResponse.LoginResponse
import com.ruben.bartender.data.remote.model.response.signoutResponse.SignoutResponse
import com.ruben.bartender.data.remote.model.response.userDataResponse.UserDataResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 29/02/20.
 **/
interface FirebaseApi {
  suspend fun sendOtp(sendOtpRequest: SendOtpRequest): Flow<SendOtpResponse>
  suspend fun login(signInRequest: SignInRequest): LoginResponse
  suspend fun saveUser(saveUserDetailsRequest: SaveUserDetailsRequest): SaveUserDetailsResponse
  fun checkIfUserExists(): Flow<CheckUserResponse?>
  fun getUserData(getUserDataRequest: GetUserDataRequest): Flow<UserDataResponse?>
  suspend fun getMainMenu(): MainMenuResponse
  fun getMenuCategories(): Flow<CategoryResponse?>
  fun logout(): Flow<SignoutResponse?>
}