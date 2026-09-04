package com.anushka.fluxledger.fakes

import com.anushka.fluxledger.domain.repository.RateRepository

class FakeRateRepository : RateRepository {

    private var conversionRate = 1.0

    fun setConversionRate(rate: Double) {
        conversionRate = rate
    }

    override suspend fun convert(amount: Double, from: String, to: String): Double {
        return amount * conversionRate
    }
}