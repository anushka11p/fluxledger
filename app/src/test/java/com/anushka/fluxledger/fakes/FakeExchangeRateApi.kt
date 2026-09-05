package com.anushka.fluxledger.fakes

import com.anushka.fluxledger.data.remote.ExchangeRateApi
import com.anushka.fluxledger.data.remote.RatesDto
import java.io.IOException

class FakeExchangeRateApi : ExchangeRateApi {

    var shouldFail = false
    var callCount = 0

    override suspend fun getLatestRates(): RatesDto {
        callCount++
        if (shouldFail) throw IOException("No network")
        return RatesDto(
            amount = 1.0,
            base = "EUR",
            date = "2026-09-05",
            rates = mapOf(
                "USD" to 1.16,
                "INR" to 109.8,
                "GBP" to 0.86
            )
        )
    }
}
