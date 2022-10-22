package com.ruben.bartender.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Created by ruben.quadros on 12/03/20.
 **/
@Entity(
  tableName = "user",
  primaryKeys = ["phone_number"]
)
data class UserEntity(
  @ColumnInfo(name = "first_name")
  val firstName: String?,
  @ColumnInfo(name = "last_name")
  val lastName: String?,
  @ColumnInfo(name = "phone_number")
  val phoneNumber: String
)