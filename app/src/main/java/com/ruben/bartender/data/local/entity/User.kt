package com.ruben.bartender.data.local.entity

import androidx.room.Entity

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Entity(
  tableName = "user",
  primaryKeys = ["phoneNumber"]
)
data class User(
  var firstName: String?,
  var lastName: String?,
  var phoneNumber: String
)