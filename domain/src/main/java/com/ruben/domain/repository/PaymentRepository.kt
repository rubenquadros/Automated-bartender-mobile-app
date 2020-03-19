package com.ruben.domain.repository

import com.ruben.domain.model.CheckGooglePayRecord
import kotlinx.coroutines.flow.Flow

/**
 * Created by ruben.quadros on 19/03/20.
 **/
interface PaymentRepository {

  fun checkGooglePayAvailability(
    type: String,
    allowedNetworks: List<String>,
    allowedAuth: List<String>,
    apiVersion: Int,
    apiVersionMinor: Int
  ): Flow<CheckGooglePayRecord?>
}