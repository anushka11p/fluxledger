package com.anushka.fluxledger.data.repository
import android.util.Log
import com.anushka.fluxledger.data.remote.ExchangeRateApi
import com.anushka.fluxledger.domain.repository.RateRepository
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class RateRepositoryImpl @Inject constructor(
    private val api: ExchangeRateApi
) : RateRepository {

    private var cachedRates: Map<String, Double>? = null
    private var cachedAt: Long = 0
    private val cacheTtlMs = 6 * 60 * 60 * 1000L

    override suspend fun convert(amount: Double, from: String, to: String): Double {
        if (from == to) return amount
        val rates = eurRates() ?: return amount
        val fromRate = rates[from] ?: return amount
        val toRate = rates[to] ?: return amount
        return amount / fromRate * toRate
    }

    private suspend fun eurRates(): Map<String, Double>? {
        val cached = cachedRates
        if (cached != null && System.currentTimeMillis() - cachedAt < cacheTtlMs) return cached

        return try {
            val dto = api.getLatestRates()
            val rates = dto.rates + (dto.base to 1.0)
            cachedRates = rates
            cachedAt = System.currentTimeMillis()
            rates
        } catch (e: Exception) {
            Log.e("RateRepository", "Rate fetch failed", e)
            cachedRates   // stale cache if we have it, null if we never fetched
        }
    }
}