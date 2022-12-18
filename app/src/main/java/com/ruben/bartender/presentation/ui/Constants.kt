package com.ruben.bartender.presentation.ui

/**
 * Created by Ruben Quadros on 23/10/22
 **/
object Constants {
    const val PHONE_NUMBER_REGEX = "[\\d]{1,10}"
    const val OTP_REGEX = "[\\d]{1,6}"
    const val LETTERS_REGEX = "^[a-zA-Z]*\$"
    const val PHONE_NUMBER_LENGTH = 10
    const val OTP_LENGTH = 6
    const val COUNTRY_CODE = "+91"
    const val OTP_TIME = 60 * 1000L //60 sec
    const val OTP_TIMER_INTERVAL = 1000L //1 sec
    const val OTP_TIMER_FORMAT = "%02d:%02d"
    const val GPAY_ID = "gpay"
    const val PHONE_PE_ID = "phonepe"
}