package com.ruben.bartender.data.remote.utils

/**
 * Created by ruben.quadros on 01/03/20.
 **/
object ApiConstants {
  const val BASE_URL = "http://192.168.0.107:5000/"
  //const val BASE_URL = "http://www.mocky.io/"
  const val TIMEOUT_REQUEST: Long = 30
  const val TIMEOUT_OTP: Long = 60L
  const val HTTP_OK = 200
  const val HTTP_AUTH_FAIL = 401
  const val HTTP_API_FAIL = 500
  const val HTTP_NEW_USER = 404
  const val HTTP_CON_ERROR = 503
  const val SUCCESS = "Success"
  const val FAIL = "Fail"
  const val NEW_USER = "New User"
  const val AUTH_FAIL = "Auth fail"
  const val CON_ERROR = "Connection failed"
  const val MENU_COLLECTION = "menu"
  const val BASIC_MENU_DOC = "basicmenu"
  const val BASIC_MENU_COLLECTION = "basicmenulist"
  const val USER_DETAILS_COLLECTION = "users"
  const val FIRST_NAME_DOC = "first_name"
  const val LAST_NAME_DOC = "last_name"
  const val PHONE_NUMBER_DOC = "phone_number_doc"
  const val IS_LOGGED_IN = "is_logged_in"
  const val IS_REGISTERED = "is_registered"
}