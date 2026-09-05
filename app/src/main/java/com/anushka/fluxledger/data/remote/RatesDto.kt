package com.anushka.fluxledger.data.remote

data class RatesDto(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)