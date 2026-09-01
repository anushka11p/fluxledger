package com.anushka.fluxledger.domain.repository

interface RateRepository {
    suspend fun convert(amount: Double, from: String, to: String): Double
}