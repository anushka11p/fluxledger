package com.anushka.fluxledger.di

import com.anushka.fluxledger.data.repository.RateRepositoryImpl
import com.anushka.fluxledger.data.repository.TransactionRepositoryImpl
import com.anushka.fluxledger.domain.repository.RateRepository
import com.anushka.fluxledger.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindRateRepository(
        impl: RateRepositoryImpl
    ): RateRepository
}