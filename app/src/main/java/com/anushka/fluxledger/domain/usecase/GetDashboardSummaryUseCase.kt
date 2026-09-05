package com.anushka.fluxledger.domain.usecase

import com.anushka.fluxledger.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

data class CategoryTotal(
    val category: String,
    val total: Double,
    val share: Float
)

data class DashboardSummary(
    val monthTotal: Double,
    val categories: List<CategoryTotal>
)

class GetDashboardSummaryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<DashboardSummary> {
        return repository.getTransactionsSince(startOfMonth()).map { transactions ->
            val monthTotal = transactions.sumOf { it.baseAmount }

            val categories = transactions
                .groupBy { it.category }
                .map { (category, items) ->
                    val total = items.sumOf { it.baseAmount }
                    CategoryTotal(
                        category = category,
                        total = total,
                        share = if (monthTotal > 0) (total / monthTotal).toFloat() else 0f
                    )
                }
                .sortedByDescending { it.total }

            DashboardSummary(monthTotal, categories)
        }
    }

    private fun startOfMonth(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
