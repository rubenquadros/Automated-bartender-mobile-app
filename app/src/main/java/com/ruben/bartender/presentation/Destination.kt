package com.ruben.bartender.presentation

/**
 * Created by Ruben Quadros on 22/10/22
 **/
object Destination {

    object LOGIN {
        const val Route = "login"
    }

    object SIGNUP {
        const val Route = "signUp"
    }

    object HOME {
        const val Route = "home"
    }
}

interface IDestination {
    fun createRoute(vararg args: String): String
}