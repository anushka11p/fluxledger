package com.anushka.fluxledger.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("v1/latest")
    suspend fun getLatestRates(): RatesDto   // EUR-based by default
}