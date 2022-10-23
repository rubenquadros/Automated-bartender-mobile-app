package com.ruben.bartender.data.remote.model.response.basicMenuResponse

sealed class MainMenuResponse {
    data class MainMenuSuccess(val mainMenu: List<MainMenuItem>) : MainMenuResponse()
    data class MainMenuFail(val message: String) : MainMenuResponse()
}

data class MainMenuItem(
    val image: String?,
    val name: String,
    val price: String,
    val uniqueId: String
)