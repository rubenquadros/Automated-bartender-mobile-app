package com.ruben.bartender.presentation

/**
 * Created by Ruben Quadros on 22/10/22
 **/
object Destination {

    object LOGIN {
        const val Route = "login"
    }

    object SIGNUP: IDestination {
        const val PhoneArg = "number"
        const val Route = "signUp/{$PhoneArg}"
        override fun createRoute(vararg args: String): String = "signUp/${args[0]}"
    }

    object HOME {
        const val Route = "home"
    }
}

interface IDestination {
    fun createRoute(vararg args: String): String
}