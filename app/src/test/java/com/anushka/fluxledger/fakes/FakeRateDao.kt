package com.anushka.fluxledger.fakes

import com.anushka.fluxledger.data.local.RateDao
import com.anushka.fluxledger.data.local.RateEntity

class FakeRateDao : RateDao {

    private val stored = mutableMapOf<String, RateEntity>()

    override suspend fun getAll(): List<RateEntity> = stored.values.toList()

    override suspend fun insertAll(rates: List<RateEntity>) {
        rates.forEach { stored[it.currency] = it }
    }

    fun seed(rates: List<RateEntity>) {
        rates.forEach { stored[it.currency] = it }
    }
}
