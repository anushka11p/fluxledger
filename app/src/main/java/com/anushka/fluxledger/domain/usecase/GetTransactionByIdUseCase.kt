package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Transaction? = repository.getTransactionById(id)
}
