package com.anushka.fluxledger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class RateEntity(
    @PrimaryKey val currency: String,
    val rate: Double,        // relative to the API's base currency (EUR)
    val fetchedAt: Long
)