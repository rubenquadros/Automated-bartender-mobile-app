package com.ruben.data.repository

import com.ruben.data.DataSource
import com.ruben.data.mapper.PaymentMapper
import com.ruben.domain.model.CheckGooglePayRecord
import com.ruben.domain.repository.PaymentRepository
import com.ruben.remote.model.request.CheckGooglePayRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 19/03/20.
 **/
class PaymentRepositoryImpl @Inject constructor(dataSource: DataSource) : PaymentRepository {

  private val paymentApi = dataSource.api().paymentApi()
  private val paymentMapper = PaymentMapper()

  override fun checkGooglePayAvailability(
    type: String,
    allowedNetworks: List<String>,
    allowedAuth: List<String>,
    apiVersion: Int,
    apiVersionMinor: Int
  ): Flow<CheckGooglePayRecord?> {
    return paymentApi.isGooglePayReady(
      CheckGooglePayRequest(
        type,
        allowedNetworks,
        allowedAuth,
        apiVersion,
        apiVersionMinor
      )
    ).map {
      paymentMapper.mapCheckGooglePayResponse(it)
    }
  }
}