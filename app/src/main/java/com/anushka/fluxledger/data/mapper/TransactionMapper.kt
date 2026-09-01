package com.anushka.fluxledger.data.mapper

import com.anushka.fluxledger.data.local.TransactionEntity
import com.anushka.fluxledger.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        currency = currency,
        baseAmount = baseAmount,
        category = category,
        note = note,
        date = date,
        createdAt = createdAt
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        currency = currency,
        baseAmount = baseAmount,
        category = category,
        note = note,
        date = date,
        createdAt = createdAt
    )
}