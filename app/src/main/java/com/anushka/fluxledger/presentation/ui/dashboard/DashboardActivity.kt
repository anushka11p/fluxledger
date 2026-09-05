package com.anushka.fluxledger.presentation.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.anushka.fluxledger.databinding.ActivityDashboardBinding
import com.anushka.fluxledger.presentation.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val adapter = CategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = adapter

        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.summary.collect { summary ->
                    binding.monthTotal.text = format.format(summary.monthTotal)
                    adapter.submitList(summary.categories)
                    val isEmpty = summary.categories.isEmpty()
                    binding.transactionCount.text = when (summary.categories.size) {
                        0 -> "nothing recorded yet"
                        1 -> "across 1 category"
                        else -> "across ${summary.categories.size} categories"
                    }
                    binding.breakdownHeader.visibility = if (isEmpty) View.GONE else View.VISIBLE

                    binding.categoryList.visibility = if (isEmpty) View.GONE else View.VISIBLE
                    binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
                }
            }
        }
    }
}
