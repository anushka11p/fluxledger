package com.anushka.fluxledger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.fluxledger.domain.model.Transaction
import com.anushka.fluxledger.domain.usecase.AddTransactionUseCase
import com.anushka.fluxledger.domain.usecase.GetTransactionByIdUseCase
import com.anushka.fluxledger.domain.usecase.UpdateTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase
) : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    private val _existing = MutableStateFlow<Transaction?>(null)
    val existing: StateFlow<Transaction?> = _existing.asStateFlow()

    fun load(id: String) {
        if (_existing.value != null) return
        viewModelScope.launch {
            _existing.value = getTransactionByIdUseCase(id)
        }
    }

    fun save(
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
                val original = _existing.value
                if (original == null) {
                    addTransactionUseCase(amount, currency, category, note, date)
                } else {
                    updateTransactionUseCase(original, amount, currency, category, note, date)
                }
                _saved.value = true
            } catch (e: Exception) {
                Log.e("AddTransactionVM", "Save failed", e)
            } finally {
                _isSaving.value = false
            }
        }
    }
}
