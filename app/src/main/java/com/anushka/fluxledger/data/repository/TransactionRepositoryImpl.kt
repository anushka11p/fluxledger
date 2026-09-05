package com.anushka.fluxledger.data.repository

import com.anushka.fluxledger.data.local.TransactionDao
import com.anushka.fluxledger.data.mapper.toDomain
import com.anushka.fluxledger.data.mapper.toEntity
import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.update(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.delete(transaction.toEntity())
    }

    override fun getTransactionsSince(startDate: Long): Flow<List<Transaction>> {
        return dao.getSince(startDate).map { list -> list.map { it.toDomain() } }
    }
}