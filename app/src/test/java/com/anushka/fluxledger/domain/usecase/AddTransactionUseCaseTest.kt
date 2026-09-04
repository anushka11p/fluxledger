package com.anushka.fluxledger.domain.usecase

import app.cash.turbine.test
import com.anushka.fluxledger.fakes.FakeRateRepository
import com.anushka.fluxledger.fakes.FakeTransactionRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTransactionUseCaseTest {

    private lateinit var transactionRepo: FakeTransactionRepository
    private lateinit var rateRepo: FakeRateRepository
    private lateinit var useCase: AddTransactionUseCase

    @Before
    fun setup() {
        transactionRepo = FakeTransactionRepository()
        rateRepo = FakeRateRepository()
        useCase = AddTransactionUseCase(transactionRepo, rateRepo)
    }

    @Test
    fun `adds transaction with correct baseAmount`() = runTest {
        rateRepo.setConversionRate(0.012) // example rate

        useCase(
            amount = 1000.0,
            currency = "INR",
            category = "Food",
            note = "Lunch",
            date = System.currentTimeMillis(),
            homeCurrency = "USD"
        )

        transactionRepo.getAllTransactions().test {
            val list = awaitItem()
            assertThat(list).hasSize(1)
            assertThat(list[0].amount).isEqualTo(1000.0)
            assertThat(list[0].baseAmount).isEqualTo(12.0) // 1000 * 0.012
            assertThat(list[0].category).isEqualTo("Food")
            cancelAndIgnoreRemainingEvents()
        }
    }
}