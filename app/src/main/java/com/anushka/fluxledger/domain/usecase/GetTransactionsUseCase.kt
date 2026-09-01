package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke() = repository.getAllTransactions()
}