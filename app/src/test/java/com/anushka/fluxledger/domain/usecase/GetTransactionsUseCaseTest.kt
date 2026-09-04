package com.anushka.fluxledger.domain.usecase

import app.cash.turbine.test
import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.fakes.FakeTransactionRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTransactionsUseCaseTest {

    private lateinit var repository: FakeTransactionRepository
    private lateinit var useCase: GetTransactionsUseCase

    @Before
    fun setup() {
        repository = FakeTransactionRepository()
        useCase = GetTransactionsUseCase(repository)
    }

    @Test
    fun `when repository is empty, returns empty list`() = runTest {
        useCase().test {
            val result = awaitItem()
            assertThat(result).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when repository has transactions, returns them`() = runTest {
        val transaction = Transaction(
            id = "1",
            amount = 100.0,
            currency = "INR",
            baseAmount = 100.0,
            category = "Food",
            note = "Test",
            date = 123L,
            createdAt = 123L
        )
        repository.setTransactions(listOf(transaction))

        useCase().test {
            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result[0].amount).isEqualTo(100.0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}