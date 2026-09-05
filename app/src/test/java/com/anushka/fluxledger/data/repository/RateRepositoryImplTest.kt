package com.anushka.fluxledger.data.repository

import com.anushka.fluxledger.data.local.RateEntity
import com.anushka.fluxledger.fakes.FakeExchangeRateApi
import com.anushka.fluxledger.fakes.FakeRateDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RateRepositoryImplTest {

    private lateinit var api: FakeExchangeRateApi
    private lateinit var dao: FakeRateDao
    private lateinit var repository: RateRepositoryImpl

    @Before
    fun setup() {
        api = FakeExchangeRateApi()
        dao = FakeRateDao()
        repository = RateRepositoryImpl(api, dao)
    }

    @Test
    fun `same currency returns amount unchanged without hitting api`() = runTest {
        val result = repository.convert(100.0, "USD", "USD")

        assertThat(result).isEqualTo(100.0)
        assertThat(api.callCount).isEqualTo(0)
    }

    @Test
    fun `converts between two currencies using cross rate`() = runTest {
        val result = repository.convert(100.0, "USD", "INR")

        assertThat(result).isWithin(0.01).of(9465.52)
    }

    @Test
    fun `converts from the api base currency`() = runTest {
        val result = repository.convert(100.0, "EUR", "USD")

        assertThat(result).isWithin(0.01).of(116.0)
    }

    @Test
    fun `successful fetch persists every rate to the dao`() = runTest {
        repository.convert(100.0, "USD", "INR")

        val stored = dao.getAll().map { it.currency }
        assertThat(stored).containsExactly("USD", "INR", "GBP", "EUR")
    }

    @Test
    fun `falls back to cached rates when the network fails`() = runTest {
        val now = System.currentTimeMillis()
        dao.seed(
            listOf(
                RateEntity("EUR", 1.0, now),
                RateEntity("USD", 1.16, now),
                RateEntity("INR", 109.8, now)
            )
        )
        api.shouldFail = true

        val result = repository.convert(100.0, "USD", "INR")

        assertThat(result).isWithin(0.01).of(9465.52)
    }

    @Test
    fun `returns amount unchanged when offline with no cache`() = runTest {
        api.shouldFail = true

        val result = repository.convert(100.0, "USD", "INR")

        assertThat(result).isEqualTo(100.0)
    }

    @Test
    fun `unknown currency returns amount unchanged`() = runTest {
        val result = repository.convert(100.0, "USD", "JPY")

        assertThat(result).isEqualTo(100.0)
    }

    @Test
    fun `fresh cache is reused instead of refetching`() = runTest {
        repository.convert(100.0, "USD", "INR")
        repository.convert(50.0, "USD", "INR")

        assertThat(api.callCount).isEqualTo(1)
    }
}
