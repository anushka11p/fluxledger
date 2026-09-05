package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.RateRepository
import com.anushka.fluxledger.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val rateRepository: RateRepository
) {
    suspend operator fun invoke(
        original: Transaction,
        amount: Double,
        currency: String,
        category: String,
        note: String?,
        date: Long,
        homeCurrency: String = "INR"
    ) {
        val amountChanged = amount != original.amount || currency != original.currency

        val baseAmount = if (amountChanged) {
            rateRepository.convert(amount, currency, homeCurrency)
        } else {
            original.baseAmount
        }

        transactionRepository.updateTransaction(
            original.copy(
                amount = amount,
                currency = currency,
                baseAmount = baseAmount,
                category = category,
                note = note,
                date = date
            )
        )
    }
}
