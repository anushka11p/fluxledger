package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.RateRepository
import com.anushka.fluxledger.domain.repository.TransactionRepository
import java.util.UUID
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val rateRepository: RateRepository
) {
    suspend operator fun invoke(
        amount: Double,
        currency: String,
        category: String,
        note: String?,
        date: Long,
        homeCurrency: String = "INR"
    ) {
        val baseAmount = rateRepository.convert(amount, currency, homeCurrency)

        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            amount = amount,
            currency = currency,
            baseAmount = baseAmount,
            category = category,
            note = note,
            date = date,
            createdAt = System.currentTimeMillis()
        )

        transactionRepository.addTransaction(transaction)
    }
}