package com.anushka.fluxledger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.fluxledger.domain.usecase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    fun addTransaction(
        amount: Double,
        currency: String,
        category: String,
        note: String?,
        date: Long
    ) {
        if (_isSaving.value) return

        viewModelScope.launch {
            _isSaving.value = true
            try {
                addTransactionUseCase(amount, currency, category, note, date)
                _saved.value = true
            } catch (e: Exception) {
                Log.e("AddTransactionVM", "Save failed", e)
            } finally {
                _isSaving.value = false
            }
        }
    }
}