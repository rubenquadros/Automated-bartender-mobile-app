package com.ruben.remote.utils

/**
 * Created by ruben.quadros on 01/03/20.
 **/
object ApiConstants {
  const val BASE_URL = "http://192.168.1.108:5000/"
  //const val BASE_URL = "http://www.mocky.io/"
  const val TIMEOUT_REQUEST: Long = 30
  const val TIMEOUT_OTP: Long = 60L
  const val HTTP_OK = 200
  const val HTTP_AUTH_FAIL = 401
  const val HTTP_API_FAIL = 500
  const val HTTP_NEW_USER = 404
  const val SUCCESS = "success"
  const val FAIL = "fail"
  const val AUTH_FAIL = "Auth fail"
  const val MENU_COLLECTION = "menu"
  const val BASIC_MENU_DOC = "basicmenu"
  const val BASIC_MENU_COLLECTION = "basicmenulist"
  const val USER_DETAILS_COLLECTION = "users"
}