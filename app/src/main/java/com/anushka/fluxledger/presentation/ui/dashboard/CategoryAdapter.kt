package com.anushka.fluxledger.presentation.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anushka.fluxledger.databinding.ItemCategoryBinding
import com.anushka.fluxledger.domain.usecase.CategoryTotal
import java.text.NumberFormat
import java.util.Locale

class CategoryAdapter : ListAdapter<CategoryTotal, CategoryAdapter.ViewHolder>(DIFF) {

    class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))

        holder.binding.categoryName.text = item.category
        holder.binding.categoryAmount.text = format.format(item.total)
        holder.binding.categoryBar.progress = (item.share * 100).toInt()
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CategoryTotal>() {
            override fun areItemsTheSame(old: CategoryTotal, new: CategoryTotal) =
                old.category == new.category

            override fun areContentsTheSame(old: CategoryTotal, new: CategoryTotal) =
                old == new
        }
    }
}
