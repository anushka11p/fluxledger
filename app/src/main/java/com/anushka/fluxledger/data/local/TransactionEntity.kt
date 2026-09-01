package com.anushka.fluxledger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    val currency: String,
    val baseAmount: Double,
    val category: String,
    val note: String?,
    val date: Long,
    val createdAt: Long
)