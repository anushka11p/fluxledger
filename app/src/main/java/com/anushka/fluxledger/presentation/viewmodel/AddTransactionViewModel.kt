package com.anushka.fluxledger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.fluxledger.domain.usecase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    fun addTransaction(
        amount: Double,
        currency: String,
        category: String,
        note: String?,
        date: Long
    ) {
        viewModelScope.launch {
            addTransactionUseCase(
                amount = amount,
                currency = currency,
                category = category,
                note = note,
                date = date
            )
        }
    }
}