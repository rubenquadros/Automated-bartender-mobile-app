package com.ruben.bartender.data.remote.model.response

/**
 * Created by Ruben Quadros on 24/10/22
 **/
sealed class GetDrinkDetailsResponse {
    data class GetDrinkDetailsFail(val message: String) : GetDrinkDetailsResponse()
    data class GetDrinkDetailsSuccess(val drinkDetails: DrinkDetails) : GetDrinkDetailsResponse()
}

data class DrinkDetails(
    val name: String,
    val image: String?,
    val description: String,
    val price: String,
    val ingredients: String
)
