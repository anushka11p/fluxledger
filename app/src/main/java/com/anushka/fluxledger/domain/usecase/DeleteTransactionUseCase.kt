package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.deleteTransaction(transaction)
    }
}