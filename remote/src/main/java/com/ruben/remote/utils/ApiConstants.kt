package com.ruben.remote.utils

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class ApiConstants {
  companion object {
    const val BASE_URL = "http://192.168.1.108:5000/"
    //const val BASE_URL = "http://www.mocky.io/"
    const val TIMEOUT_REQUEST: Long = 30
    const val TIMEOUT_OTP: Long = 60L
    const val SUCCESS = "success"
    const val FAIL = "fail"
    const val MENU_COLLECTION = "menu"
    const val BASIC_MENU_DOC = "basicmenu"
    const val BASIC_MENU_COLLECTION = "basicmenulist"
  }
}