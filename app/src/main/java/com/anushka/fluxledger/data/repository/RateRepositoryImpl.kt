package com.anushka.fluxledger.data.repository

import com.anushka.fluxledger.domain.repository.RateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RateRepositoryImpl @Inject constructor() : RateRepository {

    // Temporary: no real conversion yet (returns same amount)
    override suspend fun convert(amount: Double, from: String, to: String): Double {
        return amount
    }
}