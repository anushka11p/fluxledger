package com.anushka.fluxledger.fakes

import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTransactionRepository : TransactionRepository {

    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactions.asStateFlow()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactions.value = transactions.value + transaction
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactions.value = transactions.value.map {
            if (it.id == transaction.id) transaction else it
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactions.value = transactions.value.filter { it.id != transaction.id }
    }

    // Helper for tests
    fun setTransactions(list: List<Transaction>) {
        transactions.value = list
    }
}