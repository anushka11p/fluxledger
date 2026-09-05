package com.anushka.fluxledger.data.repository

import android.util.Log
import com.anushka.fluxledger.data.local.RateDao
import com.anushka.fluxledger.data.local.RateEntity
import com.anushka.fluxledger.data.remote.ExchangeRateApi
import com.anushka.fluxledger.domain.repository.RateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepositoryImpl @Inject constructor(
    private val api: ExchangeRateApi,
    private val dao: RateDao
) : RateRepository {

    private val cacheTtlMs = 6 * 60 * 60 * 1000L

    override suspend fun convert(amount: Double, from: String, to: String): Double {
        if (from == to) return amount
        val rates = rates() ?: return amount
        val fromRate = rates[from] ?: return amount
        val toRate = rates[to] ?: return amount
        return amount / fromRate * toRate
    }

    private suspend fun rates(): Map<String, Double>? {
        val cached = dao.getAll()
        val isFresh = cached.isNotEmpty() &&
                System.currentTimeMillis() - cached.first().fetchedAt < cacheTtlMs

        if (isFresh) return cached.associate { it.currency to it.rate }

        return try {
            val dto = api.getLatestRates()
            val rates = dto.rates + (dto.base to 1.0)
            val now = System.currentTimeMillis()
            dao.insertAll(rates.map { RateEntity(it.key, it.value, now) })
            rates
        } catch (e: Exception) {
            Log.e("RateRepository", "Rate fetch failed, falling back to cache", e)
            cached.takeIf { it.isNotEmpty() }?.associate { it.currency to it.rate }
        }
    }
}