package com.example.budget_management_system.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.budget_management_system.databinding.ItemOperationBinding
import com.example.budget_management_system.models.dto.FinancialOperationDto

class OperationAdapter(
    private val onClick: (FinancialOperationDto) -> Unit = {}
) : ListAdapter<FinancialOperationDto, OperationAdapter.OperationViewHolder>(OperationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val binding = ItemOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OperationViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OperationViewHolder(
        private val binding: ItemOperationBinding,
        private val onClick: (FinancialOperationDto) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(operation: FinancialOperationDto) {
            binding.apply {
                operationAmount.text = "${operation.amount} ${operation.currency}"
                operationType.text = operation.type.name
                operationDate.text = operation.operationDateTime.toString()
                operationDescription.text = operation.description
                root.setOnClickListener { onClick(operation) }
            }
        }
    }

    private class OperationDiffCallback : DiffUtil.ItemCallback<FinancialOperationDto>() {
        override fun areItemsTheSame(oldItem: FinancialOperationDto, newItem: FinancialOperationDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FinancialOperationDto, newItem: FinancialOperationDto): Boolean {
            return oldItem == newItem
        }
    }
}
