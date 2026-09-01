package com.anushka.fluxledger.domain.model

data class Transaction(
    val id: String,
    val amount: Double,
    val currency: String,
    val baseAmount: Double,
    val category: String,
    val note: String?,
    val date: Long,
    val createdAt: Long
)